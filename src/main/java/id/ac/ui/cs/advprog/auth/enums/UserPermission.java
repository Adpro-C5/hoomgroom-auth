package id.ac.ui.cs.advprog.auth.enums;

public enum UserPermission {
    // Auth dan manage akun
    ACCOUNT_CREATE("account:create"),
    ACCOUNT_READ("account:read"),
    ACCOUNT_UPDATE("account:update"),
    ACCOUNT_DELETE("account:delete"),
    
    // Manage produk
    PRODUCT_CREATE("product:create"),
    PRODUCT_READ("product:read"),
    PRODUCT_UPDATE("product:update"),
    PRODUCT_DELETE("product:delete"),
    
    // Manage promo
    PROMO_CREATE("promo:create"),
    PROMO_READ("promo:read"),
    PROMO_UPDATE("promo:update"),
    PROMO_DELETE("promo:delete"),
    
    // Manage pembelian
    PURCHASE_CREATE("purchase:create"),
    PURCHASE_READ("purchase:read"),
    PURCHASE_UPDATE("purchase:update"),
    
    // Manage pengiriman
    SHIPMENT_TRACK("shipment:track"),
    SHIPMENT_UPDATE("shipment:update"),
    
    // Manage wallet
    WALLET_TOP_UP("wallet:topup"),
    WALLET_VIEW("wallet:view"),
    
    // Manage stats
    STATS_VIEW("stats:view"),
    
    // Manage transaction
    TRANSACTION_VIEW("transaction:view"),
    TRANSACTION_MANAGE("transaction:manage"),
    
    VERIFY_READ("verify:read");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}