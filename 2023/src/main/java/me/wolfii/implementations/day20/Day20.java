package me.wolfii.implementations.day20;

import me.wolfii.automation.Solution;
import me.wolfii.implementations.common.MathUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Day20 implements Solution {
    public void solveFirst(List<String> lines) {
        Map<String, Module> modules = getModules(lines);
        dryRun(modules);

        AtomicInteger lowCount = new AtomicInteger(0);
        AtomicInteger highCount = new AtomicInteger(0);
        for (int i = 0; i < 1000; i++) {
            pressButton(modules, (message) -> {
                switch (message.state()) {
                    case HIGH -> highCount.getAndIncrement();
                    case LOW -> lowCount.getAndIncrement();
                }
            });
        }
        System.out.println("Part 1: " + lowCount.get() * highCount.get());
    }

    public void solveSecond(List<String> lines) {
        Map<String, Module> modules = getModules(lines);
        dryRun(modules);

        String endNode = null;
        for (Map.Entry<String, Module> entry : modules.entrySet()) {
            if (entry.getValue().getDestinations().contains("rx")) endNode = entry.getKey();
        }
        if (endNode == null) {
            System.out.println("Part 2: No node called \"rx\" found");
            return;
        }

        HashMap<String, Integer> cycleDurations = new HashMap<>();
        String finalNode = endNode;
        int count = 0;
        int cycles = modules.get("roadcaster").getDestinations().size();
        while (cycleDurations.size() < cycles) {
            int finalCount = ++count;
            pressButton(modules, (message) -> {
                if (message.state() == State.LOW) return;
                if (!message.receiver().equals(finalNode)) return;
                if (cycleDurations.containsKey(message.sender())) return;
                cycleDurations.put(message.sender(), finalCount);
            });
        }

        System.out.println("Part 2: " + MathUtil.lcm(cycleDurations.values()));
    }

    private void dryRun(Map<String, Module> modules) {
        for (String key : modules.keySet()) {
            List<String> calledModules = modules.get(key).getDestinations();
            for (String calledModule : calledModules) {
                if (!modules.containsKey(calledModule)) continue;
                modules.get(calledModule).dryRun(key);
            }
        }
    }

    private Map<String, Module> getModules(List<String> lines) {
        Map<String, Module> modules = new HashMap<>();
        for (String line : lines) {
            modules.put(line.split(" -> ")[0].substring(1), new Module(line));
        }
        return modules;
    }

    private void pressButton(Map<String, Module> modules, Consumer<Message> onMessage) {
        Deque<Message> processNext = new ArrayDeque<>();
        processNext.add(new Message("roadcaster", State.LOW, "none"));
        while (!processNext.isEmpty()) {
            Message message = processNext.pollFirst();
            onMessage.accept(message);
            Module module = modules.get(message.receiver());
            if (module == null) continue;
            List<String> calledModules = module.reveicePulse(message.state(), message.sender());
            for (String calledModule : calledModules) {
                processNext.add(new Message(calledModule, module.getState(), message.receiver()));
            }
        }
    }
}
