package util;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {
    public static String get(String key) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("config/application.properties"));
            return props.getProperty(key);
        } catch (Exception e) {
            return null;
        }
    }
}
