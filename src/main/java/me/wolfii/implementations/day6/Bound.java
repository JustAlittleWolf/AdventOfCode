package me.wolfii.implementations.day6;

public enum Bound {
    LOWER(-1),
    UPPER(1);

    public final int signum;

    Bound(int signum) {
        this.signum = signum;
    }
}