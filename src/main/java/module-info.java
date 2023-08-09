module com.webbrowser {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.web;

    opens com.webbrowser to javafx.fxml;
    exports com.webbrowser;
}