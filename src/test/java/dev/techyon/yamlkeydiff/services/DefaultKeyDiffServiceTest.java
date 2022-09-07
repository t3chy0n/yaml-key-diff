package dev.techyon.yamlkeydiff.services;

import com.google.common.io.Resources;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultKeyDiffServiceTest {

  DefaultKeyDiffService service = new DefaultKeyDiffService();

  @Test
  void diff_notAYaml() {
    String notAYaml = "this is not valid yaml";
    Assertions.assertThrows(ParseException.class, () -> service.diff(notAYaml, notAYaml));
  }

  @Test
  void diff_success() throws IOException, ParseException {
    URL url1 = Resources.getResource("one.yaml");
    String one = Resources.toString(url1, StandardCharsets.UTF_8);
    URL url2 = Resources.getResource("two.yaml");
    String two = Resources.toString(url2, StandardCharsets.UTF_8);

    List<String> diffs = service.diff(one, two);
    Assertions.assertEquals(2, diffs.size());
    Assertions.assertTrue(diffs.contains("rootObjectOne.secondLevelMissingVal"));
    Assertions.assertTrue(diffs.contains("rootLvlMissingVal"));
  }
}