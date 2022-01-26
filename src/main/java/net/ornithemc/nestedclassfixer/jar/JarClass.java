package net.ornithemc.nestedclassfixer.jar;

import org.objectweb.asm.Opcodes;

import java.util.Map;
import java.util.TreeMap;

public class JarClass
{
    private final String name;
    private final Map<String, JarField> fieldMap = new TreeMap<>();
    private final Map<String, JarMethod> methodMap = new TreeMap<>();

    private int version;
    private int access;
    private String signature;
    private String superName;
    private String[] interfaces;

    private boolean isComplete = false;
    private final boolean isNested = false;

    public JarClass(String name) {
        this.name = name;
    }

    public void populate(int version, int access, String signature, String superName, String[] interfaces) {
        this.version = version;
        this.access = access;
        this.signature = signature;
        this.superName = superName;
        this.interfaces = interfaces;
    }

    /**
     * Generates and returns a new {@link JarField} if it doesn't already exist, otherwise returns the existing one.
     *
     * @param name the field's name.
     * @param desc the field's descriptor (see {@link org.objectweb.asm.Type}).
     * @return the {@link JarField} for the given name and descriptor.
     */
    public JarField addAndReturnField(String name, String desc) {
        JarField field;
        if (fieldMap.containsKey(name + desc)) {
            field = fieldMap.get(name + desc);
        } else {
            field = new JarField(name, desc);
            fieldMap.put(name + desc, field);
        }

        return field;
    }

    /**
     * Generates and returns a new {@link JarMethod} if it doesn't already exist, otherwise returns the existing one.
     *
     * @param name the method's name.
     * @param desc the method's descriptor (see {@link org.objectweb.asm.Type}).
     * @return the {@link JarMethod} for the given name and descriptor.
     */
    public JarMethod addAndReturnMethod(String name, String desc) {
        JarMethod method;
        if (methodMap.containsKey(name + desc)) {
            method = methodMap.get(name + desc);
        } else {
            method = new JarMethod(name, desc);
            methodMap.put(name + desc, method);
        }

        return method;
    }

    public boolean isNotComplete() {
        return !this.isComplete;
    }

    public void complete() {
        this.isComplete = true;
    }

    public boolean isEnum() {
        return (this.access & Opcodes.ACC_ENUM) != 0;
    }

    public String getSuperName() {
        return superName;
    }

    public String getName() {
        return name;
    }

    public Map<String, JarField> getFieldMap() {
        return fieldMap;
    }

    public Map<String, JarMethod> getMethodMap() {
        return methodMap;
    }
}
