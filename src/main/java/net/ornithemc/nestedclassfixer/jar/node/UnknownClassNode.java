package net.ornithemc.nestedclassfixer.jar.node;

import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoClassNode;

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
