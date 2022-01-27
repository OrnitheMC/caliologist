package net.ornithemc.nestedclassfixer.jar;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.ornithemc.nestedclassfixer.jar.node.ClassNode;
import net.ornithemc.nestedclassfixer.jar.node.UnknownClassNode;
import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoClassNode;
import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoFieldNode;
import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoMethodNode;
import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoVariableNode;

public class JarFile
{
    private final File file;

    private final Map<String, ProtoClassNode> protoClasses = new HashMap<>();
    private final Map<String, ClassNode> classes = new HashMap<>();

    public JarFile(File jarFile) {
        this.file = jarFile;
    }

    public File getFile() {
        return file;
    }

    public ProtoClassNode getProtoClass(String name) {
        return protoClasses.get(name);
    }

    public Collection<ClassNode> getClasses() {
        return classes.values();
    }

    public ClassNode getClass(String name) {
        return classes.get(name);
    }

    public void construct(Set<ProtoClassNode> protoClasses, Set<ProtoFieldNode> protoFields,
            Set<ProtoMethodNode> protoMethods, Set<ProtoVariableNode> protoVariables) {
        // first pass - creating class nodes
        for (ProtoClassNode protoClass : protoClasses) {
            this.protoClasses.put(protoClass.getName(), protoClass);

            ClassNode clazz = protoClass.construct(this);
            classes.put(clazz.getIdentifier(), clazz);
        }

        // second pass - adding references to the super class and interfaces
        for (ClassNode clazz : classes.values()) {
            ProtoClassNode protoClass = clazz.getProto();

            String superName = protoClass.getSuperName();
            String[] interfaceNames = protoClass.getInterfaces();

            ClassNode superClass = classes.get(superName);
            ClassNode[] interfaces = new ClassNode[interfaceNames.length];

            if (superClass == null) {
                superClass = new UnknownClassNode(superName);
            }
            for (int i = 0; i < interfaces.length; i++) {
                String interfaceName = interfaceNames[i];
                ClassNode interf = classes.get(interfaceName);

                if (interf == null) {
                    interf = new UnknownClassNode(interfaceName);
                }

                interfaces[i] = interf;
            }

            clazz.updateAncestry(superClass, interfaces);
        }

        for (ProtoFieldNode protoField : protoFields) {
            protoField.construct(this);
        }

        for (ProtoMethodNode protoMethod : protoMethods) {
            protoMethod.construct(this);
            // TODO: add references to variables
        }

        for (ProtoVariableNode protoVariable : protoVariables) {
            protoVariable.construct(this);
        }
    }
}
