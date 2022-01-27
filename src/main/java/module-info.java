module net.ornithemc.nestedclassfixer {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.objectweb.asm;


    opens net.ornithemc.nestedclassfixer to javafx.fxml;
    exports net.ornithemc.nestedclassfixer;
    exports net.ornithemc.nestedclassfixer.jar;
    exports net.ornithemc.nestedclassfixer.jar.node;
    exports net.ornithemc.nestedclassfixer.jar.node.proto;
    opens net.ornithemc.nestedclassfixer.jar to javafx.fxml;
}