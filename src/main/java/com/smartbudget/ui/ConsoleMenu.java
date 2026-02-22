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

        System.out.print("Enter monthly budget: ");
        double budget = sc.nextDouble();
        sc.nextLine();

        ExpenseManager manager = new ExpenseManager(budget);

        System.out.print("Living type (Single / Couple / Family / Roommates): ");
        String groupType = sc.nextLine();

        int members = 1;

        if (!groupType.equalsIgnoreCase("Single")) {
            System.out.print("Number of members: ");
            members = sc.nextInt();
            sc.nextLine();
        }

        Group group = new Group(groupType, budget, members);

        // Create users dynamically
        for (int i = 1; i <= members; i++) {
            group.addMember(new User("Member " + i));
        }

        SavingsGoal goal = null;

        while (true) {
            System.out.println("\n===== SMART EXPENSE CONTROL TOOL =====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Analyze Expenses");
            System.out.println("4. Optimize Budget");
            System.out.println("5. Group Expense Summary");
            System.out.println("6. Set Savings Goal");
            System.out.println("7. Investment Suggestions");
            System.out.println("8. Exit");

            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1 -> {
                    sc.nextLine();

                    System.out.print("Category: ");
                    String category = sc.nextLine();

                    System.out.print("Amount: ");
                    double amount = sc.nextDouble();

                    System.out.print("Priority (1=Important, 5=Least): ");
                    int priority = sc.nextInt();

                    Expense expense = new Expense(category, amount, priority);
                    manager.addExpense(expense);

                    int memberIndex = 0; // default for Single user

                    // Ask assignment ONLY if group has more than 1 member
                    if (members > 1) {
                        System.out.print("Assign to member (1 - " + members + "): ");
                        memberIndex = sc.nextInt() - 1;
                    }

                    group.getMembers()
                            .get(memberIndex)
                            .getExpenses()
                            .addExpense(expense);

                }

                case 2 -> manager.showExpenses();

                case 3 -> manager.analyzeExpenses();

                case 4 -> manager.optimizeBudgetWithConfirmation(sc);

                case 5 -> group.showGroupSummary();

                case 6 -> {
                    sc.nextLine();
                    System.out.print("Goal Name: ");
                    String name = sc.nextLine();

                    System.out.print("Target Amount: ");
                    double target = sc.nextDouble();

                    System.out.print("Duration (months): ");
                    int months = sc.nextInt();

                    goal = new SavingsGoal(name, target, months);
                    System.out.println("Monthly saving required: " +
                            goal.requiredMonthlySaving());
                }

                case 7 -> {
                    System.out.print("Disposable Income: ");
                    double income = sc.nextDouble();

                    sc.nextLine();
                    System.out.print("Risk Preference (Low/Medium/High): ");
                    String risk = sc.nextLine();

                    System.out.print("Goal Duration (months): ");
                    int duration = sc.nextInt();

                    InvestmentAdvisor.suggest(income, risk, duration);
                }

                case 8 -> {
                    System.out.println("Exiting system...");
                    return;
                }

                default -> System.out.println("Invalid option!");
            }
        }
    }
}
