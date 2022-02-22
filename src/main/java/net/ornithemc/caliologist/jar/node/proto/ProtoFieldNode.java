package net.ornithemc.caliologist.jar.node.proto;

import net.ornithemc.caliologist.jar.JarFile;
import net.ornithemc.caliologist.jar.node.ClassNode;
import net.ornithemc.caliologist.jar.node.FieldNode;
import net.ornithemc.caliologist.jar.node.desc.TypeDescriptor;

public class ProtoFieldNode extends ProtoNode
{
    private final String desc;
    private final Object value;

    private FieldNode field;

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
        if (field == null) {
            ProtoClassNode protoParent = parent.asClass();
            ClassNode parent = protoParent.construct(jar);
            TypeDescriptor descriptor = TypeDescriptor.construct(desc, jar);

            field = new FieldNode(this, parent, access, name, signature, descriptor, value);
        }

        return field;
    }

    public String getDescriptor() {
        return desc;
    }

    public Object getValue() {
        return value;
    }
}
