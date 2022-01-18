package net.ornithemc.nestedclassfixer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.JarInputStream;

public class JarAnalyzer
{
    public JarAnalyzer(File inputJar) {
        try (FileInputStream fileStream = new FileInputStream(inputJar)) {
            try (JarInputStream jarStream = new JarInputStream(fileStream)) {
                jarStream.getNextJarEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
