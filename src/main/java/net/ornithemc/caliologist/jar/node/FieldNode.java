package net.ornithemc.caliologist.jar.node;

import net.ornithemc.caliologist.jar.node.proto.ProtoFieldNode;

public class FieldNode extends Node
{
    public FieldNode(ProtoFieldNode proto) {
        super(proto);
    }

    @Override
    public ProtoFieldNode proto() {
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
}
