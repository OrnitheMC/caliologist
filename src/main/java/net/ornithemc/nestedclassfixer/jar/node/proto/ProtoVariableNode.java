package net.ornithemc.nestedclassfixer.jar.node.proto;

import net.ornithemc.nestedclassfixer.jar.JarFile;
import net.ornithemc.nestedclassfixer.jar.node.MethodNode;
import net.ornithemc.nestedclassfixer.jar.node.VariableNode;

public class ProtoVariableNode extends ProtoNode
{
    public ProtoVariableNode(ProtoMethodNode parent, int access, String name, String signature) {
        super(parent, access, name, signature);
    }

    @Override
    public boolean isVariable() {
        return true;
    }

    @Override
    public ProtoVariableNode asVariable() {
        return this;
    }

    @Override
    public VariableNode construct(JarFile jar) {
        ProtoMethodNode protoParent = parent.asMethod();
        MethodNode parent = jar.getMethod(protoParent.signature);

        return new VariableNode(this, parent, access, name, signature);
    }
}
