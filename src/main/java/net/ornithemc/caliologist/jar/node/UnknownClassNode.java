package net.ornithemc.caliologist.jar.node;

import net.ornithemc.caliologist.jar.node.proto.ProtoClassNode;

public class UnknownClassNode extends ClassNode
{
    public UnknownClassNode(String name) {
        super(null, null, -1, name, "");
    }

    @Override
    public ProtoClassNode getProto() {
        return null;
    }
}
