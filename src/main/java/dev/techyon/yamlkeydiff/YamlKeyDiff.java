package dev.techyon.yamlkeydiff;

import dev.techyon.yamlkeydiff.model.DirKeyDiff;
import dev.techyon.yamlkeydiff.services.DefaultDirDiffService;
import dev.techyon.yamlkeydiff.services.DefaultKeyDiffService;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@QuarkusMain
public class YamlKeyDiff implements QuarkusApplication {
  @Override
  public int run(String... args) throws Exception {
    if (args.length != 3) {
      printHelp();
      return 50;
    }

    if (args[0].equals("--help")) {
      printHelp();
      return 0;
    }

    if (args[0].equals("dirDiff")) {
        try {
          DefaultDirDiffService service = new DefaultDirDiffService();
          DirKeyDiff dirKeyDiff = service.diff(args[1], args[2]);

            if (dirKeyDiff.getMissingFiles().isEmpty() && dirKeyDiff.getMissingKeys().isEmpty()) {
                System.out.println("There is no missing keys");
            } else {
                System.out.println("\nMissing files:");
                dirKeyDiff.getMissingFiles().stream().sorted()
                        .forEach(System.out::println);
                System.out.println("\nMissing keys:");
                dirKeyDiff.getMissingKeys().forEach((key, value) -> {
                System.out.println(key);
                value.forEach(s -> System.out.println("\t" + s));
                });
                return 20;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 50;
        }
      return 0;
    }

    if (args[0].equals("fileDiff")) {
        try {
            Path yamlFilePath1 = Path.of(args[1]);
            Path yamlFilePath2 = Path.of(args[2]);

            DefaultKeyDiffService service = new DefaultKeyDiffService();
            List<String> missingKeys = service.diff(yamlFilePath1, yamlFilePath2);

            if (missingKeys.isEmpty()) {
            System.out.println("There is no missing keys");
            } else {
            System.out.println("Missing keys:");
            missingKeys.forEach(System.out::println);
            return 20;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 50;
        }

        return 0;
    }

    if (args[0].equals("missingFiles")) {
        try {
            DefaultDirDiffService service = new DefaultDirDiffService();
            List<String> missingFiles = service.findMissingFiles(args[1], args[2]);

            if (missingFiles.isEmpty()) {
            System.out.println("There is no missing files");
            } else {
            System.out.println("Missing files:");
            missingFiles.forEach(s -> System.out.println(s));
            return 20;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 50;
        }

        return 0;
    }

    printHelp();
    return 0;
  }

  public void printHelp() {
    System.out.println("Usage: yaml-key-diff <command> <source> <destination>");
    System.out.println("Commands:");
    System.out.println("  dirDiff: Compare two directories");
    System.out.println("  fileDiff: Compare two files");
    System.out.println("  missingFiles: Find missing files in destination directory");
    System.out.println("  --help: Print this help message");
  }
}
