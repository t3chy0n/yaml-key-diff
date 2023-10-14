package dev.techyon.yamlkeydiff;

import com.google.common.io.Resources;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainLauncher;
import io.quarkus.test.junit.main.QuarkusMainTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URL;

@QuarkusMainTest
class YamlKeyDiffTest {

  @Test
  void testLaunchCommand_fileDiff_noDiff(QuarkusMainLauncher launcher) {
    URL url = Resources.getResource("one.yaml");
    LaunchResult result = launcher.launch("fileDiff", url.getFile(), url.getFile());
    Assertions.assertEquals(0, result.exitCode());
    Assertions.assertEquals("There is no missing keys", result.getOutput());
  }

  @Test
  void testLaunchCommand_fileDiff_diff(QuarkusMainLauncher launcher) {
    URL url1 = Resources.getResource("one.yaml");
    URL url2 = Resources.getResource("two.yaml");
    LaunchResult result = launcher.launch("fileDiff", url1.getFile(), url2.getFile());
    Assertions.assertEquals(20, result.exitCode());

    String expected = "Missing keys:\n" +
            "rootObjectOne.secondLevelMissingVal\n" +
            "rootLvlMissingVal";

    Assertions.assertEquals(expected, result.getOutput());
  }

  @Test
  void testLaunchCommand_dirDiff_diff(QuarkusMainLauncher launcher) {
    URL url1 = Resources.getResource("stage");
    URL url2 = Resources.getResource("prod");
    LaunchResult result = launcher.launch("dirDiff", url1.getFile(), url2.getFile());
    Assertions.assertEquals(20, result.exitCode());

    String expected = "\nMissing files:\n" +
            "service2/one.yaml\n" +
            "serviceMissing/two.yaml\n" +
            "\nMissing keys:\n" +
            "service1/one.yaml\n" +
            "\trootObjectOne.secondLevelMissingVal\n" +
            "\trootLvlMissingVal";

    Assertions.assertEquals(expected, result.getOutput());
  }

  @Test
  void testLaunchCommand_missingFiles_diff(QuarkusMainLauncher launcher) {
    URL url1 = Resources.getResource("stage");
    URL url2 = Resources.getResource("prod");
    LaunchResult result = launcher.launch("missingFiles", url1.getFile(), url2.getFile());
    Assertions.assertEquals(20, result.exitCode());

    String expected = "Missing files:\n" +
            "serviceMissing/two.yaml\n" +
            "service2/one.yaml";

    Assertions.assertEquals(expected, result.getOutput());
  }

}