package com.webbrowser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private BorderPane rootPane;

    @FXML
    private TextField urlTextField;

    @FXML
    private WebView webView;

    private WebEngine webEngine;
    private String currentURL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webView.getEngine().locationProperty().addListener((observable, oldValue, newValue) -> {
            currentURL = newValue;
            urlTextField.setText(currentURL);
        });
    }

    @FXML
    private void navigateButtonClicked(ActionEvent event) {
        currentURL = urlTextField.getText();
        if (!currentURL.startsWith("http://") && !currentURL.startsWith("https://"))
            currentURL = "https://" + currentURL;

        urlTextField.setText(currentURL);
        try {
            webView.getEngine().load(currentURL);
        } catch (Exception e) {
            currentURL = "https://www.google.com/search?q=" + currentURL;
            webView.getEngine().load(currentURL);
            urlTextField.setText(currentURL);
        }
    }

    public void historyBack() {
        webView.getEngine().getHistory().go(-1);
    }

    public void historyForward() {
        webView.getEngine().getHistory().go(+1);
    }

    public void setCurrentURL(String currentURL) {
        this.currentURL = currentURL;
    }
}