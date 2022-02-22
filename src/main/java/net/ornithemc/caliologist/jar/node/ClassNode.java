package net.ornithemc.caliologist.jar.node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.ornithemc.caliologist.jar.node.proto.ProtoClassNode;
import net.ornithemc.caliologist.jar.node.proto.ProtoFieldNode;
import net.ornithemc.caliologist.jar.node.proto.ProtoMethodNode;

public class ClassNode extends Node
{
    private ClassNode superClass;
    private ClassNode[] interfaces;

    private final Map<String, FieldNode> fields;
    private final Map<String, MethodNode> methods;

    private final Map<String, ClassNode> innerClasses;
    private final Set<ClassNode> anonymousClasses;

    private String simpleName; // used by nested classes

    public ClassNode(ProtoClassNode proto, ClassNode parent, int access, String name, String signature) {
        super(proto, null, access, name, signature);

        this.fields = new HashMap<>();
        this.methods = new HashMap<>();

        this.innerClasses = new HashMap<>();
        this.anonymousClasses = new HashSet<>();
    }

    @Override
    public ProtoClassNode getProto() {
        return proto.asClass();
    }

    @Override
    public boolean isClass() {
        return true;
    }

    @Override
    public ClassNode asClass() {
        return this;
    }

    @Override
    protected boolean isValidParent(Node node) {
        return node == null || node.isClass();
    }

    @Override
    public boolean addChild(Node node) {
        if (super.addChild(node)) {
            if (node.isField()) {
                FieldNode field = node.asField();
                ProtoFieldNode proto = field.getProto();

                fields.put(proto.getName(), field);
            }
            if (node.isMethod()) {
                MethodNode method = node.asMethod();
                ProtoMethodNode proto = method.getProto();

                methods.put(proto.getName() + proto.getDescriptor(), method);
            }

            return true;
        }

        return false;
    }

    @Override
    protected boolean isValidChild(Node node) {
        return !(node instanceof VariableNode);
    }

    public ClassNode getSuperClass() {
        return superClass;
    }

    public ClassNode[] getInterfaces() {
        return interfaces;
    }

    public void updateAncestry(ClassNode superClass, ClassNode... interfaces) {
        this.superClass = superClass;
        this.interfaces = interfaces;
    }

    /**
     * Retrieve a field node from the name of its proto field
     */
    public FieldNode getField(String name) {
        return fields.get(name);
    }

    /**
     * Retrieve a method node from the name and descriptor of its proto method
     */
    public MethodNode getMethod(String name, String desc) {
        return methods.get(name + desc);
    }

    public boolean addInnerClass(ClassNode clazz) {
        if (clazz.getParent() != null || !addChild(clazz)) {
            return false;
        }

        String simpleName = getSimpleName(clazz);
        clazz.setSimpleName(simpleName);
        String name = getName() + "$" + simpleName;
        clazz.setName(name);

        innerClasses.put(simpleName, clazz);

        return true;
    }

    private String getSimpleName(ClassNode clazz) {
        return clazz.getName(); // TODO: generate unique (within this class) simple names
    }

    public boolean addAnonymousClass(ClassNode clazz) {
        if (clazz.getParent() != null || !addChild(clazz)) {
            return false;
        }

        clazz.setSimpleName(null);
        String name = getName() + "$" + anonymousClasses.size();
        clazz.setName(name);

        anonymousClasses.add(clazz);

        return true;
    }

    public void removeNestedClass(ClassNode clazz) {
        String simpleName = clazz.getSimpleName();

        if (simpleName == null) {
            anonymousClasses.remove(clazz);
            resetAnonymousClassNames();
        } else {
            innerClasses.remove(simpleName, clazz);
        }
    }

    private void resetAnonymousClassNames() {
        int i = 0;
        
        for (ClassNode clazz : anonymousClasses) {
            String name = getName() + "$" + i;
            clazz.setName(name);
        }
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String name) {
        if (isValidSimpleName(name)) {
            simpleName = name;
        }
    }

    private boolean isValidSimpleName(String name) {
        return true; // TODO: check against keywords and invalid characters
    }
}
