package id.ac.ui.cs.advprog.auth.enums;

import static id.ac.ui.cs.advprog.auth.enums.UserPermission.*;

import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
    ADMIN(Sets.newHashSet(
        ACCOUNT_READ, PRODUCT_CREATE, PRODUCT_READ, 
        PRODUCT_UPDATE, PRODUCT_DELETE, PROMO_CREATE, 
        PROMO_READ, PROMO_UPDATE, PROMO_DELETE, 
        PURCHASE_READ, SHIPMENT_UPDATE, STATS_VIEW,
        TRANSACTION_MANAGE, VERIFY_READ
    )),
    BUYER(Sets.newHashSet(
        PRODUCT_READ, PURCHASE_CREATE, PURCHASE_READ, 
        SHIPMENT_TRACK, PROMO_READ, WALLET_TOP_UP,
        WALLET_VIEW, TRANSACTION_VIEW, VERIFY_READ
    )),
    GUEST(Sets.newHashSet(
        PRODUCT_READ, PROMO_READ, SHIPMENT_TRACK, VERIFY_READ
    ));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> authorities = getPermissions()
                                                        .stream()
                                                        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                                                        .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+ this.name()));
        return authorities;
    }
}