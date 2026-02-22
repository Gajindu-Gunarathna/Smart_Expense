package com.smartbudget.ui;

import com.smartbudget.model.SavingsGoal;
import com.smartbudget.core.AppContext;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SavingsGoalView {

    public static void show(Stage stage) {

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label titleLabel = new Label("Set a Savings Goal");

        TextField nameField = new TextField();
        nameField.setPromptText("Goal Name");

        TextField targetField = new TextField();
        targetField.setPromptText("Target Amount");

        TextField monthsField = new TextField();
        monthsField.setPromptText("Duration (months)");

        Button saveBtn = new Button("Save Goal");
        Label resultLabel = new Label();

        saveBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            double target;
            int months;
            try {
                target = Double.parseDouble(targetField.getText().trim());
                months = Integer.parseInt(monthsField.getText().trim());
            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter valid numbers for target and duration.");
                return;
            }

            if (name.isEmpty() || target <= 0 || months <= 0) {
                resultLabel.setText("All fields must be filled with positive values.");
                return;
            }

            SavingsGoal goal = new SavingsGoal(name, target, months);
            AppContext.setSavingsGoal(goal);

            resultLabel.setText("Goal '" + name + "' saved! Monthly saving required: " +
                    String.format("%.2f", goal.requiredMonthlySaving()));
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> DashboardView.show(stage));

        layout.getChildren().addAll(titleLabel, nameField, targetField, monthsField, saveBtn, resultLabel, backBtn);

        stage.setScene(new Scene(layout, 400, 300));
        stage.setTitle("Savings Goal");
        stage.show();
    }
}
