package net.ornithemc.nestedclassfixer.jar.node;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.objectweb.asm.Opcodes;

import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoNode;

public abstract class Node
{
    private final ProtoNode proto;

    private Node parent;
    private Set<Node> children;

    private int access;
    private String name;
    private String signature;

    protected Node(ProtoNode proto, Node parent, int access, String name, String signature) {
        this.proto = proto;

        this.parent = parent;
        this.children = new LinkedHashSet<>();

        this.access = access;
        this.name = name;
        this.signature = signature;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Node) {
            Node node = (Node)obj;
            return Objects.equals(parent, node.parent) && Objects.equals(name, node.name) && Objects.equals(signature, node.signature);
        }

        return false;
    }

    public ProtoNode getProto() {
        return proto;
    }

    public boolean isClass() {
        return false;
    }

    public ClassNode asClass() {
        throw new UnsupportedOperationException();
    }

    public boolean isField() {
        return false;
    }

    public FieldNode asField() {
        throw new UnsupportedOperationException();
    }

    public boolean isMethod() {
        return false;
    }

    public MethodNode asMethod() {
        throw new UnsupportedOperationException();
    }

    public boolean isVariable() {
        return false;
    }

    public VariableNode asVariable() {
        throw new UnsupportedOperationException();
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node node) {
        this.parent = node;
    }

    public Set<Node> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    public boolean addChild(Node node) {
       return isValidChild(node) && children.add(node);
    }

    protected abstract boolean isValidChild(Node node);

    public boolean removeChild(Node node) {
        return children.remove(node);
    }

    public int getAccess() {
        return access;
    }

    public boolean isPublic() {
        return (access & Opcodes.ACC_PUBLIC) != 0;
    }

    public boolean isPrivate() {
        return (access & Opcodes.ACC_PRIVATE) != 0;
    }

    public boolean isProtected() {
        return (access & Opcodes.ACC_PROTECTED) != 0;
    }

    public boolean isStatic() {
        return (access & Opcodes.ACC_STATIC) != 0;
    }

    public boolean isFinal() {
        return (access & Opcodes.ACC_FINAL) != 0;
    }

    public boolean isSuper() {
        return (access & Opcodes.ACC_SUPER) != 0;
    }

    public boolean isSynchronized() {
        return (access & Opcodes.ACC_SYNCHRONIZED) != 0;
    }

    public boolean isOpen() {
        return (access & Opcodes.ACC_OPEN) != 0;
    }

    public boolean isTransitive() {
        return (access & Opcodes.ACC_TRANSITIVE) != 0;
    }

    public boolean isVolatile() {
        return (access & Opcodes.ACC_VOLATILE) != 0;
    }

    public boolean isBridge() {
        return (access & Opcodes.ACC_BRIDGE) != 0;
    }

    public boolean isStaticPhase() {
        return (access & Opcodes.ACC_STATIC_PHASE) != 0;
    }

    public boolean isVarargs() {
        return (access & Opcodes.ACC_VARARGS) != 0;
    }

    public boolean isTransient() {
        return (access & Opcodes.ACC_TRANSIENT) != 0;
    }

    public boolean isNative() {
        return (access & Opcodes.ACC_NATIVE) != 0;
    }

    public boolean isInterface() {
        return (access & Opcodes.ACC_INTERFACE) != 0;
    }

    public boolean isAbstract() {
        return (access & Opcodes.ACC_ABSTRACT) != 0;
    }

    public boolean isStrict() {
        return (access & Opcodes.ACC_STRICT) != 0;
    }

    public boolean isSynthetic() {
        return (access & Opcodes.ACC_SYNTHETIC) != 0;
    }

    public boolean isAnnotation() {
        return (access & Opcodes.ACC_ANNOTATION) != 0;
    }

    public boolean isEnum() {
        return (access & Opcodes.ACC_ENUM) != 0;
    }

    public boolean isMandated() {
        return (access & Opcodes.ACC_MANDATED) != 0;
    }

    public boolean isModule() {
        return (access & Opcodes.ACC_MODULE) != 0;
    }

    public String getName() {
        return name;
    }

    public String getSignature() {
        return signature;
    }

    /**
     * Returns a string that uniquely identifies this node.
     */
    public abstract String getIdentifier();

}