package com.smartbudget.algorithms;

import com.smartbudget.model.Expense;

import java.util.*;

public class PatternDetector {

    // Detect patterns and also return repeated spending summary
    public static List<String> detectPatterns(List<Expense> expenses) {
        Map<String, Integer> categoryCount = new HashMap<>();
        List<String> repeatedPatterns = new ArrayList<>();

        for (Expense e : expenses) {
            String pattern = "";
            String cat = e.getCategory().toLowerCase();

            // Count occurrences for repeated spending
            categoryCount.put(cat, categoryCount.getOrDefault(cat, 0) + 1);

            if (cat.equals("food") && e.getAmount() > 200) {
                pattern = "Frequent/High Food Expense";
            } else if (cat.contains("subscription")) {
                pattern = "Multiple Subscriptions";
            } else if (e.getAmount() > 1000) {
                pattern = "High Impulse Spending";
            }

            e.setPattern(pattern.isEmpty() ? "None" : pattern);
        }

        // Build repeated patterns list
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            if (entry.getValue() > 1) {
                repeatedPatterns.add("Repeated Spending Patterns:\n" +
                        entry.getKey().substring(0,1).toUpperCase() + entry.getKey().substring(1) +
                        " → " + entry.getValue() + " times");
            }
        }

        return repeatedPatterns;
    }
}
