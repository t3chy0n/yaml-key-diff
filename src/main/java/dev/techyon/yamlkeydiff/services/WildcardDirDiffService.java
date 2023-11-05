package dev.techyon.yamlkeydiff.services;

import dev.techyon.yamlkeydiff.model.DirKeyDiff;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WildcardDirDiffService {
    private DefaultKeyDiffService keyDiffService = new DefaultKeyDiffService();
    private WildcardFileListProvider fileListProvider = new WildcardFileListProvider();

    public DirKeyDiff diff(Path startPath, String sourcePattern, String destPattern) throws IOException {
        DirKeyDiff dirKeyDiff = new DirKeyDiff();

        HashMap<String, Path> sourceFiles = fileListProvider.findFiles(startPath, sourcePattern);
        HashMap<String, Path> destFiles = fileListProvider.findFiles(startPath, destPattern);

        for (Map.Entry<String, Path> entry : sourceFiles.entrySet()) {
            String relativePath = entry.getKey();
            Path sourcePath = entry.getValue();
            Path destPath = destFiles.get(relativePath);
            if (destPath != null) {
                try {
                    List<String> missingKeys = keyDiffService.diff(sourcePath, destPath);
                    if (!missingKeys.isEmpty()) {
                        dirKeyDiff.getMissingKeys().put(relativePath, missingKeys);
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                dirKeyDiff.getMissingFiles().add(relativePath);
            }
        }

        return dirKeyDiff;
    }
}
