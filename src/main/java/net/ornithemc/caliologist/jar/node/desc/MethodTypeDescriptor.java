package net.ornithemc.caliologist.jar.node.desc;

public class MethodTypeDescriptor extends TypeDescriptor
{
    private final TypeDescriptor[] argumentTypes;
    private final TypeDescriptor returnType;

    public MethodTypeDescriptor(TypeDescriptor[] argumentTypes, TypeDescriptor returnType) {
        this.argumentTypes = argumentTypes;
        this.returnType = returnType;
    }

    @Override
    public String getDescriptor() {
        String desc = "(";

        for (TypeDescriptor argumentType : argumentTypes) {
            desc += argumentType.getDescriptor();
        }

        return desc + ")" + returnType.getDescriptor();
    }
}
