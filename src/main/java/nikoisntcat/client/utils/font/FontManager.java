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
    public static FontRenderer light22;
    public static FontRenderer light28;
    public static FontRenderer thin26;
    public static FontRenderer light20;
    public static FontRenderer jelloLight14;
    public static FontRenderer medium20;
    public static FontRenderer thin24;
    public static FontRenderer thin28;
    public static FontRenderer thin20;
    public static FontRenderer thin30;
    private static final Map<Integer, FontRenderer> frMaps;
    public static FontRenderer light26;
    private static final Logger logger;
    public static FontRenderer jelloLight25;
    public static FontRenderer light24;
    public static FontRenderer light30;
    public static FontRenderer thin18;
    public static FontRenderer medium16;
    public static FontRenderer light18;
    public static FontRenderer icon48;
    public static FontRenderer thin22;

    static {
        logger = LoggerFactory.getLogger(FontManager.class);
        frMaps = new HashMap<>();
    }

    public static FontRenderer cachedCreate(int size) {
        if (frMaps.containsKey(size)) {
            return frMaps.get(size);
        }
        try {
            FontRenderer fontRenderer = FontManager.createFont(size, "icon");
            frMaps.put(size, fontRenderer);
            return fontRenderer;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @NotNull
    public static FontRenderer createFont(float size, String name) throws IOException, FontFormatException {
        var ttf = Objects.requireNonNull(AegisClient.class.getClassLoader().getResourceAsStream("assets/fonts/" + name + ".ttf"));
        return new FontRenderer(Font.createFont(Font.TRUETYPE_FONT, ttf).deriveFont(Font.PLAIN, size / 2.0f), size / 2.0f);
    }
}
