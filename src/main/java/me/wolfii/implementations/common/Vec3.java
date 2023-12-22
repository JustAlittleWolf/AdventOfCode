package me.wolfii.implementations.common;

public record Vec3(int x, int y, int z) {
    public Vec3 plusZ(int z) {
        return new Vec3(x, y, this.z() + z);
    }
}
