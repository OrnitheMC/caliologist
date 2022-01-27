package net.ornithemc.nestedclassfixer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import net.ornithemc.nestedclassfixer.jar.NestedClassFixer;

import java.io.File;

public class MainWindowController
{
    private final Main mainApplication;

    public MainWindowController(Main mainApplication) {
        this.mainApplication = mainApplication;
    }

    @FXML
    private void handleOpenButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Minecraft Jar");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Minecraft Jar", "*.jar"));
        File file = fileChooser.showOpenDialog(mainApplication.getScene().getWindow());

        if (file == null) return;

        NestedClassFixer fixer = new NestedClassFixer(file);

        fixer.readJar();
        fixer.findNestedClassCandidates();
    }

    @FXML
    private void handleCloseButton(ActionEvent event) {
        Platform.exit();
    }
}
