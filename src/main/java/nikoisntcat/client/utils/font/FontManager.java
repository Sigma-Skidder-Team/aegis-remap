package nikoisntcat.client.utils.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import nikoisntcat.AegisClient;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FontManager {
    public static Class160 field2406;
    public static Class160 field2407;
    public static Class160 field2408;
    public static Class160 field2409;
    public static Class160 field2410;
    public static Class160 field2411;
    public static Class160 field2412;
    public static Class160 field2413;
    public static Class160 field2414;
    public static Class160 field2415;
    private static Map field2416;
    public static Class160 field2417;
    private static final Logger logger;
    public static Class160 field2419;
    public static Class160 field2420;
    public static Class160 field2421;
    public static Class160 thin18;
    public static Class160 field2423;
    public static Class160 field2424;
    public static Class160 icon48;
    public static Class160 field2426;

    static {
        logger = LoggerFactory.getLogger(FontManager.class);
        field2416 = new HashMap();
    }

    public static Class160 method1987(int size) {
        if (field2416.containsKey(size)) {
            return (Class160)field2416.get(size);
        }
        try {
            Class160 class160 = FontManager.createFont(size, "icon");
            field2416.put(size, class160);
            return class160;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @NotNull
    public static Class160 createFont(float size, String name) throws IOException, FontFormatException {
        return new Class160(Font.createFont(0, Objects.requireNonNull(AegisClient.class.getClassLoader().getResourceAsStream("assets/fonts/" + name + ".ttf"))).deriveFont(0, size / 2.0f), size / 2.0f);
    }
}
