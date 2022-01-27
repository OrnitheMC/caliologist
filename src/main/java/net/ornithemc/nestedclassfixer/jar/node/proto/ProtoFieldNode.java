package net.ornithemc.nestedclassfixer.jar.node.proto;

import net.ornithemc.nestedclassfixer.jar.JarFile;
import net.ornithemc.nestedclassfixer.jar.node.ClassNode;
import net.ornithemc.nestedclassfixer.jar.node.FieldNode;

public class ProtoFieldNode extends ProtoNode
{
    private final String desc;
    private final Object value;

    public ProtoFieldNode(ProtoClassNode parent, int access, String name, String desc, String signature, Object value) {
        super(parent, access, name, signature);

        this.desc = desc;
        this.value = value;
    }

    @Override
    public boolean isField() {
        return true;
    }

    @Override
    public ProtoFieldNode asField() {
        return this;
    }

    @Override
    public FieldNode construct(JarFile jar) {
        ProtoClassNode protoParent = parent.asClass();
        ClassNode parent = jar.getClass(protoParent.name);

        return new FieldNode(this, parent, access, name, desc, signature, value);
    }

    public String getDescriptor() {
        return desc;
    }

    public Object getValue() {
        return value;
    }
}
