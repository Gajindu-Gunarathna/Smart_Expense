package com.smartbudget.ui;

import com.smartbudget.service.Group;
import com.smartbudget.model.User;
import com.smartbudget.core.AppContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GroupExpenseSummaryView {

    public static void show(Stage stage) {

        Group group = AppContext.getGroup();

        // ===== HEADER =====
        Label headerTitle = new Label("Group Expense Summary");
        headerTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        headerTitle.setTextFill(Color.WHITE);

        HBox header = new HBox(headerTitle);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #2E86C1;");

        // ===== SUMMARY CARDS =====
        VBox groupTypeCard = createCard("Group Type", group.getGroupType());
        VBox budgetCard = createCard("Group Budget",
                String.format("%.2f", group.getGroupBudget()));
        VBox shareCard = createCard("Expected Per Person",
                String.format("%.2f", group.getExpectedShare()));

        HBox summaryRow = new HBox(20, groupTypeCard, budgetCard, shareCard);
        summaryRow.setAlignment(Pos.CENTER);

        // ===== TABLE =====
        TableView<User> table = new TableView<>();
        table.setPrefHeight(300);

        TableColumn<User, String> nameCol = new TableColumn<>("Member Name");
        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());
        nameCol.setPrefWidth(200);

        TableColumn<User, Number> spentCol = new TableColumn<>("Spent");
        spentCol.setCellValueFactory(data -> data.getValue().totalSpentProperty());
        spentCol.setPrefWidth(150);

        TableColumn<User, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data ->
                data.getValue().statusProperty(group.getExpectedShare()));
        statusCol.setPrefWidth(200);

        // Color status dynamically
        statusCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);

                    if (status.toLowerCase().contains("over")) {
                        setTextFill(Color.GREEN);
                    } else if (status.toLowerCase().contains("under")) {
                        setTextFill(Color.RED);
                    } else {
                        setTextFill(Color.BLACK);
                    }
                }
            }
        });

        table.getColumns().addAll(nameCol, spentCol, statusCol);

        ObservableList<User> members =
                FXCollections.observableArrayList(group.getMembers());
        table.setItems(members);

        // ===== BUTTON =====
        Button backBtn = new Button("Back to Dashboard");
        backBtn.setPrefWidth(200);
        backBtn.setStyle("""
                -fx-background-color: #2E86C1;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                """);

        backBtn.setOnMouseEntered(e ->
                backBtn.setStyle("""
                        -fx-background-color: #1B4F72;
                        -fx-text-fill: white;
                        -fx-font-weight: bold;
                        -fx-background-radius: 8;
                        """));

        backBtn.setOnMouseExited(e ->
                backBtn.setStyle("""
                        -fx-background-color: #2E86C1;
                        -fx-text-fill: white;
                        -fx-font-weight: bold;
                        -fx-background-radius: 8;
                        """));

        backBtn.setOnAction(e -> DashboardView.show(stage));

        // ===== CONTENT CARD =====
        VBox content = new VBox(25, summaryRow, table, backBtn);
        content.setPadding(new Insets(30));
        content.setAlignment(Pos.TOP_CENTER);

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

        Scene scene = new Scene(root, 850, 600);
        stage.setScene(scene);
        stage.setTitle("Group Expense Summary");
        stage.show();
    }

    // ===== Reusable Info Card =====
    private static VBox createCard(String title, String value) {

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        valueLabel.setTextFill(Color.DARKBLUE);

        VBox box = new VBox(8, titleLabel, valueLabel);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(220);

        box.setStyle("""
                -fx-background-color: #EBF5FB;
                -fx-background-radius: 12;
                """);

        return box;
    }
}