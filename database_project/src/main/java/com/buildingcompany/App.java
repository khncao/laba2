package com.buildingcompany;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.buildingcompany.controllers.BuildEstimateController;
import com.buildingcompany.services.IXMLParser;
import com.buildingcompany.services.XMLParserSAXImpl;
import com.buildingcompany.views.BuildEstimateView;
import com.buildingcompany.views.LoginView;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene mainScene;
    // TODO(khncao): move to view manager service class if plan to expand
    private static LoginView loginView = new LoginView();
    private static BuildEstimateView buildEstimateView;

    @Override
    public void start(Stage stage) throws IOException {
        mainScene = new Scene(loginView.getParent(), 400, 400);
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
        if(buildEstimateView == null) {
            buildEstimateView = new BuildEstimateView(new BuildEstimateController());
        }
        setViewParent(buildEstimateView.getParent());
    }

    public static void main(String[] args) {
        IXMLParser xmlParser = new XMLParserSAXImpl();
        xmlParser.parse("BuildingType", true);
        xmlParser.parse("Address", true);
        xmlParser.parse("Material", true);
        xmlParser.parse("Tool", true);
        launch();
    }
}