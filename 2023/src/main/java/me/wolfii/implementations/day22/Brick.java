package me.wolfii.implementations.day22;

import me.wolfii.implementations.common.Vec3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Brick {
    private final Vec3 start;
    private final Vec3 end;
    public final Set<Brick> supportedBricks = new HashSet<>();
    public final Set<Brick> supportingBricks = new HashSet<>();

    public Brick(Vec3 start, Vec3 end) {
        this.start = start;
        this.end = end;
    }

    public static Brick of(String input) {
        String[] parts = input.split("~");
        String[] startCoordinates = parts[0].split(",");
        Vec3 start = new Vec3(Integer.parseInt(startCoordinates[0]), Integer.parseInt(startCoordinates[1]), Integer.parseInt(startCoordinates[2]));
        String[] endCoordinatse = parts[1].split(",");
        Vec3 end = new Vec3(Integer.parseInt(endCoordinatse[0]), Integer.parseInt(endCoordinatse[1]), Integer.parseInt(endCoordinatse[2]));
        return new Brick(start, end);
    }

    public Brick moveDown() {
        return new Brick(start.plusZ(-1), end.plusZ(-1));
    }

    public int getLength() {
        return Math.abs(start.x() - end.x()) + Math.abs(start.y() - end.y()) + Math.abs(start.z() - end.z());
    }

    public List<Vec3> getAllCubes() {
        List<Vec3> cubes = new ArrayList<>();
        for (int x = start.x(); x <= end.x(); x++) {
            for (int y = start.y(); y <= end.y(); y++) {
                for (int z = start.z(); z <= end.z(); z++) {
                    cubes.add(new Vec3(x, y, z));
                }
            }
        }
        return cubes;
    }

    public List<Vec3> getHorizontalCubes() {
        List<Vec3> cubes = new ArrayList<>();
        int z = getMaxHeight();
        for (int x = start.x(); x <= end.x(); x++) {
            for (int y = start.y(); y <= end.y(); y++) {
                cubes.add(new Vec3(x, y, z));
            }
        }
        return cubes;
    }

    public int getMaxHeight() {
        return Math.max(start.z(), end.z());
    }

    public int getMinHeight() {
        return Math.min(start.z(), end.z());
    }
}
