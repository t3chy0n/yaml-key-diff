package dev.techyon.yamlkeydiff.services;

import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultDirDiffServiceTest {

    DefaultDirDiffService service = new DefaultDirDiffService();

    @Test
    void findMissingFiles_twoMissingFiles() throws IOException {
        URL url1 = Resources.getResource("stage");
        String one = url1.getPath();
        URL url2 = Resources.getResource("prod");
        String two = url2.getPath();

        List<String> missingFiles = service.findMissingFiles(one, two);

        assertNotNull(missingFiles);
        assertEquals(2, missingFiles.size());
        assertTrue(missingFiles.contains("service2/one.yaml"));
        assertTrue(missingFiles.contains("serviceMissing/two.yaml"));
    }

    @Test
    void findMissingFiles_dirNotFound() {

        assertThrows(IllegalArgumentException.class, () -> service.findMissingFiles("/iDontExist", "/meToo"));
    }

    @Test
    void diff_twoMissingFilesTwoMissingKeys() throws IOException {
        URL url1 = Resources.getResource("stage");
        String one = url1.getPath();
        URL url2 = Resources.getResource("prod");
        String two = url2.getPath();

        var dirKeyDiff = service.diff(one, two);

        assertNotNull(dirKeyDiff);
        assertEquals(2, dirKeyDiff.getMissingFiles().size());
        assertTrue(dirKeyDiff.getMissingFiles().contains("service2/one.yaml"));
        assertTrue(dirKeyDiff.getMissingFiles().contains("serviceMissing/two.yaml"));

        assertEquals(1, dirKeyDiff.getMissingKeys().size());
        assertTrue(dirKeyDiff.getMissingKeys().containsKey("service1/one.yaml"));
        assertEquals(2, dirKeyDiff.getMissingKeys().get("service1/one.yaml").size());
        assertTrue(dirKeyDiff.getMissingKeys().get("service1/one.yaml").contains("rootObjectOne.secondLevelMissingVal"));
        assertTrue(dirKeyDiff.getMissingKeys().get("service1/one.yaml").contains("rootLvlMissingVal"));
    }
}