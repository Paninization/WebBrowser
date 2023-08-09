package com.webbrowser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.web.WebView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        WebView webView = new WebView();
        //https://aperiodic-equipment.000webhostapp.com/
        webView.getEngine().load("https://it.wikipedia.org/wiki/Front-end_e_back-end"); // Inserisci l'URL del sito web

        webView.getEngine().locationProperty().addListener((observable, oldValue, newValue) -> {

            String extension = getExtensionFromUrl(newValue);
            if (extension.equals(".fxml")) {
                try {
                    String xmlContent = downloadFxmlContentFromHttp(newValue);
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes());
                    FXMLLoader loader = new FXMLLoader();
                    Parent root = loader.load(inputStream);
                    scene = new Scene(root, 800, 600);
                    stage.setScene(scene);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        this.root = new BorderPane(webView);
        scene = new Scene(root, 800, 600);

        stage.setTitle("JavaFX WebView Example");
        stage.setScene(scene);
        stage.show();


    }

    BorderPane root;
    Scene scene;

    private String getExtensionFromUrl(String url) {
        try {
            URL parsedUrl = new URL(url);
            String path = parsedUrl.getPath();
            int lastDotIndex = path.lastIndexOf('.');
            if (lastDotIndex != -1) {
                return path.substring(lastDotIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    private String downloadFxmlContentFromHttp(String test) throws IOException {
        String url = "https://aperiodic-equipment.000webhostapp.com/test.fxml";

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }


    private void startTest(Stage stage) {
        String fxmlContent = null;
        try {
            fxmlContent = downloadFxmlContentFromHttp("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        ByteArrayInputStream inputStream = new ByteArrayInputStream(fxmlContent.getBytes());


        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        try {
            root = loader.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}