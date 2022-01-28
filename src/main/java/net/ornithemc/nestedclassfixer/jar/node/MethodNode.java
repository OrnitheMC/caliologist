package net.ornithemc.nestedclassfixer.jar.node;

import net.ornithemc.nestedclassfixer.jar.node.desc.TypeDescriptor;
import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoMethodNode;

public class MethodNode extends Node
{
    private final TypeDescriptor descriptor;
    private final ClassNode[] exceptions;

    public MethodNode(ProtoMethodNode proto, ClassNode parent, int access, String name, String signature, TypeDescriptor descriptor, ClassNode[] exceptions) {
        super(proto, parent, access, name, signature);

        this.descriptor = descriptor;
        this.exceptions = exceptions;
    }

    @Override
    public ProtoMethodNode getProto() {
        return proto.asMethod();
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
    protected boolean isValidParent(Node node) {
        return node != null && node.isClass();
    }

    @Override
    protected boolean isValidChild(Node node) {
        return node instanceof VariableNode;
    }

    public TypeDescriptor getDescriptor() {
        return descriptor;
    }

    public ClassNode[] getExceptions() {
        return exceptions;
    }
}
