package dev.techyon.yamlkeydiff.services;

import dev.techyon.yamlkeydiff.model.DirKeyDiff;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.google.common.io.Files.getFileExtension;

public class DefaultDirDiffService {

    private DefaultKeyDiffService keyDiffService = new DefaultKeyDiffService();
    public static final List<String> YAML_EXTS = List.of("yaml", "yml");

    public List<String> findMissingFiles(String sourceDir, String destDir) throws IOException {

        List<String> sourceFiles = getListOfYamlFiles(sourceDir, true);

        List<String> destFiles = getListOfYamlFiles(destDir, true);

        return sourceFiles.stream().filter(s -> !destFiles.contains(s)).collect(java.util.stream.Collectors.toList());
    }

    public DirKeyDiff diff(String sourceDir, String destDir) throws IOException {
        DirKeyDiff dirKeyDiff = new DirKeyDiff();

        Path pathIn = Paths.get(sourceDir);
        if (!Files.exists(pathIn)) {
            throw new IllegalArgumentException("Directory does not exist: " + sourceDir);
        }

        Files.walk(pathIn).forEach(path -> {
                    if (Files.isRegularFile(path)) {
                        String relativePath = getRelativePath(path, pathIn);
                        String destPath = destDir + "/" + relativePath;
                        Path currnetPath = Paths.get(destPath);
                        if (Files.exists(currnetPath)) {
                            try {
                                List<String> missingKeys = keyDiffService.diff(path, currnetPath);
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
                }
        );

        return dirKeyDiff;
    }

    private List<String> getListOfYamlFiles(String dir, boolean relativePaths) throws IOException {
        Path pathIn = Paths.get(dir);
        if (!Files.exists(pathIn)) {
            throw new IllegalArgumentException("Directory does not exist: " + dir);
        }

        return Files.walk(pathIn)
                .filter(Files::isRegularFile)
                .filter(path -> YAML_EXTS.contains(getFileExtension(path.toString())))
                .map(path -> (relativePaths) ? getRelativePath(path, pathIn) : path.toString())
                .collect(java.util.stream.Collectors.toList());
    }

    private String getRelativePath(Path path, Path basePath) {
        return basePath.relativize(path).toString();
    }
}
