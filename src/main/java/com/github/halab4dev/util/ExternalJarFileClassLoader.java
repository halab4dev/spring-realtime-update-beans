package com.github.halab4dev.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExternalJarFileClassLoader {

    private ExternalJarFileClassLoader() {}

    private static final String TEM_FOLDER = "tmp";

    public static Map<String, Class<?>> loadClasses(String jarFilePath, List<String> targetClasses) throws Exception {
        createTemporaryFolder();
        Path jarDirectory = decompressJar(jarFilePath);
        File rootDir = new File(jarDirectory.toAbsolutePath().toString());
        File springBootDir = new File(jarDirectory.toAbsolutePath() + "/BOOT-INF/classes/");

        List<URL> urls = new ArrayList<>();
        urls.add(rootDir.toURI().toURL());
        urls.add(springBootDir.toURI().toURL());

        Map<String, Class<?>> classes = new HashMap<>();
        try (URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[0]))) {

            for (String targetClass : targetClasses) {
                Class<?> clazz = classLoader.loadClass(targetClass);
                classes.put(targetClass, clazz);
            }
        }
        deleteTemporaryFolder();
        return classes;
    }

    private static void createTemporaryFolder() throws IOException {
        deleteTemporaryFolder();
        File temFolder = new File(TEM_FOLDER);
        temFolder.deleteOnExit();
    }

    private static Path decompressJar(String jarFilePath) throws IOException {
        new ProcessBuilder("unzip", jarFilePath, "-d", "./tmp")
                .start();
        return new File("tmp").toPath();
    }

    private static void deleteTemporaryFolder() throws IOException {
        File temFolder = new File(TEM_FOLDER);
        if (!temFolder.exists()) {
            return;
        }
        Files.walkFileTree(temFolder.toPath(), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
