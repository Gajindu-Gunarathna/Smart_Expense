package com.smartbudget.ui;

import com.smartbudget.core.AppContext;
import com.smartbudget.model.Expense;
import com.smartbudget.service.Group;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AddExpenseView {

    public static void show(Stage stage) {

        Group group = AppContext.getGroup();

        // ===== Title =====
        Label title = new Label("Add New Expense");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.DARKBLUE);

        // ===== Form Fields =====
        TextField categoryField = new TextField();
        categoryField.setPromptText("Enter category (Food, Rent, etc)");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");

        ComboBox<Integer> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll(1, 2, 3, 4, 5);
        priorityBox.setValue(3);

        ComboBox<String> memberBox = new ComboBox<>();

        if (group.getMembers().size() > 1) {
            for (int i = 0; i < group.getMembers().size(); i++) {
                memberBox.getItems().add("Member " + (i + 1));
            }
        }

        // ===== Buttons =====
        Button saveBtn = createButton("Save Expense");
        Button backBtn = createButton("Back to Dashboard");

        // ===== Save Logic =====
        saveBtn.setOnAction(e -> {
            try {

                String category = categoryField.getText();
                double amount = Double.parseDouble(amountField.getText());
                int priority = priorityBox.getValue();

                if (category.isEmpty()) {
                    showError("Category cannot be empty");
                    return;
                }

                Expense expense = new Expense(category, amount, priority);
                AppContext.getExpenseManager().addExpense(expense);

                if (group.getMembers().size() == 1) {
                    group.getMembers().get(0).getExpenses().addExpense(expense);
                } else {

                    if (memberBox.getValue() == null) {
                        showError("Please select a member");
                        return;
                    }

                    int index = memberBox.getSelectionModel().getSelectedIndex();
                    group.getMembers().get(index).getExpenses().addExpense(expense);
                }

                showInfo("Expense added successfully!");

                categoryField.clear();
                amountField.clear();
                priorityBox.setValue(3);

            } catch (NumberFormatException ex) {
                showError("Enter a valid amount");
            }
        });

        backBtn.setOnAction(e -> DashboardView.show(stage));

        // ===== Form Layout =====
        VBox form = new VBox(12,
                new Label("Category"),
                categoryField,
                new Label("Amount"),
                amountField,
                new Label("Priority (1 = Important, 5 = Least)"),
                priorityBox
        );

        if (group.getMembers().size() > 1) {
            form.getChildren().addAll(
                    new Label("Assign to Member"),
                    memberBox
            );
        }

        form.getChildren().addAll(saveBtn, backBtn);
        form.setAlignment(Pos.CENTER_LEFT);

        // ===== Card Container =====
        VBox card = new VBox(20, title, form);
        card.setPadding(new Insets(25));
        card.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 15;
                -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 15, 0, 0, 5);
                """);

        // ===== Root =====
        StackPane root = new StackPane(card);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #F4F6F7;");

        Scene scene = new Scene(root, 450, 500);
        stage.setScene(scene);
        stage.setTitle("Add Expense");
        stage.show();
    }

    // ===== Reusable Styled Button =====
    private static Button createButton(String text) {

        Button button = new Button(text);
        button.setPrefWidth(220);

        button.setStyle("""
                -fx-background-color: #2E86C1;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                """);

        button.setOnMouseEntered(e ->
                button.setStyle("""
                        -fx-background-color: #1B4F72;
                        -fx-text-fill: white;
                        -fx-font-weight: bold;
                        -fx-background-radius: 8;
                        """));

        button.setOnMouseExited(e ->
                button.setStyle("""
                        -fx-background-color: #2E86C1;
                        -fx-text-fill: white;
                        -fx-font-weight: bold;
                        -fx-background-radius: 8;
                        """));

        return button;
    }

    private static void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }

    private static void showInfo(String message) {
        new Alert(Alert.AlertType.INFORMATION, message).show();
    }
}