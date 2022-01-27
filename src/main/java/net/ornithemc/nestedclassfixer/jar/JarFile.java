package net.ornithemc.nestedclassfixer.jar;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.ornithemc.nestedclassfixer.jar.node.ClassNode;
import net.ornithemc.nestedclassfixer.jar.node.FieldNode;
import net.ornithemc.nestedclassfixer.jar.node.MethodNode;
import net.ornithemc.nestedclassfixer.jar.node.UnknownClassNode;
import net.ornithemc.nestedclassfixer.jar.node.VariableNode;
import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoClassNode;
import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoFieldNode;
import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoMethodNode;
import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoNode;
import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoVariableNode;

public class JarFile
{
    private final File file;
    
    private final Map<String, ClassNode> classes = new HashMap<>();
    private final Map<String, FieldNode> fields = new HashMap<>();
    private final Map<String, MethodNode> methods = new HashMap<>();
    private final Map<String, VariableNode> variables = new HashMap<>();

    public JarFile(File jarFile) {
        this.file = jarFile;
    }

    public File getFile() {
        return file;
    }

    public Collection<ClassNode> getClasses() {
        return classes.values();
    }

    public ClassNode getClass(String name) {
        return classes.get(name);
    }

    public Collection<MethodNode> getMethods() {
        return methods.values();
    }

    public MethodNode getMethod(String signature) {
        return methods.get(signature);
    }

    public void construct(Set<ProtoClassNode> protoClasses, Set<ProtoFieldNode> protoFields, Set<ProtoMethodNode> protoMethods, Set<ProtoVariableNode> protoVariables) {
        // firs pass - creating class nodes
        for (ProtoClassNode protoClass : protoClasses) {
            ClassNode clazz = protoClass.construct(this);

            if (clazz != null) {
                classes.put(clazz.getIdentifier(), clazz);
            }
        }

        // second pass - adding references to the parent class, super class, and interfaces
        for (ClassNode clazz : classes.values()) {
            ProtoClassNode protoClass = clazz.getProto();
            ProtoNode protoParent = protoClass.getParent();

            String parentName = protoParent == null ? "" : protoParent.getName();
            String superName = protoClass.getSuperName();
            String[] interfaceNames = protoClass.getInterfaces();

            ClassNode parent = classes.get(parentName);
            ClassNode superClass = classes.get(superName);
            ClassNode[] interfaces = new ClassNode[interfaceNames.length];

            if (superClass == null) {
                superClass = new UnknownClassNode(superName);
            }
            for (int i = 0; i < interfaces.length; i++) {
                interfaces[i] = classes.get(interfaceNames[i]);
            }

            clazz.updateAncestry(parent, superClass, interfaces);
        }

        for (ProtoFieldNode protoField : protoFields) {
            FieldNode field = protoField.construct(this);

            if (field != null) {
                fields.put(field.getIdentifier(), field);
            }
        }

        // first pass - creating method nodes
        for (ProtoMethodNode protoMethod : protoMethods) {
            MethodNode method = protoMethod.construct(this);

            if (method != null) {
                methods.put(method.getIdentifier(), method);
            }
        }

        for (ProtoVariableNode protoVariable : protoVariables) {
            VariableNode variable = protoVariable.construct(this);

            if (variable != null) {
                variables.put(variable.getIdentifier(), variable);
            }
        }

        // second pass - adding references to variables
        for (MethodNode method : methods.values()) {
            // TODO: add references to variables
        }
    }
}
