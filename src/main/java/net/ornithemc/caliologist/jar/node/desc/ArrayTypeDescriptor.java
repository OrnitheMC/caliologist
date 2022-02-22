package net.ornithemc.caliologist.jar.node.desc;

public class ArrayTypeDescriptor extends TypeDescriptor
{
    private final int dimensions;
    private final TypeDescriptor arrayType;

    public ArrayTypeDescriptor(int dimensions, TypeDescriptor arrayType) {
        this.dimensions = dimensions;
        this.arrayType = arrayType;
    }

    @Override
    public String getDescriptor() {
        String desc = "";

        for (int i = 0; i < dimensions; i++) {
            desc += "[";
        }

        return desc + arrayType.getDescriptor();
    }
}
