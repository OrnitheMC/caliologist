package net.ornithemc.caliologist.jar.node.proto;

import net.ornithemc.caliologist.jar.node.MethodNode;

public class ProtoMethodNode extends ProtoNode
{
    private final String desc;
    private final String[] exceptions;

    public ProtoMethodNode(int access, String name, String desc, String signature, String[] exceptions) {
        super(access, name, signature);

        this.desc = desc;
        this.exceptions = exceptions;
    }

    @Override
    public boolean isMethod() {
        return true;
    }

    @Override
    public ProtoMethodNode asMethod() {
        return this;
    }

    @Override
    public boolean isValidChild(ProtoNode node) {
        return node.isClass();
    }

    @Override
    public MethodNode construct() {
        return new MethodNode(this);
    }

    public String getDescriptor() {
        return desc;
    }

    public String[] getExceptions() {
        return exceptions;
    }
}
