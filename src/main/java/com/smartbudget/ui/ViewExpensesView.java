package com.smartbudget.ui;

import com.smartbudget.core.AppContext;
import com.smartbudget.model.Expense;
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

public class ViewExpensesView {

    public static void show(Stage stage) {

        // ===== HEADER =====
        Label headerTitle = new Label("All Expenses");
        headerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        headerTitle.setTextFill(Color.WHITE);

        HBox header = new HBox(headerTitle);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #2E86C1;");

        // ===== TABLE =====
        TableView<Expense> table = new TableView<>();
        table.setPrefHeight(350);

        TableColumn<Expense, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data -> data.getValue().categoryProperty());
        categoryCol.setPrefWidth(200);

        TableColumn<Expense, Number> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(data -> data.getValue().amountProperty());
        amountCol.setPrefWidth(150);

        // Format amount column
        amountCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", value.doubleValue()));
                }
            }
        });

        TableColumn<Expense, Number> priorityCol = new TableColumn<>("Priority");
        priorityCol.setCellValueFactory(data -> data.getValue().priorityProperty());
        priorityCol.setPrefWidth(120);

        // Color priority levels
        priorityCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty);
                if (empty || value == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(value.toString());

                    if (value.intValue() == 1) {
                        setTextFill(Color.RED);
                    } else if (value.intValue() <= 3) {
                        setTextFill(Color.ORANGE);
                    } else {
                        setTextFill(Color.GREEN);
                    }
                }
            }
        });

        table.getColumns().addAll(categoryCol, amountCol, priorityCol);

        // ===== LOAD DATA =====
        List<Expense> sortedExpenses =
                AppContext.getExpenseManager().getSortedExpenses();

        ObservableList<Expense> observableList =
                FXCollections.observableArrayList(sortedExpenses);

        table.setItems(observableList);

        // ===== TOTAL SPENDING =====
        double total = sortedExpenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        Label totalLabel = new Label("Total Spending: " +
                String.format("%.2f", total));
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        totalLabel.setTextFill(Color.DARKBLUE);

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

        // ===== CONTENT =====
        VBox content = new VBox(20, totalLabel, table, backBtn);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(30));

        VBox card = new VBox(content);
        card.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 15;
                -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 15, 0, 0, 5);
                """);

        StackPane centerPane = new StackPane(card);
        centerPane.setPadding(new Insets(30));
        centerPane.setStyle("-fx-background-color: #F4F6F7;");

        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(centerPane);

        Scene scene = new Scene(root, 750, 550);
        stage.setScene(scene);
        stage.setTitle("View Expenses");
        stage.show();
    }
}