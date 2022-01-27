package net.ornithemc.nestedclassfixer.jar.node;

import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoNode;

public class VariableNode extends Node
{
    public VariableNode(ProtoNode proto, Node parent, int access, String name, String signature) {
        super(proto, parent, access, name, signature);
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
    protected boolean isValidChild(Node node) {
        return false;
    }

    @Override
    public String getIdentifier() {
        return getParent().getIdentifier() + "@" + getName();
    }
}
