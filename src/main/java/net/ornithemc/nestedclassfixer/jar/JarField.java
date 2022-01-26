package net.ornithemc.nestedclassfixer.jar;

import org.objectweb.asm.Opcodes;

public class JarField
{
    private final String name;
    private final String desc;
    private int access;
    private String signature;
    private Object value;

    public JarField(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public void populate(int access, String signature, Object value) {
        this.access = access;
        this.signature = signature;
        this.value = value;
    }

    public boolean isSynthetic() {
        return (access & Opcodes.ACC_SYNTHETIC) != 0;
    }
}
