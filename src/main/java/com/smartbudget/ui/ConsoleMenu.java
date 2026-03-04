package com.smartbudget.ui;

import com.smartbudget.model.Expense;
import com.smartbudget.model.SavingsGoal;
import com.smartbudget.service.ExpenseManager;
import com.smartbudget.model.User;
import com.smartbudget.service.Group;
import com.smartbudget.service.InvestmentAdvisor;

import java.util.Scanner;

public class ConsoleMenu {

    public static void start() {

        Scanner sc = new Scanner(System.in);

        System.out.println("=====================================");
        System.out.println("     SMART EXPENSE CONTROL TOOL     ");
        System.out.println("=====================================");

        double budget = readDouble(sc, "Enter monthly budget: ");
        ExpenseManager manager = new ExpenseManager(budget);

        System.out.print("Living type (Single / Couple / Family / Roommates): ");
        String groupType = sc.nextLine();

        int members = 1;
        if (!groupType.equalsIgnoreCase("Single")) {
            members = readInt(sc, "Number of members: ");
        }

        Group group = new Group(groupType, budget, members);

        for (int i = 1; i <= members; i++) {
            group.addMember(new User("Member " + i));
        }

        SavingsGoal goal = null;

        while (true) {

            printMenu();

            int choice = readInt(sc, "Choose option: ");

            switch (choice) {

                case 1 -> addExpense(sc, manager, group, members);

                case 2 -> manager.showExpenses();

                case 3 -> manager.analyzeExpenses();

                case 4 -> manager.optimizeBudgetWithConfirmation(sc);

                case 5 -> group.showGroupSummary();

                case 6 -> {
                    goal = createSavingsGoal(sc);
                }

                case 7 -> suggestInvestment(sc);

                case 8 -> {
                    System.out.println("Exiting system...");
                    return;
                }

                default -> System.out.println("Invalid option!");
            }
        }
    }

    // ============================
    // Utility Methods
    // ============================

    private static void printMenu() {
        System.out.println("\n-------------------------------------");
        System.out.println("1. Add Expense");
        System.out.println("2. View Expenses");
        System.out.println("3. Analyze Expenses");
        System.out.println("4. Optimize Budget");
        System.out.println("5. Group Expense Summary");
        System.out.println("6. Set Savings Goal");
        System.out.println("7. Investment Suggestions");
        System.out.println("8. Exit");
        System.out.println("-------------------------------------");
    }

    private static void addExpense(Scanner sc, ExpenseManager manager,
                                   Group group, int members) {

        sc.nextLine();

        System.out.print("Category: ");
        String category = sc.nextLine();

        double amount = readDouble(sc, "Amount: ");
        int priority = readInt(sc, "Priority (1=Important, 5=Least): ");

        Expense expense = new Expense(category, amount, priority);
        manager.addExpense(expense);

        int memberIndex = 0;

        if (members > 1) {
            memberIndex = readInt(sc,
                    "Assign to member (1 - " + members + "): ") - 1;

            if (memberIndex < 0 || memberIndex >= members) {
                System.out.println("Invalid member. Assigned to Member 1.");
                memberIndex = 0;
            }
        }

        group.getMembers()
                .get(memberIndex)
                .getExpenses()
                .addExpense(expense);

        System.out.println("Expense added successfully!");
    }

    private static SavingsGoal createSavingsGoal(Scanner sc) {

        sc.nextLine();

        System.out.print("Goal Name: ");
        String name = sc.nextLine();

        double target = readDouble(sc, "Target Amount: ");
        int months = readInt(sc, "Duration (months): ");

        SavingsGoal goal = new SavingsGoal(name, target, months);

        System.out.println("Monthly saving required: "
                + goal.requiredMonthlySaving());

        return goal;
    }

    private static void suggestInvestment(Scanner sc) {

        double income = readDouble(sc, "Disposable Income: ");

        sc.nextLine();
        System.out.print("Risk Preference (Low/Medium/High): ");
        String risk = sc.nextLine();

        int duration = readInt(sc, "Goal Duration (months): ");

        InvestmentAdvisor.suggest(income, risk, duration);
    }

    // ============================
    // Safe Input Methods
    // ============================

    private static int readInt(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }
    }

    private static double readDouble(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Enter a valid amount.");
            }
        }
    }
}