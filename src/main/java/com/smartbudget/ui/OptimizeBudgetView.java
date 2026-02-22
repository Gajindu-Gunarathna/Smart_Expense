package com.smartbudget.ui;

import com.smartbudget.core.AppContext;
import com.smartbudget.model.Expense;
import com.smartbudget.service.ExpenseManager;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class OptimizeBudgetView {

    public static void show(Stage stage) {
        ExpenseManager manager = AppContext.getExpenseManager();
        List<Expense> suggestions = manager.getOptimizationSuggestions();
        double total = manager.getTotalExpenses();
        double budget = manager.getBudget();

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label exceededLabel = new Label();
        if (total <= budget) {
            exceededLabel.setText("You are within the budget! Total: " + total);
        } else {
            exceededLabel.setText("Budget exceeded by: " + (total - budget));
        }
        layout.getChildren().add(exceededLabel);

        if (!suggestions.isEmpty()) {
            Label instructions = new Label("Select expenses to remove:");
            layout.getChildren().add(instructions);

            List<CheckBox> checkBoxes = new ArrayList<>();
            for (int i = 0; i < suggestions.size(); i++) {
                Expense e = suggestions.get(i);
                CheckBox cb = new CheckBox("[" + (i + 1) + "] " + e +
                        " | Reason: Low priority (P" + e.getPriority() +
                        ") & high savings (" + e.getAmount() + ")");
                checkBoxes.add(cb);
                layout.getChildren().add(cb);
            }

            Button removeBtn = new Button("Remove Selected");
            removeBtn.setOnAction(ev -> {
                for (int i = 0; i < checkBoxes.size(); i++) {
                    if (checkBoxes.get(i).isSelected()) {
                        manager.removeExpense(suggestions.get(i));
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Selected expenses removed!");
                alert.showAndWait();
                show(stage); // refresh the UI
            });
            layout.getChildren().add(removeBtn);
        }

        Button backBtn = new Button("Back");
        backBtn.setOnAction(ev -> DashboardView.show(stage));
        layout.getChildren().add(backBtn);

        stage.setScene(new Scene(layout, 600, 400));
        stage.setTitle("Optimize Budget");
        stage.show();
    }
}
