package com.smartbudget.service;

public class InvestmentAdvisor {

    public static void suggest(double income, String risk, int duration) {
        System.out.println("\n=== Investment Suggestions ===");
        System.out.println("Income: " + income + " | Risk: " + risk + " | Duration: " + duration + " months");

        switch (risk.toLowerCase()) {
            case "low" -> {
                System.out.println("• Fixed Deposits / Savings Accounts");
                System.out.println("• Government Bonds");
            }
            case "medium" -> {
                System.out.println("• Mutual Funds (Balanced)");
                System.out.println("• Index Funds");
            }
            case "high" -> {
                System.out.println("• Stock Market / Equities");
                System.out.println("• High-risk Mutual Funds");
            }
            default -> System.out.println("Invalid risk preference. Choose Low, Medium, or High.");
        }

        System.out.println("Recommendation: Spread investments according to risk and duration.");
    }
}
