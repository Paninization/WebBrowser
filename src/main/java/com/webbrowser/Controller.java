package com.webbrowser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Control;

public class Controller {

    @FXML
    protected void onHelloButtonClick(ActionEvent event) {
        if (event.getSource() instanceof Control source) {
            String daa = (String) source.getUserData();
            System.out.println(daa);
        }
    }
}