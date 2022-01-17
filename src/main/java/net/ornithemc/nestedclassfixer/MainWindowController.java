package net.ornithemc.nestedclassfixer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainWindowController
{
    @FXML
    private void handleOpenButton(ActionEvent event){
        System.out.println("Open button pressed");
    }
}
