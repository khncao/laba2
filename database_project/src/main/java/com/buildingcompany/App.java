package com.buildingcompany;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.buildingcompany.controllers.BuildEstimateController;
import com.buildingcompany.entities.*;
import com.buildingcompany.services.IXMLParser;
import com.buildingcompany.services.XMLParserSAXImpl;
import com.buildingcompany.views.BuildEstimateView;
import com.buildingcompany.views.LoginView;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Logger logger = LogManager.getLogger(App.class);
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
        testXmlParser(new XMLParserSAXImpl());
        launch();
    }

    private static void testXmlParser(IXMLParser xmlParser) {
        boolean btypeValid = xmlParser.validate("BuildingType", "BuildingType");
        logger.info(btypeValid);

        var buildingTypes = xmlParser.parse("BuildingType", BuildingType.class);
        logger.info(buildingTypes.size() == 3);
        var addresses = xmlParser.parse("Address", Address.class);
        logger.info(addresses.size() == 4);
        var materials = xmlParser.parse("Material", Material.class);
        logger.info(materials.size() == 3);
        var tools = xmlParser.parse("Tool", Tool.class);
        logger.info(tools.size() == 3);
        logger.info("Tools:");
        tools.stream().forEach(t -> logger.info(t.toString()));
    }
}
