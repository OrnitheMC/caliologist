package net.ornithemc.nestedclassfixer.jar;

import java.util.Map;

public class JarClass
{
    private final String name;

    private int version;
    private int access;
    private String signature;
    private String superName;
    private String[] interfaces;

    private boolean isComplete = false;

    public JarClass(String name) {
        this.name = name;
    }

    public void populate(int version, int access, String signature, String superName, String[] interfaces) {
        this.version = version;
        this.access = access;
        this.signature = signature;
        this.superName = superName;
        this.interfaces = interfaces;

        this.isComplete = true;
    }

    public boolean isComplete() {
        return this.isComplete;
    }
}
