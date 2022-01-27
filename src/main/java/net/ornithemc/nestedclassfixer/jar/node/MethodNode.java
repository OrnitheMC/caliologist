package net.ornithemc.nestedclassfixer.jar.node;

import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoMethodNode;

public class MethodNode extends Node
{
    private final String desc;
    private final String[] exceptions;

    public MethodNode(ProtoMethodNode proto, ClassNode parent, int access, String name, String desc, String signature, String[] exceptions) {
        super(proto, parent, access, name, signature);

        this.desc = desc;
        this.exceptions = exceptions;
    }

    @Override
    public ProtoMethodNode getProto() {
        return super.getProto().asMethod();
    }

    @Override
    public boolean isMethod() {
        return true;
    }

    @Override
    public MethodNode asMethod() {
        return this;
    }

    @Override
    protected boolean isValidChild(Node node) {
        return node instanceof VariableNode;
    }

    @Override
    public String getIdentifier() {
        return getParent().getIdentifier() + "#" + getName() + desc;
    }

    public String getDescriptor() {
        return desc;
    }
}
