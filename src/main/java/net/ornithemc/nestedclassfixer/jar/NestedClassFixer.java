package net.ornithemc.nestedclassfixer.jar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import net.ornithemc.nestedclassfixer.jar.node.ClassNode;
import net.ornithemc.nestedclassfixer.jar.node.Node;
import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoClassNode;
import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoFieldNode;
import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoMethodNode;
import net.ornithemc.nestedclassfixer.jar.node.proto.ProtoVariableNode;

public class NestedClassFixer
{
    private final JarFile jar;
    private final Set<ClassNode> candidates;

    public NestedClassFixer(File inputJar) {
        this.jar = new JarFile(inputJar);
        this.candidates = new HashSet<>();
    }

    public void readJar() {
        Set<ProtoClassNode> protoClasses = new HashSet<>();
        Set<ProtoFieldNode> protoFields = new HashSet<>();
        Set<ProtoMethodNode> protoMethods = new HashSet<>();
        Set<ProtoVariableNode> protoVariables = new HashSet<>();

        try (JarInputStream js = new JarInputStream(new FileInputStream(jar.getFile()))) {
            int classes = 0;

            for (JarEntry entry; (entry = js.getNextJarEntry()) != null;) {
                if (entry.getName().endsWith(".class")) {
                    ClassReader reader = new ClassReader(js);
                    ClassVisitor visitor = new ClassVisitor(Opcodes.ASM9)
                    {
                        ProtoClassNode clazz;

                        @Override
                        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                            if (clazz == null) {
                                clazz = new ProtoClassNode(null, version, access, name, signature, superName, interfaces);
                                protoClasses.add(clazz);
                            }

                            super.visit(version, access, name, signature, superName, interfaces);
                        }

                        @Override
                        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
                            if (clazz != null) {
                                ProtoFieldNode field = new ProtoFieldNode(clazz, access, name, desc, signature, value);
                                protoFields.add(field);
                            }

                            return super.visitField(access, name, desc, signature, value);
                        }

                        @Override
                        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                            if (clazz != null) {
                                ProtoMethodNode method = new ProtoMethodNode(clazz, access, name, desc, signature, exceptions);
                                protoMethods.add(method);
                            }

                            // TODO: add method visitor to capture parameters/locals

                            return super.visitMethod(access, name, desc, signature, exceptions);
                        }
                    };

                    reader.accept(visitor, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG | ClassReader.SKIP_CODE);
                    classes++;
                }
            }
            System.out.println("Visited " + classes + " classes");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ksskk");

        jar.construct(protoClasses, protoFields, protoMethods, protoVariables);
    }

    public void findNestedClassCandidates() {
        int nestedClasses = 0;

        for (ClassNode clazz : this.jar.getClasses()) {
            if (clazz.isEnum()) {
                if (!clazz.getSuperClass().getName().equals("java/lang/Enum")) {
                    //System.out.println("Found " + clazz.getName());
                    candidates.add(clazz);
                    nestedClasses++;
                    continue;
                }
            }
            for (Node child : clazz.getChildren()) {
                if ((child.isField() || child.isMethod()) && child.isSynthetic()) {
                    //System.out.println("Found " + clazz.getName());
                    candidates.add(clazz);
                    nestedClasses++;
                    break;
                }
            }
        }
    }
}
