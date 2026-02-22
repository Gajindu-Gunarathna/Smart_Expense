package com.smartbudget.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardView {

    public static void show(Stage stage) {

        Button addExpense = new Button("Add Expense");
        Button viewExpenses = new Button("View Expenses");
        Button analyze = new Button("Analyze Expenses");
        Button optimize = new Button("Optimize Budget");
        Button groupSummary = new Button("Group Summary");
        Button savings = new Button("Savings Goal");
        Button investment = new Button("Investment Suggestions");
        Button exit = new Button("Exit");

        addExpense.setOnAction(e -> AddExpenseView.show(stage));
        viewExpenses.setOnAction(e -> ViewExpensesView.show(stage));
        optimize.setOnAction(e -> OptimizeBudgetView.show(stage));
        savings.setOnAction(e -> SavingsGoalView.show(stage));
        investment.setOnAction(e -> InvestmentSuggestionsView.show(stage));
        analyze.setOnAction(e -> AnalyzeExpensesView.show(stage));
        groupSummary.setOnAction(e -> GroupExpenseSummaryView.show(stage));


        exit.setOnAction(e -> stage.close());

        VBox layout = new VBox(10,
                addExpense,
                viewExpenses,
                analyze,
                optimize,
                groupSummary,
                savings,
                investment,
                exit
        );

        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 300, 400));
        stage.setTitle("Dashboard");
        stage.show();
    }
}
