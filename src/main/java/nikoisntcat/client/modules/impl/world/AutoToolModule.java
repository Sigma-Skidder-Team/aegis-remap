package nikoisntcat.client.modules.impl.world;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.PacketSendEvent;
import nikoisntcat.client.managers.Class223;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.utils.PlayerUtil;
import nikoisntcat.client.utils.player.Class205;

public class AutoToolModule extends Module {
    public BooleanSetting field1723;
    private int field1724;
    private boolean field1725;
    public BooleanSetting field1726 = new BooleanSetting("Weapon", false);
    private boolean field1727;
    static Object field1728;

    @Override
    public void onTick() {
        if (!PlayerUtil.nullCheck()) {
            if (this.field1725) {
                this.field1724--;
                if (this.field1724 == 0) {
                    this.field1725 = false;
                    this.method1277();
                }
            }

            FuckerModule var1 = (FuckerModule) AegisClient.moduleManager.field2010.get(FuckerModule.class);
            if (!mc.options.attackKey.isPressed() && !var1.field1748 && !this.field1725) {
                this.method1277();
            }
        }
    }

    @Override
    public void onSendPacket(PacketSendEvent event) {
        if (event.getPacket() instanceof PlayerActionC2SPacket var3) {
            int var4 = Class205.method1388(mc.world.getBlockState(var3.getPos()));
            if (var4 != -1 && (!this.field1725 || !this.field1723.getValue())) {
                this.field1725 = false;
                this.field1727 = true;
                Class223.method1439(var4, false);
            }
        }

        if (this.field1726.getValue()) {
            Packet var5 = event.getPacket();
            if (var5 instanceof PlayerInteractEntityC2SPacket && ((PlayerInteractEntityC2SPacket) var5).type == PlayerInteractEntityC2SPacket.ATTACK) {
                int var6 = Class205.method1388(null);
                this.field1724 = 20;
                if (var6 != -1) {
                    this.field1725 = true;
                    this.field1727 = true;
                    Class223.method1439(var6, false);
                }
            }
        }
    }

    public AutoToolModule() {
        super("AutoTool", 0, Category.WORLD);
        this.field1723 = new BooleanSetting("WeaponFirst", false, e -> this.field1726.getValue());
        this.field1727 = false;
        this.field1725 = false;
        this.field1724 = 0;
    }

    private void method1277() {
        this.field1725 = false;
        if (this.field1727) {
            this.field1727 = false;
            Class223.method1277();
        }
    }
}
