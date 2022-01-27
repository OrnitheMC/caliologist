package net.ornithemc.nestedclassfixer.jar.node;

import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoFieldNode;

public class FieldNode extends Node
{
    private final String desc;
    private final Object value;

    public FieldNode(ProtoFieldNode proto, ClassNode parent, int access, String name, String desc, String signature, Object value) {
        super(proto, parent, access, name, signature);

        this.desc = desc;
        this.value = value;
    }

    @Override
    public ProtoFieldNode getProto() {
        return super.getProto().asField();
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
    protected boolean isValidChild(Node node) {
        return false;
    }

    @Override
    public String getIdentifier() {
        return getParent().getIdentifier() + "#" + getName();
    }

    public String getDescriptor() {
        return desc;
    }

    public Object getValue() {
        return value;
    }
}
