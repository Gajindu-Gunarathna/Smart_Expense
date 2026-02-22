package com.smartbudget.ui;

import com.smartbudget.core.AppContext;
import com.smartbudget.model.User;
import com.smartbudget.service.ExpenseManager;
import com.smartbudget.service.Group;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartupView {

    public static void show(Stage stage) {

        TextField budgetField = new TextField();
        ComboBox<String> groupTypeBox = new ComboBox<>();
        Spinner<Integer> memberSpinner = new Spinner<>(1, 10, 1);

        groupTypeBox.getItems().addAll("Single", "Couple", "Family", "Roommates");
        groupTypeBox.setValue("Single");

        memberSpinner.setDisable(true);

        groupTypeBox.setOnAction(e -> {
            boolean isSingle = groupTypeBox.getValue().equalsIgnoreCase("Single");
            memberSpinner.setDisable(isSingle);
        });

        Button startBtn = new Button("Start System");

        startBtn.setOnAction(e -> {
            try {
                double budget = Double.parseDouble(budgetField.getText());
                String groupType = groupTypeBox.getValue();
                int members = groupType.equalsIgnoreCase("Single")
                        ? 1
                        : memberSpinner.getValue();

                ExpenseManager manager = new ExpenseManager(budget);
                Group group = new Group(groupType, budget, members);

                for (int i = 1; i <= members; i++) {
                    group.addMember(new User("Member " + i));
                }

                AppContext.init(manager, group);
                DashboardView.show(stage);

            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Enter a valid budget").show();
            }
        });

        VBox layout = new VBox(10,
                new Label("Monthly Budget"),
                budgetField,
                new Label("Living Type"),
                groupTypeBox,
                new Label("Number of Members"),
                memberSpinner,
                startBtn
        );

        layout.setPadding(new Insets(20));
        stage.setScene(new Scene(layout, 320, 360));
        stage.setTitle("Smart Expense Control");
        stage.show();
    }
}
