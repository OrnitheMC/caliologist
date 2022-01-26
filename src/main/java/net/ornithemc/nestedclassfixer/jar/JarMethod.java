package net.ornithemc.nestedclassfixer.jar;

import org.objectweb.asm.Opcodes;

public class JarMethod
{
    public final String name;
    public final String desc;
    public int access;
    public String signature;
    public String[] exceptions;

    public JarMethod(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public void populate(int access, String signature, String[] exceptions) {
        this.access = access;
        this.signature = signature;
        this.exceptions = exceptions;
    }

    public boolean isSynthetic() {
        return ((access & Opcodes.ACC_SYNTHETIC) != 0);
    }

    public boolean isBridge() {
        return ((access & Opcodes.ACC_BRIDGE) != 0);
    }
}
