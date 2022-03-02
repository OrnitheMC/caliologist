package net.ornithemc.caliologist.jar.node.proto;

import java.util.Objects;

import net.ornithemc.caliologist.jar.node.Node;

public abstract class ProtoNode
{
    protected final int access;
    protected final String name;
    protected final String signature;

    protected ProtoNode parent;
    protected Node node;

    protected ProtoNode(int access, String name, String signature) {
        this.access = access;
        this.name = name;
        this.signature = signature;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ProtoNode) {
            ProtoNode node = (ProtoNode) obj;
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

    public ProtoNode getParent() {
        return parent;
    }

    public void setParent(ProtoNode node) {
        if (isValidParent(node)) {
            parent = node;
        }
    }

    public boolean isValidParent(ProtoNode node) {
        return node != null && node != this && node.isValidChild(this);
    }

    public abstract boolean isValidChild(ProtoNode node);

    public int getAccess() {
        return access;
    }

    public String getName() {
        return name;
    }

    public String getSignature() {
        return signature;
    }

    public Node node() {
        if (node == null) {
            node = construct();

            if (parent != null) {
                Node parentNode = parent.node();
                node.setParent(parentNode);
            }
        }

        return node;
    }

    protected abstract Node construct();

}
