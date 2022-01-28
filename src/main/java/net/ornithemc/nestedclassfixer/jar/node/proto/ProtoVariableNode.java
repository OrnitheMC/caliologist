package net.ornithemc.nestedclassfixer.jar.node.proto;

import net.ornithemc.nestedclassfixer.jar.JarFile;
import net.ornithemc.nestedclassfixer.jar.node.MethodNode;
import net.ornithemc.nestedclassfixer.jar.node.VariableNode;

public class ProtoVariableNode extends ProtoNode
{
    private VariableNode variable;

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
        if (variable == null) {
            ProtoMethodNode protoParent = parent.asMethod();
            MethodNode parent = protoParent.construct(jar);

            variable = new VariableNode(this, parent, access, name, signature);
        }

        return variable;
    }
}
