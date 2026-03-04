package com.smartbudget.ui;

import com.smartbudget.model.SavingsGoal;
import com.smartbudget.core.AppContext;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SavingsGoalView {

    public static void show(Stage stage) {

        // ===== HEADER =====
        Label headerTitle = new Label("Set a Savings Goal");
        headerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        headerTitle.setTextFill(Color.WHITE);

        HBox header = new HBox(headerTitle);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #2E86C1;");

        // ===== INPUT FIELDS =====
        TextField nameField = new TextField();
        nameField.setPromptText("Goal Name");

        TextField targetField = new TextField();
        targetField.setPromptText("Target Amount");

        TextField monthsField = new TextField();
        monthsField.setPromptText("Duration (months)");

        VBox inputBox = new VBox(15, nameField, targetField, monthsField);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(10));

        // ===== RESULT LABEL =====
        Label resultLabel = new Label();
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        resultLabel.setTextFill(Color.DARKGREEN);
        resultLabel.setWrapText(true);

        // ===== BUTTONS =====
        Button saveBtn = new Button("Save Goal");
        saveBtn.setPrefWidth(200);
        saveBtn.setStyle("""
                -fx-background-color: #28B463;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                """);

        saveBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            double target;
            int months;
            try {
                target = Double.parseDouble(targetField.getText().trim());
                months = Integer.parseInt(monthsField.getText().trim());
            } catch (NumberFormatException ex) {
                resultLabel.setTextFill(Color.RED);
                resultLabel.setText("Please enter valid numbers for target and duration.");
                return;
            }

            if (name.isEmpty() || target <= 0 || months <= 0) {
                resultLabel.setTextFill(Color.RED);
                resultLabel.setText("All fields must be filled with positive values.");
                return;
            }

            SavingsGoal goal = new SavingsGoal(name, target, months);
            AppContext.setSavingsGoal(goal);

            resultLabel.setTextFill(Color.DARKGREEN);
            resultLabel.setText("Goal '" + name + "' saved!\n" +
                    "Monthly saving required: " +
                    String.format("%.2f", goal.requiredMonthlySaving()));
        });

        Button backBtn = new Button("Back to Dashboard");
        backBtn.setPrefWidth(200);
        backBtn.setStyle("""
                -fx-background-color: #2E86C1;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                """);
        backBtn.setOnAction(e -> DashboardView.show(stage));

        VBox buttonsBox = new VBox(15, saveBtn, backBtn);
        buttonsBox.setAlignment(Pos.CENTER);

        // ===== MAIN CONTENT CARD =====
        VBox content = new VBox(25, inputBox, resultLabel, buttonsBox);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(30));

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

        Scene scene = new Scene(root, 600, 450);
        stage.setScene(scene);
        stage.setTitle("Savings Goal");
        stage.show();
    }
}