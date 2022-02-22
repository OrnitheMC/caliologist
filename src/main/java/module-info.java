module net.ornithemc.nestedclassfixer {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.objectweb.asm;


    opens net.ornithemc.caliologist to javafx.fxml;
    exports net.ornithemc.caliologist;
    exports net.ornithemc.caliologist.jar;
    exports net.ornithemc.caliologist.jar.node;
    exports net.ornithemc.caliologist.jar.node.desc;
    exports net.ornithemc.caliologist.jar.node.proto;
    opens net.ornithemc.caliologist.jar to javafx.fxml;
}