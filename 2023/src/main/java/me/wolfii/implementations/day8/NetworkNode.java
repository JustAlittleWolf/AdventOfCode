package me.wolfii.implementations.day8;

public class NetworkNode {
    public final String leftNode;
    public final String rightNode;
    public final String name;
    public final boolean isGhostEndNode;
    public final boolean isTargetNode;

    public NetworkNode(String line) {
        String[] parts = line.split(" = ");
        name = parts[0];
        String nextNodes = parts[1].substring(1, parts[1].length() - 1);
        parts = nextNodes.split(", ");
        leftNode = parts[0];
        rightNode = parts[1];
        isGhostEndNode = name.endsWith("Z");
        isTargetNode = name.equals("ZZZ");
    }
}
