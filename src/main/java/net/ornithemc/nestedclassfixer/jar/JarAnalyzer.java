package net.ornithemc.nestedclassfixer.jar;

import org.objectweb.asm.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JarAnalyzer
{
    private final JarFile jar;
    private final Map<String, JarClass> nestedClasses = new TreeMap<>();
    private final Map<String, JarClass> classesWithGenerics = new TreeMap<>();

    public JarAnalyzer(File inputJar) {
        this.jar = new JarFile(inputJar);
        try (FileInputStream fileStream = new FileInputStream(this.jar.getFile())) {
            try (JarInputStream jarStream = new JarInputStream(fileStream)) {
                int classes = 0;

                for (JarEntry entry; (entry = jarStream.getNextJarEntry()) != null; ) {
                    if (entry.getName().endsWith(".class")) {
                        ClassReader classReader = new ClassReader(jarStream);

                        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM9)
                        {
                            JarClass jarClass;

                            @Override
                            public void visit(int version, int access, String name, String signature, String superName,
                                              String[] interfaces) {
                                System.out.println("Visiting class " + name);

                                jarClass = jar.addAndReturnClass(name);
                                if (jarClass.isNotComplete()) {
                                    jarClass.populate(version, access, signature, superName, interfaces);
                                }
                            }

                            @Override
                            public FieldVisitor visitField(int access, String name, String desc, String signature,
                                                           Object value) {
                                if (jarClass.isNotComplete()) {
                                    JarField field = jarClass.addAndReturnField(name, desc);
                                    field.populate(access, signature, value);
                                }

                                return null;
                            }

                            @Override
                            public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                                             String[] exceptions) {
                                if (jarClass.isNotComplete()) {
                                    JarMethod method = jarClass.addAndReturnMethod(name, desc);
                                    method.populate(access, signature, exceptions);
                                }

                                return null;
                            }

                            @Override
                            public void visitEnd() {
                                jarClass.complete();
                            }
                        };

                        classReader.accept(classVisitor, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG | ClassReader.SKIP_CODE);
                        classes++;
                    }
                }
                System.out.println("Visited " + classes + " classes");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        findNestedClasses();
        System.out.println("ksskk");
    }

    public void findNestedClasses() {
        int nestedClasses = 0;
        jarIterator:
        for (JarClass jarClass : this.jar.getClassMap().values()) {
            if (jarClass.isEnum()) {
                if (!jarClass.getSuperName().equals("java/lang/Enum")) {
                    System.out.println("Found " + jarClass.getName());
                    this.nestedClasses.put(jarClass.getName(), jarClass);
                    nestedClasses++;
                }
            } else {
                for (JarField field : jarClass.getFieldMap().values()) {
                    if (field.isSynthetic()) {
                        System.out.println("Found " + jarClass.getName());
                        this.nestedClasses.put(jarClass.getName(), jarClass);
                        nestedClasses++;

                        continue jarIterator;
                    }
                }

                for (JarMethod method : jarClass.getMethodMap().values()) {
                    if (method.isSynthetic()) {
                        System.out.println("Found " + jarClass.getName());
                        this.nestedClasses.put(jarClass.getName(), jarClass);
                        nestedClasses++;

                        continue jarIterator;
                    }
                }
            }
        }
    }
}
