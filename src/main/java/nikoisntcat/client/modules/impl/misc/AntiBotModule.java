package nikoisntcat.client.modules.impl.misc;

import net.minecraft.entity.LivingEntity;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;

import java.util.ArrayList;
import java.util.List;

public class AntiBotModule extends Module {
    public BooleanSetting checkOnGround = new BooleanSetting("CheckOnGround", false);
    private final List field1919 = new ArrayList<>();
    private final List<Integer> nonBotIDs = new ArrayList<>();
    public static AntiBotModule instance;
    public BooleanSetting checkName = new BooleanSetting("CheckID", false);

    private AntiBotModule() {
        super("AntiBot", 0, false, Category.MISC);
    }

    static {
        instance = new AntiBotModule();
    }

    public boolean isBot(LivingEntity entity) {
        if (!this.isEnabled()) {
            return false;
        }
        // :wilted_rose::wilted_rose::wilted_rose:
        if (this.checkOnGround.getValue() && !this.nonBotIDs.contains(entity.getId())) {
            return true;
        }
        String name = entity.getName().getString();
        // :wilted_rose:
        return this.checkName.getValue()
                && (name.contains("[") || name.contains("/")
                || name.contains("|") || name.contains(" ") || name.contains("ITEM SHOP")
                || name.isEmpty());
    }

    @Override
    public void onSetWorld() {
        this.nonBotIDs.clear();
        this.field1919.clear();
    }
}