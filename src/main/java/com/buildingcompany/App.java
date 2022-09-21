package com.buildingcompany;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.buildingcompany.controllers.BuildEstimateController;
import com.buildingcompany.entities.*;
import com.buildingcompany.services.IJsonProcessor;
import com.buildingcompany.services.IXMLParser;
import com.buildingcompany.services.JsonJacksonImpl;
import com.buildingcompany.services.XMLParserJAXBImpl;
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
        // logger.info("SAX:");
        // testXmlParser(new XMLParserSAXImpl());
        logger.info("JAXB:");
        testXmlParser(new XMLParserJAXBImpl());
        logger.info("Jackson");
        testJsonProcessor(new JsonJacksonImpl());
        // launch();
    }

    private static List<Address> testAddresses = new ArrayList<>();
    private static List<Tool> testTools = new ArrayList<>();
    private static void testJsonProcessor(IJsonProcessor jsonProcessor) {
        Material material = new Material(4, "TestMat", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal("2.33"));
        material.getPerCountryAvgCostPerUnit().add(Map.entry("USA", new BigDecimal("9.99")));
        material.getPerCountryAvgCostPerUnit().add(Map.entry("Japan", new BigDecimal("12.99")));
        BuildingType buildingType = new BuildingType(19, "TestBuildingType1", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        buildingType.getRequiredMaterialAmounts().add(new BuildingTypeAmounts<Material>(material, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
        Company company = new Company(1, "TestCompany", testAddresses.get(0), "industry");
        
        jsonProcessor.write("Address", testAddresses);
        jsonProcessor.write("Material", Arrays.asList(material));
        jsonProcessor.write("BuildingType", Arrays.asList(buildingType));
        jsonProcessor.write("Company", Arrays.asList(company));
        jsonProcessor.write("Tool", testTools);

        List<Address> parsedAddresses = jsonProcessor.parse("Address", Address.class);
        List<Material> parsedMaterials = jsonProcessor.parse("Material", Material.class);
        List<BuildingType> parsedBuildingTypes = jsonProcessor.parse("BuildingType", BuildingType.class);
    }

    private static void testXmlParser(IXMLParser xmlParser) {
        boolean btypeValid = xmlParser.validate("BuildingType", "BuildingType", BuildingType.class);
        logger.info(btypeValid);
        boolean toolValid = xmlParser.validate("Tool", "Tool", Tool.class);
        logger.info(toolValid);

        var buildingTypes = xmlParser.parse("BuildingType", BuildingType.class);
        logger.info(buildingTypes.size() == 3);

        var addresses = xmlParser.parse("Address", Address.class);
        logger.info(addresses.size() == 4);

        var materials = xmlParser.parse("Material", Material.class);
        logger.info(materials.size() == 3);
        
        var tools = xmlParser.parse("Tool", Tool.class);
        logger.info(tools.size() == 3);
        tools.stream().forEach(t -> logger.info(t.toString()));

        testTools = tools;
        testAddresses = addresses;
    }
}
