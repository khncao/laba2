package com.buildingcompany.views;

import java.util.ArrayList;
import java.util.List;

import com.buildingcompany.controllers.BuildEstimateController;
import com.buildingcompany.entities.Address;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class BuildEstimateView {
    final Text displayMessage = new Text();
    final GridPane grid = new GridPane();
    private BuildEstimateController controller;
    private int rowIndex = 0;

    public BuildEstimateView(BuildEstimateController controller) {
        this.controller = controller;
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(2);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        TextField addrLine1 = new TextField();
        TextField addrLine2 = new TextField();
        TextField addrLine3 = new TextField();
        TextField country = new TextField();
        TextField city = new TextField();
        TextField zipCode = new TextField();

        ChoiceBox buildingTypeChoice = new ChoiceBox<>();
        // use array wrapper to modify from lambda
        int[] buildingTypeIndex = new int[] { -1 };
        List<String> buildingChoices = new ArrayList<String>(20);
        int choiceCount = controller.updateBuildingTypeChoices(buildingChoices);
        buildingTypeChoice.getItems().setAll(buildingChoices);
        buildingTypeChoice.getSelectionModel().selectedIndexProperty().addListener(
            (ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
                buildingTypeIndex[0] = (int)newVal;
                String bldgTypeName = buildingChoices.get(buildingTypeIndex[0]);
                // controller.testPrintReqResources(bldgTypeName);
            }
        );
        TextField sqrMetersText = new TextField();
        TextField numFloorsText = new TextField("1");

        Button submitBtn = new Button("Calculate estimate");
        submitBtn.setOnAction((ActionEvent ev) -> {
            String message = "";
            boolean isValid = true;
            String countryName = country.getText().trim();
            // TODO(khncao): better input validation
            if(countryName == null 
            || (!countryName.equalsIgnoreCase("usa") 
            && !countryName.equalsIgnoreCase("japan"))) {
                message += "Invalid country input. Only USA and Japan supported atm\n";
                isValid = false;
            }
            Address address = new Address(
                0,
                addrLine1.getText(),
                addrLine2.getText(),
                addrLine3.getText(),
                countryName,
                city.getText(),
                zipCode.getText()
            );
            String bldgTypeName = null;
            if(buildingTypeIndex[0] < 0 || buildingTypeIndex[0] > buildingChoices.size() - 1) {
                message += "Invalid building type: " + buildingTypeIndex[0] + '\n';
                isValid = false;
            } else {
                bldgTypeName = buildingChoices.get(buildingTypeIndex[0]);
            }
            float sqrMeters = 0f;
            try {
                sqrMeters = Float.parseFloat(sqrMetersText.getText());
            } catch(NumberFormatException e) {
                message += "Invalid square meter input\n";
                isValid = false;
            }
            int numFloors = 0;
            try {
                numFloors = Integer.parseInt(numFloorsText.getText());
            } catch(NumberFormatException e) {
                message += "Invalid number of floors input";
                isValid = false;
            }
            displayMessage.setText(message);
            if(!isValid) {
                return;
            }
            displayMessage.setText("Calculating time and cost");
            displayMessage.setText(controller.runCalculation(address, bldgTypeName, sqrMeters, numFloors));
        });

        // TODO(khncao): encapsulate address input form; fix layout with proper indentation
        Label descripLabel = new Label("Only usa and japan test data atm");
        GridPane.setColumnSpan(descripLabel, 2);
        addGridRow(descripLabel, new Label());
        addGridRow(new Label("Address:"), new Label());
        addGridRow(new Label("    Line 1:"), addrLine1);
        addGridRow(new Label("    Line 2:"), addrLine2);
        addGridRow(new Label("    Line 3:"), addrLine3);
        addGridRow(new Label("    *Country:"), country);
        addGridRow(new Label("    City:"), city);
        addGridRow(new Label("    Zipcode:"), zipCode);

        addGridRow(new Label("*Building type:"), buildingTypeChoice);
        addGridRow(new Label("*Foundation(M^2):"), sqrMetersText);
        addGridRow(new Label("*Number of floors:"), numFloorsText);
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(submitBtn);
        grid.add(hbBtn, 1, rowIndex);

        ScrollPane scrollPane = new ScrollPane(displayMessage);
        scrollPane.setMinWidth(scrollPane.getMaxWidth());
        scrollPane.setMinHeight(80d);
        GridPane.setColumnSpan(scrollPane, 2);
        grid.add(scrollPane, 0, rowIndex + 1);
    }

    private void addGridRow(Node label, Node field) {
        grid.addRow(rowIndex, label, field);
        rowIndex++;
    }

    public Parent getParent() {
        return grid;
    }
}
