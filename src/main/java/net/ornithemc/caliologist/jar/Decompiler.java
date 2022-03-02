package net.ornithemc.caliologist.jar;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Collections;

import org.benf.cfr.reader.apiunreleased.ClassFileSource2;
import org.benf.cfr.reader.apiunreleased.JarContent;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.Pair;
import org.benf.cfr.reader.entities.ClassFile;
import org.benf.cfr.reader.state.DCCommonState;
import org.benf.cfr.reader.state.TypeUsageCollectingDumper;
import org.benf.cfr.reader.util.AnalysisType;
import org.benf.cfr.reader.util.getopt.OptionsImpl;
import org.benf.cfr.reader.util.output.Dumper;
import org.benf.cfr.reader.util.output.IllegalIdentifierDump;
import org.benf.cfr.reader.util.output.StringStreamDumper;

import net.ornithemc.caliologist.jar.node.ClassNode;

public class Decompiler
{
    private final JarFile jar;
    private final FileSystem fileSystem;
    private final DCCommonState state;
    
    public Decompiler(JarFile jar) throws IOException {
        this.jar = jar;
        this.fileSystem = FileSystems.newFileSystem(this.jar.getFile().toPath());
        this.state = new DCCommonState(OptionsImpl.getFactory().create(Collections.emptyMap()), new ClassFileSource2() {

            @Override public void informAnalysisRelativePathDetail(String usePath, String classFilePath) { }
            @Override public Collection<String> addJar(String jarPath) { return null; }
            @Override public String getPossiblyRenamedPath(String path) { return path; }
            @Override public JarContent addJarContent(String jarPath, AnalysisType analysisType) { return null; }

            @Override
            public Pair<byte[], String> getClassFileContent(String path) throws IOException {
                try {
                    if (!path.endsWith(".class")) {
                        path += ".class";
                    }
                    return new Pair<>(Files.readAllBytes(fileSystem.getPath(path)), path);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        });
    }

    public String decompile(ClassNode clazz) {
        ClassFile file = state.loadClassFileAtPath(clazz.getName());
        StringBuilder sb = new StringBuilder();
        TypeUsageCollectingDumper typeUsageCollector = new TypeUsageCollectingDumper(state.getOptions(), file);
        file.analyseTop(state, typeUsageCollector);
        Dumper d = new StringStreamDumper((m, s) -> { }, sb, typeUsageCollector.getRealTypeUsageInformation(), state.getOptions(), IllegalIdentifierDump.Factory.get(state.getOptions()));
        file.dump(d);
        return sb.toString();
    }
}
