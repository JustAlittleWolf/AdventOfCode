package me.wolfii.implementations.day20;

import java.util.*;

public class Module {
    private final char type;
    private final List<String> destinations = new ArrayList<>();
    private State state = State.LOW;
    private final Map<String, State> fromStates = new HashMap<>();
    private static final List<String> NONE = List.of();

    public Module(String input) {
        String[] parts = input.split(" -> ");
        type = parts[0].charAt(0);

        destinations.addAll(List.of(parts[1].split(", ")));
    }

    public List<String> reveicePulse(State state, String from) {
        switch (type) {
            case 'b' -> {
                this.state = state;
                return destinations;
            }
            case '%' -> {
                if (state == State.HIGH) return NONE;
                this.state = this.state.inverted();
                return destinations;
            }
            case '&' -> {
                fromStates.put(from, state);
                this.state = State.HIGH;
                for (State fromState : this.fromStates.values()) {
                    if (fromState == State.LOW) return destinations;
                }
                this.state = State.LOW;
                return destinations;
            }
        }
        return NONE;
    }

    public void dryRun(String from) {
        this.fromStates.put(from, State.LOW);
    }

    public List<String> getDestinations() {
        return this.destinations;
    }

    public State getState() {
        return state;
    }
}
