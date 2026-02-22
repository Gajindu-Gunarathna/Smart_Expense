package com.smartbudget.ui;

import com.smartbudget.model.Expense;
import com.smartbudget.service.ExpenseManager;
import com.smartbudget.algorithms.PatternDetector;
import com.smartbudget.core.AppContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class AnalyzeExpensesView {

    public static void show(Stage stage) {
        VBox layout = new VBox(10); // create new VBox
        layout.setPadding(new Insets(20));

        Label titleLabel = new Label("Analyze Expenses"); // new Label each time

        TableView<Expense> table = new TableView<>();
        table.setEditable(false);

        TableColumn<Expense, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data -> data.getValue().categoryProperty());

        TableColumn<Expense, Number> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(data -> data.getValue().amountProperty());

        TableColumn<Expense, Number> priorityCol = new TableColumn<>("Priority");
        priorityCol.setCellValueFactory(data -> data.getValue().priorityProperty());

        TableColumn<Expense, String> patternCol = new TableColumn<>("Pattern");
        patternCol.setCellValueFactory(data -> data.getValue().patternProperty());

        table.getColumns().addAll(categoryCol, amountCol, priorityCol, patternCol);

        // Load data
        ExpenseManager manager = AppContext.getExpenseManager();
        List<Expense> expenses = manager.getSortedExpenses();

        // Detect patterns and get repeated spending summaries
        List<String> repeatedPatterns = PatternDetector.detectPatterns(expenses);

        ObservableList<Expense> observableList = FXCollections.observableArrayList(expenses);
        table.setItems(observableList);

        TextArea patternsArea = new TextArea();
        patternsArea.setEditable(false);
        patternsArea.setWrapText(true);
        if (repeatedPatterns.isEmpty()) {
            patternsArea.setText("No repeated spending patterns detected.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String s : repeatedPatterns) sb.append(s).append("\n");
            patternsArea.setText(sb.toString());
        }

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> DashboardView.show(stage)); // show dashboard again

        // Add **all nodes only once**
        layout.getChildren().addAll(titleLabel, table,
                new Label("Repeated Spending Patterns:"), patternsArea, backBtn);

        stage.setScene(new Scene(layout, 600, 400));
        stage.setTitle("Analyze Expenses");
        stage.show();
    }

}
