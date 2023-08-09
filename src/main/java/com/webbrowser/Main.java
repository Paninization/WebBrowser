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
    BorderPane root;
    Scene scene;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainGUI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 800, 600);

        WebView webView = (WebView) root.lookup("#webView");  // Trova la WebView nell'elemento radice
        //https://aperiodic-equipment.000webhostapp.com/
        //http://dcavallero.altervista.org/new/articoli/articoli.php
        // https://it.wikipedia.org/wiki/Front-end_e_back-end
        webView.getEngine().load("https://www.google.com");

        webView.getEngine().locationProperty().addListener((observable, oldValue, newValue) -> {
            String extension = getExtensionFromUrl(newValue);
            if (extension.equals(".fxml")) {
                try {
                    String xmlContent = downloadFxmlContentFromHttp(newValue);
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes());
                    FXMLLoader loader = new FXMLLoader();
                    Parent newRoot = loader.load(inputStream);
                    scene.setRoot(newRoot);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        stage.setTitle("JavaFX WebView Example");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }


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