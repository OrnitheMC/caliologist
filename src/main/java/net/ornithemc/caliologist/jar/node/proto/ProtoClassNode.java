package net.ornithemc.caliologist.jar.node.proto;

import net.ornithemc.caliologist.jar.node.ClassNode;

public class ProtoClassNode extends ProtoNode
{
    private final int version;
    private final String superName;
    private final String[] interfaces;

    public ProtoClassNode(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super(access, name, signature);

        this.version = version;
        this.superName = superName;
        this.interfaces = interfaces;
    }

    @Override
    public boolean isClass() {
        return true;
    }

    @Override
    public ProtoClassNode asClass() {
        return this;
    }

    @Override
    public boolean isValidParent(ProtoNode node) {
        return node == null || super.isValidParent(node);
    }

    @Override
    public boolean isValidChild(ProtoNode node) {
        return true;
    }

    @Override
    protected ClassNode construct() {
        return new ClassNode(this);
    }

    public int getVersion() {
        return version;
    }

    public String getSuperName() {
        return superName;
    }

    public String[] getInterfaces() {
        return interfaces;
    }
}
