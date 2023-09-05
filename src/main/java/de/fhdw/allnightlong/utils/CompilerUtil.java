package de.fhdw.allnightlong.utils;



import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class CompilerUtil {

    public static Class<?> compile(String sourceCode, String className) {
        // Zugriff auf den System-Java-Compiler
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // File-Manager zum Speichern von Quellcode und Bytecode
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        // Ein JavaFileObject aus dem Quellcode erstellen
        JavaFileObject javaFile = new JavaSourceFromString(className, sourceCode);

        // Kompilierungsvorgang
        JavaCompiler.CompilationTask task = compiler.getTask(
            null,
            fileManager,
            diagnostics,
            null,
            null,
            Arrays.asList(javaFile)
        );

        boolean compilationStatus = task.call();

        // Überprüfen, ob die Kompilierung erfolgreich war
        if (compilationStatus) {
            // Wenn die Kompilierung erfolgreich war, laden Sie die Klasse
            try (URLClassLoader classLoader = new URLClassLoader(new URL[] { new File("").toURI().toURL() })) {
                return classLoader.loadClass(className);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            // Ausgabe der Kompilierungsfehler
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                System.out.println(diagnostic.getMessage(null));
            }
            return null;
        }
    }

    // Zusätzliche Klasse, um ein JavaFileObject aus einem String zu erstellen
    public static class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;

        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }
}
