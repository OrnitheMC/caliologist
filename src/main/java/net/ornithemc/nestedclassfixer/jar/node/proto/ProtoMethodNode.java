package net.ornithemc.nestedclassfixer.jar.node.proto;

import net.ornithemc.nestedclassfixer.jar.JarFile;
import net.ornithemc.nestedclassfixer.jar.node.ClassNode;
import net.ornithemc.nestedclassfixer.jar.node.MethodNode;

public class ProtoMethodNode extends ProtoNode
{
    private final String desc;
    private final String[] exceptions;

    public ProtoMethodNode(ProtoClassNode parent, int access, String name, String desc, String signature, String[] exceptions) {
        super(parent, access, name, signature);

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
    public MethodNode construct(JarFile jar) {
        ProtoClassNode protoParent = parent.asClass();
        ClassNode parent = jar.getClass(protoParent.name);

        return new MethodNode(this, parent, access, name, desc, signature, exceptions);
    }

    public String getDescriptor() {
        return desc;
    }

    public String[] getExceptions() {
        return exceptions;
    }
}
