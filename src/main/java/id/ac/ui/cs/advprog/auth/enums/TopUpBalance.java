package id.ac.ui.cs.advprog.auth.enums;

import lombok.Getter;

@Getter
public enum TopUpBalance{
    TEN(10000),
    TWENTYFIVE(25000),
    FIFTY(50000),
    ONEHUNDRED(100000);

    private final long value;

    private TopUpBalance(long value){
        this.value = value;
    }

    public static boolean contains(long param) {
        for (TopUpBalance balance : TopUpBalance.values()) {
            if (balance.value == param) {
                return true;
            }
        }
        return false;
    }
}