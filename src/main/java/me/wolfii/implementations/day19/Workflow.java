package me.wolfii.implementations.day19;

import java.util.ArrayList;
import java.util.List;

public class Workflow {
    private final List<Rule> rules = new ArrayList<>();
    public Workflow(String section) {
        for(String rule : section.split(",")) {
            rules.add(new Rule(rule));
        }
    }

    public String testPart(Part part) {
        for(Rule rule : rules) {
            String result = rule.test(part);
            if(result == null) continue;
            return result;
        }
        return null;
    }
}
