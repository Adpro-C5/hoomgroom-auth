package id.ac.ui.cs.advprog.auth.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

import java.util.List;
import java.util.Collection;
import java.time.LocalDate;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import id.ac.ui.cs.advprog.auth.enums.Gender;
import id.ac.ui.cs.advprog.auth.enums.UserRole;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_user")
public class User implements UserDetails {

    private String fullName;
    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String username;
    @Id
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private double walletBalance;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Token> tokens;

    User(UserBuilder builder) {
        this.fullName = builder.fullName;
        this.birthdate = builder.birthdate;
        this.gender = builder.gender;
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
        this.walletBalance = builder.walletBalance;
        this.tokens = builder.tokens;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getValue()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getRealUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}