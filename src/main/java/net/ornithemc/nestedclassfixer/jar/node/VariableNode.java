package net.ornithemc.nestedclassfixer.jar.node;

import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoVariableNode;

public class VariableNode extends Node
{
    public VariableNode(ProtoVariableNode proto, Node parent, int access, String name, String signature) {
        super(proto, parent, access, name, signature);
    }

    @Override
    public ProtoVariableNode getProto() {
        return proto.asVariable();
    }

    @Override
    public boolean isVariable() {
        return true;
    }

    @Override
    public VariableNode asVariable() {
        return this;
    }

    @Override
    protected boolean isValidParent(Node node) {
        return node != null && node.isMethod();
    }

    @Override
    protected boolean isValidChild(Node node) {
        return false;
    }
}
