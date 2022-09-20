package com.buildingcompany.views;

import com.buildingcompany.App;
import com.buildingcompany.controllers.LoginController;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class LoginView {
    final Text displayMessage = new Text();
    final GridPane grid = new GridPane();

    public LoginView() {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        TextField dbPort = new TextField("3306");
        TextField dbName = new TextField("building_company");
        TextField userTextField = new TextField("root");
        PasswordField pwBox = new PasswordField();

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction((ActionEvent e) -> {
            if(LoginController.tryLogin(dbPort.getText(), dbName.getText(), userTextField.getText(), pwBox.getText())) {
                displayMessage.setText("Login success");
                App.showLoggedInView();
            } else {
                displayMessage.setText("Login failed");
            }
        });
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginBtn);

        grid.addRow(0, new Label("Port: "), dbPort);
        grid.addRow(1, new Label("DB name:"), dbName);
        grid.addRow(2, new Label("Username:"), userTextField);
        grid.addRow(3, new Label("Password:"), pwBox);

        grid.add(hbBtn, 1, 4);
        grid.add(displayMessage, 1, 5);
    }

    public Parent getParent() {
        return grid;
    }
}
