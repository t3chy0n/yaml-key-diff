package dev.techyon.yamlkeydiff;

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
    if (args.length < 2) {
      System.out.println("Two files required to diff");
      return 50;
    }

    try {
      Path yamlFilePath1 = Path.of(args[0]);
      String yaml1 = Files.readString(yamlFilePath1);

      Path yamlFilePath2 = Path.of(args[1]);
      String yaml2 = Files.readString(yamlFilePath2);

      DefaultKeyDiffService service = new DefaultKeyDiffService();
      List<String> missingKeys = service.diff(yaml1, yaml2);

      if (missingKeys.isEmpty()) {
        System.out.println("There is no missing keys");
      } else {
        System.out.println("Missing keys:");
        missingKeys.forEach(s -> System.out.println(s));
        return 20;
      }

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
      return 50;
    }


    return 0;
  }
}
