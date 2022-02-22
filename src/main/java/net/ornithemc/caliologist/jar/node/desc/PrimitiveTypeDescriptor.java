package net.ornithemc.caliologist.jar.node.desc;

import org.objectweb.asm.Type;

public class PrimitiveTypeDescriptor extends TypeDescriptor
{
    private final Type type;

    public PrimitiveTypeDescriptor(Type type) {
        this.type = type;
    }

    @Override
    public String getDescriptor() {
        return type.getDescriptor();
    }
}
