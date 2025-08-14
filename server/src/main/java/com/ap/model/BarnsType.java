package com.ap.model;

public enum BarnsType {
    Barn(true, 4),
    BigBarn(true, 8),
    DeluxeBarn(true, 12),
    Coop(false, 4),
    BigCoop(false, 8),
    DeluxeCoop(false, 12),
    ;

    private boolean isBarn;
    private int capacity;

    BarnsType(boolean isBarn, int capacity) {
        this.isBarn = isBarn;
        this.capacity = capacity;
    }

    public boolean isBarn() {
        return isBarn;
    }

    public int getCapacity() {
        return capacity;
    }
}
