package net.ornithemc.caliologist.jar.node.desc;

import net.ornithemc.caliologist.jar.node.ClassNode;

public class ObjectTypeDescriptor extends TypeDescriptor
{
    private final ClassNode clazz;

    public ObjectTypeDescriptor(ClassNode clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getDescriptor() {
        return "L" + clazz.getName() + ";";
    }
}
