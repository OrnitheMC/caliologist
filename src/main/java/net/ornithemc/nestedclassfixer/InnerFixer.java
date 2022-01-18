package net.ornithemc.nestedclassfixer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InnerFixer extends Application
{
    private Scene scene;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InnerFixer.class.getResource("main-window.fxml"));
        fxmlLoader.setController(new MainWindowController(this));

        this.scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Nested Class Fixer");
        stage.setScene(scene);
        stage.show();
    }

    public Scene getScene() {
        return scene;
    }
}