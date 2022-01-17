module net.ornithemc.nestedclassfixer {
    requires javafx.controls;
    requires javafx.fxml;


    opens net.ornithemc.nestedclassfixer to javafx.fxml;
    exports net.ornithemc.nestedclassfixer;
}