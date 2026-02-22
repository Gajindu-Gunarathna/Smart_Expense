package com.smartbudget.ui;

import com.smartbudget.service.Group;
import com.smartbudget.model.User;
import com.smartbudget.core.AppContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GroupExpenseSummaryView {

    public static void show(Stage stage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label titleLabel = new Label("Group Expense Summary");

        // Get current group from context
        Group group = AppContext.getGroup();

        Label groupTypeLabel = new Label("Group Type: " + group.getGroupType());
        Label budgetLabel = new Label("Group Budget: " + group.getGroupBudget());
        Label perPersonLabel = new Label("Expected per-person share: " + group.getExpectedShare());

        // Table for individual member expenses
        TableView<User> table = new TableView<>();
        table.setEditable(false);

        TableColumn<User, String> nameCol = new TableColumn<>("Member Name");
        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());

        TableColumn<User, Number> spentCol = new TableColumn<>("Spent");
        spentCol.setCellValueFactory(data -> data.getValue().totalSpentProperty());

        TableColumn<User, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> data.getValue().statusProperty(group.getExpectedShare()));


        table.getColumns().addAll(nameCol, spentCol, statusCol);

        ObservableList<User> members = FXCollections.observableArrayList(group.getMembers());
        table.setItems(members);

        // Back button
        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> DashboardView.show(stage));

        // Add everything to layout
        layout.getChildren().addAll(titleLabel, groupTypeLabel, budgetLabel, perPersonLabel, table, backBtn);

        stage.setScene(new Scene(layout, 600, 400));
        stage.setTitle("Group Expense Summary");
        stage.show();
    }
}
