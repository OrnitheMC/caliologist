package net.ornithemc.caliologist.jar.node;

import net.ornithemc.caliologist.jar.node.proto.ProtoMethodNode;

public class MethodNode extends Node
{
    public MethodNode(ProtoMethodNode proto) {
        super(proto);
    }

    @Override
    public ProtoMethodNode proto() {
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
}
