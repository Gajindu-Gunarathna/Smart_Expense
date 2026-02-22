package com.smartbudget.ui;

import com.smartbudget.core.AppContext;
import com.smartbudget.model.Expense;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ViewExpensesView {

    public static void show(Stage stage) {

        TableView<Expense> table = new TableView<>();

        TableColumn<Expense, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Expense, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Expense, Integer> priorityCol = new TableColumn<>("Priority");
        priorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));

        table.getColumns().addAll(categoryCol, amountCol, priorityCol);

        // Fetch sorted expenses from ExpenseManager
        List<Expense> sortedExpenses = AppContext.getExpenseManager().getSortedExpenses();

        table.setItems(FXCollections.observableArrayList(sortedExpenses));

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> DashboardView.show(stage));

        VBox layout = new VBox(10, table, backBtn);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 500, 400));
        stage.setTitle("View Expenses");
        stage.show();
    }
}
