package dev.techyon.yamlkeydiff.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.google.common.io.Files.getFileExtension;

public class DefaultDirDiffService {

    public static final List<String> YAML_EXTS = List.of("yaml", "yml");

    public List<String> findMissingFiles(String sourceDir, String destDir) throws IOException {

        List<String> sourceFiles = getListOfYamlFiles(sourceDir);

        List<String> destFiles = getListOfYamlFiles(destDir);

        return sourceFiles.stream().filter(s -> !destFiles.contains(s)).collect(java.util.stream.Collectors.toList());
    }

    private List<String> getListOfYamlFiles(String dir) throws IOException{
        Path pathIn = Paths.get(dir);
        if (!Files.exists(pathIn)) {
            throw new IllegalArgumentException("Directory does not exist: " + dir);
        }

        return Files.walk(pathIn)
                .filter(Files::isRegularFile)
                .filter(path -> YAML_EXTS.contains(getFileExtension(path.toString())))
                .map(path -> getRelativePath(path, pathIn))
                .collect(java.util.stream.Collectors.toList());
    }

    private String getRelativePath(Path path, Path basePath) {
        return basePath.relativize(path).toString();
    }
}
