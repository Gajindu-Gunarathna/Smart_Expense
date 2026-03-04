package com.smartbudget.ui;

import com.smartbudget.model.Expense;
import com.smartbudget.service.ExpenseManager;
import com.smartbudget.algorithms.PatternDetector;
import com.smartbudget.core.AppContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;

public class AnalyzeExpensesView {

    public static void show(Stage stage) {

        // ===== HEADER =====
        Label headerTitle = new Label("Expense Analysis");
        headerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        headerTitle.setTextFill(Color.WHITE);

        HBox header = new HBox(headerTitle);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #2E86C1;");

        // ===== TABLE =====
        TableView<Expense> table = new TableView<>();
        table.setEditable(false);
        table.setPrefHeight(300);

        TableColumn<Expense, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data -> data.getValue().categoryProperty());
        categoryCol.setPrefWidth(150);

        TableColumn<Expense, Number> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(data -> data.getValue().amountProperty());
        amountCol.setPrefWidth(120);

        TableColumn<Expense, Number> priorityCol = new TableColumn<>("Priority");
        priorityCol.setCellValueFactory(data -> data.getValue().priorityProperty());
        priorityCol.setPrefWidth(100);

        TableColumn<Expense, String> patternCol = new TableColumn<>("Pattern");
        patternCol.setCellValueFactory(data -> data.getValue().patternProperty());
        patternCol.setPrefWidth(200);

        table.getColumns().addAll(categoryCol, amountCol, priorityCol, patternCol);

        // ===== LOAD DATA =====
        ExpenseManager manager = AppContext.getExpenseManager();
        List<Expense> expenses = manager.getSortedExpenses();
        List<String> repeatedPatterns = PatternDetector.detectPatterns(expenses);

        ObservableList<Expense> observableList =
                FXCollections.observableArrayList(expenses);
        table.setItems(observableList);

        // ===== PATTERN SUMMARY AREA =====
        TextArea patternsArea = new TextArea();
        patternsArea.setEditable(false);
        patternsArea.setWrapText(true);
        patternsArea.setPrefHeight(120);

        if (repeatedPatterns.isEmpty()) {
            patternsArea.setText("No repeated spending patterns detected.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String s : repeatedPatterns) {
                sb.append("• ").append(s).append("\n");
            }
            patternsArea.setText(sb.toString());
        }

        // ===== BUTTON =====
        Button backBtn = new Button("Back to Dashboard");
        backBtn.setPrefWidth(200);
        backBtn.setStyle("""
                -fx-background-color: #2E86C1;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                """);

        backBtn.setOnMouseEntered(e ->
                backBtn.setStyle("""
                        -fx-background-color: #1B4F72;
                        -fx-text-fill: white;
                        -fx-font-weight: bold;
                        -fx-background-radius: 8;
                        """));

        backBtn.setOnMouseExited(e ->
                backBtn.setStyle("""
                        -fx-background-color: #2E86C1;
                        -fx-text-fill: white;
                        -fx-font-weight: bold;
                        -fx-background-radius: 8;
                        """));

        backBtn.setOnAction(e -> DashboardView.show(stage));

        // ===== CARD CONTENT =====
        VBox content = new VBox(15,
                new Label("Expense Table"),
                table,
                new Label("Repeated Spending Patterns"),
                patternsArea,
                backBtn
        );

        content.setPadding(new Insets(25));

        VBox card = new VBox(content);
        card.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 15;
                -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 15, 0, 0, 5);
                """);

        StackPane centerPane = new StackPane(card);
        centerPane.setPadding(new Insets(30));
        centerPane.setStyle("-fx-background-color: #F4F6F7;");

        // ===== ROOT =====
        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(centerPane);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Analyze Expenses");
        stage.show();
    }
}