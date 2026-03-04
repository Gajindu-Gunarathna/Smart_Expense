package com.smartbudget.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class DashboardView {

    public static void show(Stage stage) {

        // ===== Title =====
        Label title = new Label("Smart Expense Control");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        title.setTextFill(Color.WHITE);

        HBox header = new HBox(title);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #2E86C1;");

        // ===== Buttons =====
        Button addExpense = createButton("Add Expense");
        Button viewExpenses = createButton("View Expenses");
        Button analyze = createButton("Analyze Expenses");
        Button optimize = createButton("Optimize Budget");
        Button groupSummary = createButton("Group Summary");
        Button savings = createButton("Savings Goal");
        Button investment = createButton("Investment Suggestions");
        Button exit = createButton("Exit");

        // ===== Navigation =====
        addExpense.setOnAction(e -> AddExpenseView.show(stage));
        viewExpenses.setOnAction(e -> ViewExpensesView.show(stage));
        optimize.setOnAction(e -> OptimizeBudgetView.show(stage));
        savings.setOnAction(e -> SavingsGoalView.show(stage));
        investment.setOnAction(e -> InvestmentSuggestionsView.show(stage));
        analyze.setOnAction(e -> AnalyzeExpensesView.show(stage));
        groupSummary.setOnAction(e -> GroupExpenseSummaryView.show(stage));
        exit.setOnAction(e -> stage.close());

        // ===== Grid Layout =====
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(30));
        grid.setAlignment(Pos.CENTER);

        grid.add(addExpense, 0, 0);
        grid.add(viewExpenses, 1, 0);
        grid.add(analyze, 0, 1);
        grid.add(optimize, 1, 1);
        grid.add(groupSummary, 0, 2);
        grid.add(savings, 1, 2);
        grid.add(investment, 0, 3);
        grid.add(exit, 1, 3);

        // ===== Root Layout =====
        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(grid);
        root.setStyle("-fx-background-color: #F4F6F7;");

        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    // ===== Reusable Styled Button =====
    private static Button createButton(String text) {

        Button button = new Button(text);
        button.setPrefSize(220, 50);

        button.setStyle("""
                -fx-background-color: white;
                -fx-font-weight: bold;
                -fx-background-radius: 10;
                -fx-border-radius: 10;
                -fx-border-color: #D5D8DC;
                """);

        button.setOnMouseEntered(e ->
                button.setStyle("""
                        -fx-background-color: #2E86C1;
                        -fx-text-fill: white;
                        -fx-font-weight: bold;
                        -fx-background-radius: 10;
                        """));

        button.setOnMouseExited(e ->
                button.setStyle("""
                        -fx-background-color: white;
                        -fx-text-fill: black;
                        -fx-font-weight: bold;
                        -fx-background-radius: 10;
                        -fx-border-radius: 10;
                        -fx-border-color: #D5D8DC;
                        """));

        return button;
    }
}