package net.ornithemc.caliologist;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;

import net.ornithemc.caliologist.jar.JarFile;
import net.ornithemc.caliologist.jar.node.ClassNode;

public class MainWindowController
{
    private final Main main;

    @FXML
    private ListView<ClassNode> classFileBrowser;
    @FXML
    private TextArea classFileViewer;

    @FXML
    private MenuItem closeJarButton;

    private JarFile jar;
    private ClassNode clazz;

    public MainWindowController(Main main) {
        this.main = main;
    }

    @FXML
    private void openJar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Minecraft Jar");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Minecraft Jar", "*.jar"));

        File file = fileChooser.showOpenDialog(this.main.getScene().getWindow());

        if (file != null) {
            try {
                this.jar = new JarFile(file);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            this.listClasses();

            this.classFileBrowser.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            this.classFileBrowser.setCellFactory(list -> new ListCell<>()
            {

                @Override
                public void updateItem(ClassNode clazz, boolean empty) {
                    if (clazz != null) {
                        setText(clazz.getName());
                    }

                    super.updateItem(clazz, empty);
                }
            });
            this.classFileBrowser.setOnMouseClicked(clickEvent -> {
                if (clickEvent.getButton().equals(MouseButton.PRIMARY) && clickEvent.getClickCount() == 2) {
                    ClassNode clazz = this.classFileBrowser.getSelectionModel().getSelectedItem();

                    if (clazz != null && clazz != this.clazz) {
                        viewClass(clazz);
                    }
                }
            });
            this.closeJarButton.disableProperty().set(false);
        }
    }

    @FXML
    private void closeJar(ActionEvent event) {
        this.jar = null;
        this.clear();
    }

    private void clear() {
        this.classFileBrowser.getItems().clear();
        this.classFileViewer.clear();

        this.closeJarButton.disableProperty().set(true);
    }

    private void listClasses() {
        this.clear();

        for (ClassNode clazz : this.jar.getClasses()) {
            this.classFileBrowser.getItems().add(clazz);
        }
    }

    private void viewClass(ClassNode clazz) {
        this.classFileViewer.clear();

        if (clazz != null) {
            classFileViewer.appendText(jar.decompile(clazz));
        }
    }
}
