package net.ornithemc.nestedclassfixer.jar;

import org.objectweb.asm.Type;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

class JarFile
{
    private final File file;
    private final Map<String, JarClass> classMap = new HashMap<>();

    public JarFile(File jarFile) {
        this.file = jarFile;
    }

    public File getFile() {
        return file;
    }

    /**
     * Generates a new {@link JarClass} if it doesn't already exist, otherwise returns the existing one.
     *
     * @param className the internal name of the class (see {@link Type#getInternalName()})
     * @return the {@link JarClass} for the given class name
     */
    public JarClass addAndReturnClass(String className) {
        JarClass jarClass;
        if (!classMap.containsKey(className)) {
            jarClass = new JarClass(className);
            classMap.put(className, jarClass);
        } else {
            jarClass = classMap.get(className);
        }
        return jarClass;
    }
}
