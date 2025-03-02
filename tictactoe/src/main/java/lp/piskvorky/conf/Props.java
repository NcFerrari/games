package lp.piskvorky.conf;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Props {

    private final Properties properties = new Properties();

    public Props() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                properties.setProperty("width", DefaultValues.DEFAULT_WIDTH);
                properties.setProperty("height", DefaultValues.DEFAULT_HEIGHT);
                properties.setProperty("field-size", DefaultValues.DEFAULT_FIELD_SIZE);
                properties.setProperty("count-for-win", DefaultValues.DEFAULT_COUNT_FOR_WIN);
                properties.setProperty("used-color", DefaultValues.DEFAULT_USED_COLOR);
                properties.setProperty("victory-color", DefaultValues.DEFAULT_VICTORY_COLOR);
            } else {
                try (InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
                    properties.load(reader);
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("IO Exception");
        }
    }

    public double getPropertyDouble(String propertyName) {
        return Double.parseDouble(getPropertyString(propertyName));
    }

    public int getPropertyInt(String propertyName) {
        return Integer.parseInt(getPropertyString(propertyName));
    }

    public String getPropertyString(String propertyName) {
        return (String) properties.get(propertyName);
    }
}
