package com.smartbudget.ui;

import com.smartbudget.core.AppContext;
import com.smartbudget.model.Expense;
import com.smartbudget.model.User;
import com.smartbudget.service.Group;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddExpenseView {

    public static void show(Stage stage) {

        TextField categoryField = new TextField();
        TextField amountField = new TextField();

        ComboBox<Integer> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll(1, 2, 3, 4, 5);
        priorityBox.setValue(1);

        Group group = AppContext.getGroup();

        ComboBox<Integer> memberBox = new ComboBox<>();

        int memberCount = group.getMembers().size();
        if (memberCount > 1) {
            for (int i = 1; i <= memberCount; i++) {
                memberBox.getItems().add(i);
            }
        }

        Button saveBtn = new Button("Save Expense");
        Button backBtn = new Button("Back");

        saveBtn.setOnAction(e -> {
            try {
                String category = categoryField.getText();
                double amount = Double.parseDouble(amountField.getText());
                int priority = priorityBox.getValue();

                Expense expense = new Expense(category, amount, priority);

                // Add to main manager
                AppContext.getExpenseManager().addExpense(expense);

                // Assign to member
                if (group.getMembers().size() == 1) {
                    group.getMembers().get(0).getExpenses().addExpense(expense);
                } else {
                    Integer selectedNumber = memberBox.getValue();

                    if (selectedNumber == null) {
                        new Alert(Alert.AlertType.ERROR,
                                "Please select a member number").show();
                        return;
                    }

                    int index = selectedNumber - 1;
                    group.getMembers().get(index).getExpenses().addExpense(expense);
                }


                new Alert(Alert.AlertType.INFORMATION,
                        "Expense added successfully").show();

                categoryField.clear();
                amountField.clear();
                priorityBox.setValue(3);

            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR,
                        "Enter a valid amount").show();
            }
        });

        backBtn.setOnAction(e -> DashboardView.show(stage));

        VBox layout = new VBox(10,
                new Label("Category"),
                categoryField,
                new Label("Amount"),
                amountField,
                new Label("Priority (1 = Important, 5 = Least)"),
                priorityBox
        );

        if (group.getMembers().size() > 1) {
            layout.getChildren().addAll(
                    new Label("Assign to Member"),
                    memberBox
            );
        }

        layout.getChildren().addAll(saveBtn, backBtn);

        layout.setPadding(new Insets(20));
        stage.setScene(new Scene(layout, 350, 420));
        stage.setTitle("Add Expense");
        stage.show();
    }
}
