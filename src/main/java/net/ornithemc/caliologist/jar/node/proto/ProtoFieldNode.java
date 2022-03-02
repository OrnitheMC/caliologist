package net.ornithemc.caliologist.jar.node.proto;

import net.ornithemc.caliologist.jar.node.FieldNode;

public class ProtoFieldNode extends ProtoNode
{
    private final String desc;
    private final Object value;

    public ProtoFieldNode(int access, String name, String desc, String signature, Object value) {
        super(access, name, signature);

        this.desc = desc;
        this.value = value;
    }

    @Override
    public boolean isField() {
        return true;
    }

    @Override
    public ProtoFieldNode asField() {
        return this;
    }

    @Override
    public boolean isValidChild(ProtoNode node) {
        return false;
    }

    @Override
    protected FieldNode construct() {
        return new FieldNode(this);
    }

    public String getDescriptor() {
        return desc;
    }

    public Object getValue() {
        return value;
    }
}
