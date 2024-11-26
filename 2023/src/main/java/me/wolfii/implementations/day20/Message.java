package me.wolfii.implementations.day20;

public record Message(String receiver, State state, String sender) {
    @Override
    public String toString() {
        return sender + " -" + state + "-> " + receiver;
    }
}
