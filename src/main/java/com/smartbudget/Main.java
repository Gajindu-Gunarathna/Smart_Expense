package com.smartbudget;


import javafx.application.Application;
import javafx.stage.Stage;
import com.smartbudget.ui.StartupView;
import com.smartbudget.ui.ConsoleMenu;

import static javafx.application.Application.launch;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application{
    @Override
    public void start(Stage stage) {
        StartupView.show(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}