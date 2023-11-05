package dev.techyon.yamlkeydiff.services;

import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WildcardFileListProviderTest {

    WildcardFileListProvider provider = new WildcardFileListProvider();

    @Test
    void getPaths() throws IOException {
        URL stageUrl = Resources.getResource("testDirs/stage/");
        Path stageUrlPath = Path.of(stageUrl.getPath());

        HashMap<String, Path> paths = provider.findFiles(stageUrlPath, "**/*3/*");
        assertNotNull(paths);
        assertEquals(1, paths.size());
        assertTrue(paths.get("three.yaml").toString().endsWith("stage/service3/three.yaml"));
    }
}