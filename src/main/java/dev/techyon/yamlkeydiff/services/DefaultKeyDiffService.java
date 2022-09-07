package dev.techyon.yamlkeydiff.services;

import org.yaml.snakeyaml.Yaml;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultKeyDiffService {


  public List<String> diff(String yaml1, String yaml2) throws ParseException {
    Yaml snakeYaml = new Yaml();
    Map<String, Object> yaml1Map;
    Map<String, Object> yaml2Map;
    try {
      yaml1Map = snakeYaml.load(yaml1);
      yaml2Map = snakeYaml.load(yaml2);
    } catch (Exception e) {
      throw new ParseException("Unable to parse YAML", 0);
    }

    List<String> keyList1 = mapToKeyList(yaml1Map, "");
    List<String> keyList2 = mapToKeyList(yaml2Map, "");

    return keyList1.stream().filter(s -> !keyList2.contains(s)).collect(Collectors.toList());
  }

  private List<String> mapToKeyList(Map<String, Object> yamlMap, String upLvlKey) {
    List<String> keyList = new ArrayList<>();

    for (Map.Entry<String, Object> entry : yamlMap.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      if (value instanceof Map) {
        Map<String, Object> subMap = (Map<String, Object>) value;
        keyList.addAll(mapToKeyList(subMap, upLvlKey + "." + key));
      } else {
        String keyStr = String.format("%s.%s", upLvlKey, key).substring(1);
        keyList.add(keyStr);
      }

    }

    return keyList;
  }
}
