package net.ornithemc.nestedclassfixer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;

public class MainWindowController
{
    private final InnerFixer mainApplication;

    public MainWindowController(InnerFixer mainApplication) {
        this.mainApplication = mainApplication;
    }

    @FXML
    private void handleOpenButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Minecraft Jar");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Minecraft Jar", "*.jar"));
        File file = fileChooser.showOpenDialog(mainApplication.getScene().getWindow());

        if (file == null) return;

        JarAnalyzer analyzer = new  JarAnalyzer(file);
    }
}
