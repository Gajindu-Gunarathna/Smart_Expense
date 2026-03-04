package com.smartbudget.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class InvestmentSuggestionsView {

    public static void show(Stage stage) {

        // ===== HEADER =====
        Label headerTitle = new Label("Investment Suggestions");
        headerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        headerTitle.setTextFill(Color.WHITE);

        HBox header = new HBox(headerTitle);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #2E86C1;");

        // ===== INPUT FIELDS =====
        TextField incomeField = new TextField();
        incomeField.setPromptText("Disposable Income");

        ComboBox<String> riskBox = new ComboBox<>();
        riskBox.getItems().addAll("Low", "Medium", "High");
        riskBox.setPromptText("Select Risk Preference");

        TextField durationField = new TextField();
        durationField.setPromptText("Goal Duration (months)");

        VBox inputBox = new VBox(15, incomeField, riskBox, durationField);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(10));

        // ===== RESULT AREA =====
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setPrefHeight(180);

        // ===== BUTTONS =====
        Button suggestBtn = new Button("Get Suggestions");
        suggestBtn.setPrefWidth(200);
        suggestBtn.setStyle("""
                -fx-background-color: #28B463;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                """);

        suggestBtn.setOnAction(e -> {
            double income;
            int duration;
            try {
                income = Double.parseDouble(incomeField.getText().trim());
                duration = Integer.parseInt(durationField.getText().trim());
            } catch (NumberFormatException ex) {
                resultArea.setText("Please enter valid numbers for income and duration.");
                return;
            }

            String risk = riskBox.getValue();
            if (risk == null) {
                resultArea.setText("Please select a risk preference.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Income: ").append(income)
                    .append(" | Risk: ").append(risk)
                    .append(" | Duration: ").append(duration).append(" months\n\n");

            switch (risk.toLowerCase()) {
                case "low" -> {
                    sb.append("• Fixed Deposits / Savings Accounts\n");
                    sb.append("• Government Bonds\n");
                }
                case "medium" -> {
                    sb.append("• Mutual Funds (Balanced)\n");
                    sb.append("• Index Funds\n");
                }
                case "high" -> {
                    sb.append("• Stock Market / Equities\n");
                    sb.append("• High-risk Mutual Funds\n");
                }
            }
            sb.append("\nRecommendation: Spread investments according to risk and duration.");

            resultArea.setText(sb.toString());
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

        VBox buttonsBox = new VBox(15, suggestBtn, backBtn);
        buttonsBox.setAlignment(Pos.CENTER);

        // ===== MAIN CONTENT CARD =====
        VBox content = new VBox(20, inputBox, resultArea, buttonsBox);
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

        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Investment Suggestions");
        stage.show();
    }
}