package nikoisntcat.client.modules.impl.misc;

import net.minecraft.entity.LivingEntity;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;

import java.util.ArrayList;
import java.util.List;

public class AntiBotModule extends Module {
    public BooleanSetting field1918 = new BooleanSetting("CheckOnGround", false);
    private final List field1919 = new ArrayList<>();
    private final List field1920 = new ArrayList<>();
    public static AntiBotModule instance;
    public BooleanSetting field1922 = new BooleanSetting("CheckID", false);

    private AntiBotModule() {
        super("AntiBot", 0, false, Category.MISC);
    }

    static {
        instance = new AntiBotModule();
    }

    public boolean method1380(LivingEntity entity) {
        if (!this.isEnabled()) {
            return false;
        }
        if (this.field1918.getValue() && !this.field1920.contains(entity.getId())) {
            return true;
        }
        String string = entity.getName().getString();
        return this.field1922.getValue() && (string.contains("[") || string.contains("/") || string.contains("|") || string.contains(" ") || string.contains("ITEM SHOP") || string.isEmpty());
    }

    @Override
    public void onSetWorld() {
        this.field1920.clear();
        this.field1919.clear();
    }
}