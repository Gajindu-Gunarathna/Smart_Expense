package com.smartbudget.ui;

import com.smartbudget.core.AppContext;
import com.smartbudget.model.Expense;
import com.smartbudget.service.ExpenseManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class OptimizeBudgetView {

    public static void show(Stage stage) {

        ExpenseManager manager = AppContext.getExpenseManager();
        List<Expense> suggestions = manager.getOptimizationSuggestions();
        double total = manager.getTotalExpenses();
        double budget = manager.getBudget();
        double difference = total - budget;

        // ===== HEADER =====
        Label headerTitle = new Label("Budget Optimization");
        headerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        headerTitle.setTextFill(Color.WHITE);

        HBox header = new HBox(headerTitle);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #2E86C1;");

        // ===== SUMMARY CARDS =====
        VBox budgetCard = createCard("Budget",
                String.format("%.2f", budget));

        VBox totalCard = createCard("Total Expenses",
                String.format("%.2f", total));

        VBox statusCard;

        if (difference <= 0) {
            statusCard = createCard("Status",
                    "Within Budget ✔");
        } else {
            statusCard = createCard("Exceeded By",
                    String.format("%.2f", difference));
        }

        HBox summaryRow = new HBox(20, budgetCard, totalCard, statusCard);
        summaryRow.setAlignment(Pos.CENTER);

        // ===== SUGGESTIONS SECTION =====
        VBox suggestionsBox = new VBox(10);
        suggestionsBox.setPadding(new Insets(10));

        List<CheckBox> checkBoxes = new ArrayList<>();

        if (!suggestions.isEmpty()) {

            Label instructions = new Label("Suggested Expenses to Remove:");
            instructions.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            suggestionsBox.getChildren().add(instructions);

            for (Expense e : suggestions) {

                CheckBox cb = new CheckBox(
                        e.getCategory() +
                                " | Amount: " + e.getAmount() +
                                " | Priority: " + e.getPriority()
                );

                checkBoxes.add(cb);
                suggestionsBox.getChildren().add(cb);
            }
        } else {
            suggestionsBox.getChildren().add(
                    new Label("No optimization needed. You're managing well!")
            );
        }

        ScrollPane scrollPane = new ScrollPane(suggestionsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(250);

        // ===== REMOVE BUTTON =====
        Button removeBtn = new Button("Remove Selected");
        removeBtn.setPrefWidth(200);
        removeBtn.setStyle("""
                -fx-background-color: #C0392B;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                """);

        removeBtn.setOnAction(ev -> {
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    manager.removeExpense(suggestions.get(i));
                }
            }

            new Alert(Alert.AlertType.INFORMATION,
                    "Selected expenses removed!").showAndWait();

            show(stage); // refresh UI
        });

        // ===== BACK BUTTON =====
        Button backBtn = new Button("Back to Dashboard");
        backBtn.setPrefWidth(200);
        backBtn.setStyle("""
                -fx-background-color: #2E86C1;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                """);

        backBtn.setOnAction(ev -> DashboardView.show(stage));

        VBox buttons = new VBox(15, removeBtn, backBtn);
        buttons.setAlignment(Pos.CENTER);

        // ===== MAIN CONTENT =====
        VBox content = new VBox(25, summaryRow, scrollPane, buttons);
        content.setPadding(new Insets(30));
        content.setAlignment(Pos.TOP_CENTER);

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

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Optimize Budget");
        stage.show();
    }

    // ===== Reusable Card =====
    private static VBox createCard(String title, String value) {

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        valueLabel.setTextFill(Color.DARKBLUE);

        VBox box = new VBox(8, titleLabel, valueLabel);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(220);

        box.setStyle("""
                -fx-background-color: #EBF5FB;
                -fx-background-radius: 12;
                """);

        return box;
    }
}