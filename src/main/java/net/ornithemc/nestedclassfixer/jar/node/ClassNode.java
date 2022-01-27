package net.ornithemc.nestedclassfixer.jar.node;

import java.util.HashMap;
import java.util.Map;

import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoClassNode;
import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoFieldNode;
import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoMethodNode;

public class ClassNode extends Node
{
    private ClassNode superClass;
    private ClassNode[] interfaces;

    private final Map<String, FieldNode> fields;
    private final Map<String, MethodNode> methods;

    public ClassNode(ProtoClassNode proto, ClassNode parent, int access, String name, String signature) {
        super(proto, null, access, name, signature);

        this.fields = new HashMap<>();
        this.methods = new HashMap<>();
    }

    @Override
    public ProtoClassNode getProto() {
        return proto.asClass();
    }

    @Override
    public boolean isClass() {
        return true;
    }

    @Override
    public ClassNode asClass() {
        return this;
    }

    @Override
    public boolean addChild(Node node) {
        if (super.addChild(node)) {
            if (node.isField()) {
                FieldNode field = node.asField();
                ProtoFieldNode proto = field.getProto();

                fields.put(proto.getName(), field);
            }
            if (node.isMethod()) {
                MethodNode method = node.asMethod();
                ProtoMethodNode proto = method.getProto();

                methods.put(proto.getName() + proto.getDescriptor(), method);
            }

            return true;
        }

        return false;
    }

    @Override
    protected boolean isValidChild(Node node) {
        return !(node instanceof VariableNode);
    }

    @Override
    public boolean removeChild(Node node) {
        if (super.removeChild(node)) {
            if (node.isField()) {
                FieldNode field = node.asField();
                ProtoFieldNode proto = field.getProto();

                fields.remove(proto.getName(), field);
            }
            if (node.isMethod()) {
                MethodNode method = node.asMethod();
                ProtoMethodNode proto = method.getProto();

                methods.remove(proto.getName() + proto.getDescriptor(), method);
            }

            return true;
        }

        return false;
    }

    @Override
    public String getIdentifier() {
        return getName();
    }

    public ClassNode getSuperClass() {
        return superClass;
    }

    public ClassNode[] getInterfaces() {
        return interfaces;
    }

    public void updateAncestry(ClassNode superClass, ClassNode... interfaces) {
        this.superClass = superClass;
        this.interfaces = interfaces;
    }

    public FieldNode getField(String name) {
        return fields.get(name);
    }

    public MethodNode getMethod(String name, String desc) {
        return methods.get(name + desc);
    }
}
