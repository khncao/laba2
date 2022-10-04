package com.buildingcompany;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.buildingcompany.controllers.BuildEstimateController;
import com.buildingcompany.views.BuildEstimateView;
import com.buildingcompany.views.LoginView;

/**
 * JavaFX App
 */
public class App extends Application {
    private final static Logger logger = LogManager.getLogger(App.class);
    private static Scene mainScene;
    private static LoginView loginView = new LoginView();
    private static BuildEstimateController buildEstimateController;
    private static BuildEstimateView buildEstimateView;

    @Override
    public void start(Stage stage) throws IOException {
        buildEstimateController = new BuildEstimateController();
        buildEstimateView = new BuildEstimateView(buildEstimateController);
        buildEstimateView.addPropertyChangeListener(buildEstimateController);
        mainScene = new Scene(buildEstimateView.getParent(), 400, 400);
        stage.setTitle("Building Company");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void setViewParent(Parent parent) {
        mainScene.setRoot(parent);
    }

    public static void showLoginView() {
        setViewParent(loginView.getParent());
    }

    public static void showLoggedInView() {
        setViewParent(buildEstimateView.getParent());
    }

    public static void main(String[] args) {
        launch();
    }
}
