package dev.techyon.yamlkeydiff;

import com.google.common.io.Resources;
import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainLauncher;
import io.quarkus.test.junit.main.QuarkusMainTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusMainTest
class YamlKeyDiffTest {
  @Test
  @Launch(value = {}, exitCode = 50)
  public void testLaunchCommand_noArgs(LaunchResult result) {
    Assertions.assertEquals("Two files required to diff", result.getOutput());
  }

  @Test
  public void testLaunchCommand_noDiff(QuarkusMainLauncher launcher) {
    URL url = Resources.getResource("one.yaml");
    LaunchResult result = launcher.launch(url.getFile(), url.getFile());
    Assertions.assertEquals(0, result.exitCode());
    Assertions.assertEquals("There is no missing keys", result.getOutput());
  }

  @Test
  public void testLaunchCommand_diff(QuarkusMainLauncher launcher) {
    URL url1 = Resources.getResource("one.yaml");
    URL url2 = Resources.getResource("two.yaml");
    LaunchResult result = launcher.launch(url1.getFile(), url2.getFile());
    Assertions.assertEquals(20, result.exitCode());

    String expected = "Missing keys:\n" +
            "rootObjectOne.secondLevelMissingVal\n" +
            "rootLvlMissingVal";

    Assertions.assertEquals(expected, result.getOutput());
  }
}