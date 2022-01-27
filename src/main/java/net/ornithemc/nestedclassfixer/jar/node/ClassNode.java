package net.ornithemc.nestedclassfixer.jar.node;

import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoClassNode;

public class ClassNode extends Node
{
    private ClassNode superClass;
    private ClassNode[] interfaces;

    public ClassNode(ProtoClassNode proto, int access, String name, String signature) {
        super(proto, null, access, name, signature);
    }

    @Override
    public ProtoClassNode getProto() {
        return super.getProto().asClass();
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
    protected boolean isValidChild(Node node) {
        return !(node instanceof VariableNode);
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

    public void updateAncestry(ClassNode parent, ClassNode superClass, ClassNode... interfaces) {
        this.setParent(parent);
        this.superClass = superClass;
        this.interfaces = interfaces;
    }
}
