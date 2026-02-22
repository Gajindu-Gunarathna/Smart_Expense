package com.smartbudget.model;

import javafx.beans.property.*;

public class Expense {

    private StringProperty category;
    private DoubleProperty amount;
    private IntegerProperty priority;
    private StringProperty pattern;

    public Expense(String category, double amount, int priority) {
        this.category = new SimpleStringProperty(category);
        this.amount = new SimpleDoubleProperty(amount);
        this.priority = new SimpleIntegerProperty(priority);
        this.pattern = new SimpleStringProperty("");
    }

    // Getters and Setters for values
    public String getCategory() { return category.get(); }
    public void setCategory(String category) { this.category.set(category); }

    public double getAmount() { return amount.get(); }
    public void setAmount(double amount) { this.amount.set(amount); }

    public int getPriority() { return priority.get(); }
    public void setPriority(int priority) { this.priority.set(priority); }

    public String getPattern() { return pattern.get(); }
    public void setPattern(String pattern) { this.pattern.set(pattern); }

    // JavaFX property methods for TableView
    public StringProperty categoryProperty() { return category; }
    public DoubleProperty amountProperty() { return amount; }
    public IntegerProperty priorityProperty() { return priority; }
    public StringProperty patternProperty() { return pattern; }

    @Override
    public String toString() {
        return "Category: " + getCategory() +
                ", Amount: " + getAmount() +
                ", Priority: " + getPriority() +
                ", Pattern: " + getPattern();
    }




}