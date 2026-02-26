package config;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;

public class ConfigReader {
    private static Map<String, Object> config;

    static {
        loadConfig("config-qa.yaml");
    }

    public static void loadConfig(String fileName) {
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config/" + fileName)) {
            Yaml yaml = new Yaml();
            config = yaml.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config: " + fileName, e);
        }
    }

    public static String get(String key) {
        if (config.containsKey(key)) {
            return config.get(key).toString();
        }
        // Nested keys (e.g., database.url)
        if (key.contains(".")) {
            String[] parts = key.split("\\.");
            Object value = config;
            for (String part : parts) {
                if (value instanceof Map) {
                    value = ((Map<?, ?>) value).get(part);
                } else {
                    return null;
                }
            }
            return value != null ? value.toString() : null;
        }
        return null;
    }
}
