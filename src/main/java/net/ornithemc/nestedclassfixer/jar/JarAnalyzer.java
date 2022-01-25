package net.ornithemc.nestedclassfixer.jar;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JarAnalyzer
{
    private final JarFile jar;

    public JarAnalyzer(File inputJar) {
        this.jar = new JarFile(inputJar);
        try (FileInputStream fileStream = new FileInputStream(this.jar.getFile())) {
            try (JarInputStream jarStream = new JarInputStream(fileStream)) {
                int classes = 0;

                for (JarEntry entry; (entry = jarStream.getNextJarEntry()) != null; ) {
                    if (entry.getName().endsWith(".class")) {
                        ClassReader classReader = new ClassReader(jarStream);

                        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM9) {
                            JarClass jarClass;

                            @Override
                            public void visit(int version, int access, String name, String signature, String superName,
                                              String[] interfaces) {
                                System.out.println("Visiting class " + name);

                                jarClass = jar.addAndReturnClass(name);
                                if (!jarClass.isComplete()) {
                                    jarClass.populate(version, access, signature, superName, interfaces);
                                }

                                super.visit(version, access, name, signature, superName, interfaces);
                            }

                            @Override
                            public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {


                                return null;
                            }
                        };

                        classes++;
                    }
                }
                System.out.println("Found " + classes + " classes");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
