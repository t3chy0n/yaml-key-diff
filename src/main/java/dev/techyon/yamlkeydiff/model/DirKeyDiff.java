package dev.techyon.yamlkeydiff.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirKeyDiff {
    private List<String> missingFiles = new ArrayList<>();
    private Map<String, List<String>> missingKeys = new HashMap<>();

    public List<String> getMissingFiles() {
        return missingFiles;
    }

    public void setMissingFiles(List<String> missingFiles) {
        this.missingFiles = missingFiles;
    }

    public Map<String, List<String>> getMissingKeys() {
        return missingKeys;
    }

    public void setMissingKeys(Map<String, List<String>> missingKeys) {
        this.missingKeys = missingKeys;
    }
}
