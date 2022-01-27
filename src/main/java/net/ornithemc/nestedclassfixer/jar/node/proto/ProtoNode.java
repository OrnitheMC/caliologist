package net.ornithemc.nestedclassfixer.jar.node.proto;

import java.util.Objects;

import net.ornithemc.nestedclassfixer.jar.JarFile;
import net.ornithemc.nestedclassfixer.jar.node.Node;

public abstract class ProtoNode
{
    protected final ProtoNode parent;

    protected final int access;
    protected final String name;
    protected final String signature;

    protected ProtoNode(ProtoNode parent, int access, String name, String signature) {
        this.parent = parent;

        this.access = access;
        this.name = name;
        this.signature = signature;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ProtoNode) {
            ProtoNode node = (ProtoNode)obj;
            return Objects.equals(parent, node.parent) && Objects.equals(name, node.name) && Objects.equals(signature, node.signature);
        }

        return false;
    }

    public boolean isClass() {
        return false;
    }

    public ProtoClassNode asClass() {
        throw new UnsupportedOperationException();
    }

    public boolean isField() {
        return false;
    }

    public ProtoFieldNode asField() {
        throw new UnsupportedOperationException();
    }

    public boolean isMethod() {
        return false;
    }

    public ProtoMethodNode asMethod() {
        throw new UnsupportedOperationException();
    }

    public boolean isVariable() {
        return false;
    }

    public ProtoVariableNode asVariable() {
        throw new UnsupportedOperationException();
    }

    public ProtoNode getParent() {
        return parent;
    }

    public int getAccess() {
        return access;
    }

    public String getName() {
        return name;
    }

    public String getSignature() {
        return signature;
    }

    public abstract Node construct(JarFile jar);

}
