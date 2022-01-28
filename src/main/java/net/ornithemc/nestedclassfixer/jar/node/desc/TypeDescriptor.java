package net.ornithemc.nestedclassfixer.jar.node.desc;

import org.objectweb.asm.Type;

import net.ornithemc.nestedclassfixer.jar.JarFile;
import net.ornithemc.nestedclassfixer.jar.node.ClassNode;
import net.ornithemc.nestedclassfixer.jar.node.UnknownClassNode;

public abstract class TypeDescriptor
{

    public abstract String getDescriptor();

    public static TypeDescriptor construct(String desc, JarFile jar) {
        return construct(Type.getType(desc), jar);
    }

    public static TypeDescriptor construct(Type type, JarFile jar) {
        switch (type.getSort()) {
        case Type.OBJECT:
            String name = type.getInternalName();
            ClassNode clazz = jar.getClass(name);

            if (clazz == null) {
                clazz = new UnknownClassNode(name);
            }

            return new ObjectTypeDescriptor(clazz);
        case Type.ARRAY:
            int dimensions = type.getDimensions();
            TypeDescriptor arrayType = construct(type.getElementType(), jar);

            return new ArrayTypeDescriptor(dimensions, arrayType);
        case Type.METHOD:
            Type[] args = type.getArgumentTypes();
            Type ret = type.getReturnType();

            TypeDescriptor[] argumentTypes = new TypeDescriptor[args.length];
            TypeDescriptor returnType = construct(ret, jar);

            for (int i = 0; i < args.length; i++) {
                argumentTypes[i] = construct(args[i], jar);
            }

            return new MethodTypeDescriptor(argumentTypes, returnType);
        default:
            return new PrimitiveTypeDescriptor(type);
        }
    }
}
