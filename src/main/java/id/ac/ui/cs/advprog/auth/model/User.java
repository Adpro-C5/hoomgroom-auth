package id.ac.ui.cs.advprog.auth.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.Date;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import id.ac.ui.cs.advprog.auth.enums.UserRole;

import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date birthdate;
    
    private String gender;

    @Column(unique=true)
    private String email;
    @Column(unique=true)
    private String username;

    private String password;
    private String role;
    private boolean active;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role.equals("ADMIN")) {
            return UserRole.ADMIN.getGrantedAuthority();
        } else if (role.equals("BUYER")) {
            return UserRole.BUYER.getGrantedAuthority();
        } else {
            return UserRole.GUEST.getGrantedAuthority();
        }
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.active;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}