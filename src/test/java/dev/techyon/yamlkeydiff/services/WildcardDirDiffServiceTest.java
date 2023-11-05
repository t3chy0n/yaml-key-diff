package dev.techyon.yamlkeydiff.services;

import com.google.common.io.Resources;
import dev.techyon.yamlkeydiff.model.DirKeyDiff;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class WildcardDirDiffServiceTest {

    WildcardDirDiffService wildcardDirDiffService = new WildcardDirDiffService();

    @Test
    void diff() {
        URL startUrl = Resources.getResource("testDirs/");
        Path startPath = Path.of(startUrl.getPath());

        DirKeyDiff dirKeyDiff = null;

        try {
            dirKeyDiff = wildcardDirDiffService.diff(startPath, "**/stage/*/*", "**/prod/**/*");
        } catch (Exception e) {
            fail(e.getMessage());
        }

        assertNotNull(dirKeyDiff);
        assertEquals(1, dirKeyDiff.getMissingFiles().size());
        assertEquals(2, dirKeyDiff.getMissingKeys().size());
        assertEquals(2, dirKeyDiff.getMissingKeys().get("one.yaml").size());
        assertEquals(2, dirKeyDiff.getMissingKeys().get("one.yaml").size());
    }
}