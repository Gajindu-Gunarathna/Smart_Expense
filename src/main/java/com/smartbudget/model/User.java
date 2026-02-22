// in User.java
package com.smartbudget.model;

import com.smartbudget.service.ExpenseManager;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private String name;
    private ExpenseManager expenses; // keep it private

    public User(String name) {
        this.name = name;
        this.expenses = new ExpenseManager(0); // initialize empty expense manager
    }

    public String getName() {
        return name;
    }

    public ExpenseManager getExpenses() {
        return expenses; // public getter
    }

    // JavaFX properties for TableView
    public SimpleStringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public SimpleDoubleProperty totalSpentProperty() {
        return new SimpleDoubleProperty(expenses.calculateTotal());
    }

    public SimpleStringProperty statusProperty(double expectedShare) {
        String status = expenses.calculateTotal() > expectedShare ? "⚠ Overspent" : "✔ Fair";
        return new SimpleStringProperty(status);
    }
}