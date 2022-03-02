package net.ornithemc.caliologist.jar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.benf.cfr.reader.entities.ClassFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import net.ornithemc.caliologist.jar.node.ClassNode;
import net.ornithemc.caliologist.jar.node.Node;
import net.ornithemc.caliologist.jar.node.proto.ProtoClassNode;
import net.ornithemc.caliologist.jar.node.proto.ProtoFieldNode;
import net.ornithemc.caliologist.jar.node.proto.ProtoMethodNode;
import net.ornithemc.caliologist.jar.node.proto.ProtoNode;

public class JarFile
{
    private final File file;
    private final Decompiler decompiler;
    private final Map<String, ClassNode> classes;
    private final Map<ClassNode, String> classContents;

    public JarFile(File file) throws IOException {
        this.file = file;
        this.decompiler = new Decompiler(this);
        this.classes = new LinkedHashMap<>();
        this.classContents = new HashMap<>();

        this.read();
    }

    private void read() {
        Set<ProtoNode> protoNodes = new LinkedHashSet<>();

        try (JarInputStream js = new JarInputStream(new FileInputStream(this.file))) {
            for (JarEntry entry; (entry = js.getNextJarEntry()) != null;) {
                if (entry.getName().endsWith(".class")) {
                    ClassReader reader = new ClassReader(js);
                    ClassVisitor visitor = new ClassVisitor(Opcodes.ASM9)
                    {
                        private ProtoClassNode clazz;

                        @Override
                        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                            if (clazz == null) {
                                clazz = new ProtoClassNode(version, access, name, signature, superName, interfaces);
                                protoNodes.add(clazz);
                            }

                            super.visit(version, access, name, signature, superName, interfaces);
                        }

                        @Override
                        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
                            if (clazz != null) {
                                ProtoFieldNode field = new ProtoFieldNode(access, name, desc, signature, value);
                                field.setParent(clazz);
                                protoNodes.add(field);
                            }

                            return super.visitField(access, name, desc, signature, value);
                        }

                        @Override
                        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                            if (clazz != null) {
                                ProtoMethodNode method = new ProtoMethodNode(access, name, desc, signature, exceptions);
                                method.setParent(clazz);
                                protoNodes.add(method);
                            }

                            return super.visitMethod(access, name, desc, signature, exceptions);
                        }

                        @Override
                        public void visitInnerClass(String name, String outerName, String innerName, int access) {
                            super.visitInnerClass(name, outerName, innerName, access);
                        }

                        @Override
                        public void visitOuterClass(String owner, String name, String descriptor) {
                            super.visitOuterClass(owner, name, descriptor);
                        }
                    };

                    reader.accept(visitor, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG | ClassReader.SKIP_CODE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (ProtoNode protoNode : protoNodes) {
            Node node = protoNode.node();

            if (node.isClass()) {
                ClassNode clazz = node.asClass();
                classes.put(clazz.getName(), clazz);
            }
        }
    }

    public File getFile() {
        return file;
    }

    public Collection<ClassNode> getClasses() {
        return Collections.unmodifiableCollection(classes.values());
    }

    public ClassNode getClass(String name) {
        return classes.get(name);
    }

    public String decompile(ClassNode clazz) {
        return classContents.computeIfAbsent(clazz, key -> decompiler.decompile(clazz));
    }
}
