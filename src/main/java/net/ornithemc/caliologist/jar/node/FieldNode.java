package net.ornithemc.caliologist.jar.node;

import net.ornithemc.caliologist.jar.node.desc.TypeDescriptor;
import net.ornithemc.caliologist.jar.node.proto.ProtoFieldNode;

public class FieldNode extends Node
{
    private final TypeDescriptor descriptor;
    private final Object value;

    public FieldNode(ProtoFieldNode proto, ClassNode parent, int access, String name, String signature, TypeDescriptor descriptor, Object value) {
        super(proto, parent, access, name, signature);

        this.descriptor = descriptor;
        this.value = value;
    }

    @Override
    public ProtoFieldNode getProto() {
        return proto.asField();
    }

    @Override
    public boolean isField() {
        return true;
    }

    @Override
    public FieldNode asField() {
        return this;
    }

    @Override
    protected boolean isValidParent(Node node) {
        return node != null && node.isClass();
    }

    @Override
    protected boolean isValidChild(Node node) {
        return false;
    }

    public TypeDescriptor getDescriptor() {
        return descriptor;
    }

    public Object getValue() {
        return value;
    }
}
