package com.smartbudget.service;

import com.smartbudget.model.Expense;
import com.smartbudget.structures.ExpenseLinkedList;
import com.smartbudget.structures.ExpenseHeap;
import com.smartbudget.algorithms.QuickSortUtil;
import com.smartbudget.algorithms.PatternDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpenseManager {
    private ExpenseLinkedList list = new ExpenseLinkedList();
    private double budget;

    public ExpenseManager(double budget) {
        this.budget = budget;
    }

    // Add an expense to the linked list
    public void addExpense(Expense expense) {
        list.addExpense(expense);
    }

    // Return sorted list (Priority ASC → Amount DESC) for UI
    public List<Expense> getSortedExpenses() {
        List<Expense> copy = new ArrayList<>(list.toList());
        QuickSortUtil.quickSort(copy, 0, copy.size() - 1); // your custom QuickSort
        return copy;
    }

    // Console-friendly view (can be used in UI if needed)
    public void showExpenses() {
        List<Expense> expenses = getSortedExpenses(); // reuse sorted list

        System.out.println("Expenses (Sorted by Priority → Amount):");
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    // Analyze expenses: sorted + pattern detection
    public void analyzeExpenses() {
        List<Expense> expenses = new ArrayList<>(list.toList());
        QuickSortUtil.quickSort(expenses, 0, expenses.size() - 1);

        System.out.println("Expenses sorted by amount (for analysis):");
        for (Expense e : expenses) {
            System.out.println(e);
        }

        // Detect unnecessary/repetitive patterns
        PatternDetector.detectPatterns(expenses);
    }

    // Optimize budget with interactive removal
    public void optimizeBudgetWithConfirmation(Scanner sc) {
        double total = list.calculateTotal();

        if (total <= budget) {
            System.out.println("You are within the budget!");
            return;
        }

        double exceeded = total - budget;
        System.out.println("Budget exceeded by: " + exceeded);

        ExpenseHeap heap = new ExpenseHeap();
        for (Expense e : list.toList()) {
            heap.insert(e);
        }

        List<Expense> suggestions = new ArrayList<>();
        while (total > budget && !heap.isEmpty()) {
            Expense e = heap.extractMax();
            suggestions.add(e);
            total -= e.getAmount();
        }

        System.out.println("\nOptimization Suggestions:");
        for (int i = 0; i < suggestions.size(); i++) {
            Expense e = suggestions.get(i);
            System.out.println("[" + (i + 1) + "] Remove: " + e +
                    " | Reason: Low importance (priority " + e.getPriority() +
                    ") and high savings (" + e.getAmount() + ")");
        }

        System.out.print("\nEnter expense numbers to remove (comma separated), or 0 to cancel: ");
        sc.nextLine();
        String input = sc.nextLine();

        if (input.equals("0")) {
            System.out.println("No expenses were removed.");
            return;
        }

        String[] choices = input.split(",");

        for (String choice : choices) {
            int index = Integer.parseInt(choice.trim()) - 1;
            if (index >= 0 && index < suggestions.size()) {
                list.removeExpense(suggestions.get(index));
            }
        }

        System.out.println("Selected expenses removed successfully.");
        System.out.println("Updated total expenses: " + list.calculateTotal());
    }

    // Helper methods for UI usage
    public double getTotalExpenses() {
        return list.calculateTotal();
    }

    public double getBudget() {
        return budget;
    }

    public List<Expense> getOptimizationSuggestions() {
        double total = list.calculateTotal();
        List<Expense> suggestions = new ArrayList<>();
        if (total <= budget) return suggestions;

        ExpenseHeap heap = new ExpenseHeap();
        for (Expense e : list.toList()) {
            heap.insert(e);
        }

        while (total > budget && !heap.isEmpty()) {
            Expense e = heap.extractMax();
            suggestions.add(e);
            total -= e.getAmount();
        }
        return suggestions;
    }

    public void removeExpense(Expense expense) {
        list.removeExpense(expense);
    }

    // ExpenseManager.java
    public double calculateTotal() {
        return list.calculateTotal(); // calls the linked list to sum all expenses
    }

}
