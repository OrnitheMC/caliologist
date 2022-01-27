package net.ornithemc.nestedclassfixer.jar.node.proto;

import net.ornithemc.nestedclassfixer.jar.JarFile;
import net.ornithemc.nestedclassfixer.jar.node.ClassNode;

public class ProtoClassNode extends ProtoNode
{
    private final int version;
    private final String superName;
    private final String[] interfaces;

    public ProtoClassNode(ProtoNode parent, int version, int access, String name, String signature, String superName, String[] interfaces) {
        super(parent, access, name, signature);

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
    public ClassNode construct(JarFile jar) {
        return new ClassNode(this, access, name, signature);
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
