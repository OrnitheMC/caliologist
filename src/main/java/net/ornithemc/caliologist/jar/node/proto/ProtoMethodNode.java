package net.ornithemc.caliologist.jar.node.proto;

import net.ornithemc.caliologist.jar.JarFile;
import net.ornithemc.caliologist.jar.node.ClassNode;
import net.ornithemc.caliologist.jar.node.MethodNode;
import net.ornithemc.caliologist.jar.node.UnknownClassNode;
import net.ornithemc.caliologist.jar.node.desc.TypeDescriptor;

public class ProtoMethodNode extends ProtoNode
{
    private final String desc;
    private final String[] exceptions;

    private MethodNode method;

    public ProtoMethodNode(ProtoClassNode parent, int access, String name, String desc, String signature, String[] exceptions) {
        super(parent, access, name, signature);

        this.desc = desc;
        this.exceptions = (exceptions == null) ? new String[0] : exceptions;
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
        if (method == null) {
            ProtoClassNode protoParent = parent.asClass();
            ClassNode parent = protoParent.construct(jar);

            TypeDescriptor descriptor = TypeDescriptor.construct(desc, jar);
            ClassNode[] exceptionTypes = new ClassNode[exceptions.length];

            for (int i = 0; i < exceptions.length; i++) {
                String name = exceptions[i];
                ClassNode exception = jar.getClass(name);

                if (exception == null) {
                    exception = new UnknownClassNode(name);
                }

                exceptionTypes[i] = exception;
            }

            method = new MethodNode(this, parent, access, name, signature, descriptor, exceptionTypes);
        }

        return method;
    }

    public String getDescriptor() {
        return desc;
    }

    public String[] getExceptions() {
        return exceptions;
    }
}
