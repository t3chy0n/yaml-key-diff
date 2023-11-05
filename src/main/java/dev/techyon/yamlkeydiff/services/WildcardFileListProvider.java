package dev.techyon.yamlkeydiff.services;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.HashMap;

public class WildcardFileListProvider {

    public HashMap<String, Path> findFiles(Path startDir, String pattern) throws IOException {
        String globPattern = "glob:" + pattern;
        final PathMatcher matcher = FileSystems.getDefault().getPathMatcher(globPattern);
        final HashMap<String, Path> matchedPaths = new HashMap<>();

        FileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                // Skip directories starting with a dot
                if (dir.getFileName().toString().startsWith(".")) {
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (matcher.matches(file)) {
                    matchedPaths.put(file.getFileName().toString(), file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                return FileVisitResult.CONTINUE;
            }
        };


        Files.walkFileTree(startDir, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, fileVisitor);
            
        return matchedPaths;
    }

}
