package com.smartbudget.model;

public class SavingsGoal {
    private String name;
    private double targetAmount;
    private int months;

    public SavingsGoal(String name, double targetAmount, int months) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.months = months;
    }

    public String getName() {
        return name;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public int getMonths() {
        return months;
    }

    public double requiredMonthlySaving() {
        return targetAmount / months;
    }
}
