package com.smartbudget.ui;

import com.smartbudget.service.InvestmentAdvisor;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InvestmentSuggestionsView {

    public static void show(Stage stage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label titleLabel = new Label("Investment Suggestions");

        TextField incomeField = new TextField();
        incomeField.setPromptText("Disposable Income");

        ComboBox<String> riskBox = new ComboBox<>();
        riskBox.getItems().addAll("Low", "Medium", "High");
        riskBox.setPromptText("Select Risk Preference");

        TextField durationField = new TextField();
        durationField.setPromptText("Goal Duration (months)");

        Button suggestBtn = new Button("Get Suggestions");
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);

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

            // Capture console output of InvestmentAdvisor
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

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> DashboardView.show(stage));

        layout.getChildren().addAll(titleLabel, incomeField, riskBox, durationField, suggestBtn, resultArea, backBtn);

        stage.setScene(new Scene(layout, 450, 400));
        stage.setTitle("Investment Suggestions");
        stage.show();
    }
}
