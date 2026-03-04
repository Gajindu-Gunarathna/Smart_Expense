package com.smartbudget.ui;

import com.smartbudget.core.AppContext;
import com.smartbudget.model.User;
import com.smartbudget.service.ExpenseManager;
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

public class StartupView {

    public static void show(Stage stage) {

        // ===== Title =====
        Label title = new Label("Smart Expense Control");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setTextFill(Color.DARKBLUE);

        // ===== Budget Field =====
        TextField budgetField = new TextField();
        budgetField.setPromptText("Enter Monthly Budget");

        // ===== Group Type =====
        ComboBox<String> groupTypeBox = new ComboBox<>();
        groupTypeBox.getItems().addAll("Single", "Couple", "Family", "Roommates");
        groupTypeBox.setValue("Single");

        // ===== Member Spinner =====
        Spinner<Integer> memberSpinner = new Spinner<>(1, 10, 2);
        memberSpinner.setDisable(true);

        groupTypeBox.setOnAction(e -> {
            boolean isSingle = groupTypeBox.getValue().equalsIgnoreCase("Single");
            memberSpinner.setDisable(isSingle);
        });

        // ===== Start Button =====
        Button startBtn = new Button("Start System");
        startBtn.setPrefWidth(200);
        startBtn.setStyle("""
                -fx-background-color: #2E86C1;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                """);

        startBtn.setOnMouseEntered(e ->
                startBtn.setStyle("""
                -fx-background-color: #1B4F72;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                """));

        startBtn.setOnMouseExited(e ->
                startBtn.setStyle("""
                -fx-background-color: #2E86C1;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                """));

        // ===== Button Logic =====
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

        // ===== Form Layout =====
        VBox form = new VBox(12,
                new Label("Monthly Budget"),
                budgetField,
                new Label("Living Type"),
                groupTypeBox,
                new Label("Number of Members"),
                memberSpinner,
                startBtn
        );

        form.setAlignment(Pos.CENTER_LEFT);

        // ===== Card Style Container =====
        VBox card = new VBox(20, title, form);
        card.setPadding(new Insets(25));
        card.setAlignment(Pos.CENTER);
        card.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 15;
                -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 15, 0, 0, 5);
                """);

        // ===== Root Layout =====
        StackPane root = new StackPane(card);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #F2F4F4;");

        Scene scene = new Scene(root, 400, 450);

        stage.setTitle("Smart Expense Control");
        stage.setScene(scene);
        stage.show();
    }
}