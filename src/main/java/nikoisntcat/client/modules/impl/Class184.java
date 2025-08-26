package nikoisntcat.client.modules.impl;

import java.util.List;

import net.minecraft.block.AirBlock;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround;
import net.minecraft.util.math.Vec3d;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.PacketSendEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.settings.impl.NumberSetting;
import nikoisntcat.client.utils.Class228;
import nikoisntcat.client.utils.render.Notification;
import org.joml.Vector2f;

public class Class184 extends Module {
    private int field1790;
    private boolean field1791;
    private Vec3d field1792;
    private boolean field1793;
    private Vec3d field1794;
    private Vec3d field1795;
    private Vector2f field1796;
    private Vec3d field1797;
    public NumberSetting field1798;
    public NumberSetting field1799;
    private boolean field1800;
    public ModeSetting field1801 = new ModeSetting(
            (String) method1313('\u0004'), (String) method1313('\u0000'), List.of((String) method1313('\u0000'), (String) method1313('\u0001'))
    );
    private Vec3d field1802;
    private Vector2f field1803;

    @Override
    public native void onPreTick();

    public Class184() {
        super((String) method1313('\u0003'), 0, false, Category.MOVE);
        this.field1798 = new NumberSetting((String) method1313('\u0005'), 3.0, 5.0, 0.0, 0.1, e -> this.field1801.getValue().equals((String) method1313('\u0000')));
        this.field1799 = new NumberSetting(
                (String) method1313('\u0006'), 50.0, 100.0, 20.0, 1.0, e -> this.field1801.getValue().equals((String) method1313('\u0001'))
        );
        this.field1800 = false;
    }

    @Override
    public void onSendPacket(PacketSendEvent event) {
        if (this.field1793 && this.field1801.getValue().equals((String) method1313('\u0001')) && event.getPacket() instanceof PlayerMoveC2SPacket) {
            this.field1793 = false;
            event.cancel();
            NotificationModule.addNotification(new Notification(this.name, this.name, (String) method1313('\u0002'), this.isEnabled(), false, false));
            AegisClient.packetUtil
                    .sendPacketSilently(
                            new PositionAndOnGround(mc.player.getPos().getX(), mc.player.getPos().getY() + 1.0 - Math.random(), mc.player.getPos().getZ(), false, false)
                    );
        }
    }

    public static boolean method1311(double posX, double posY, double posZ) {
        for (int var6 = (int) posY; var6 > -1; var6--) {
            if (!(mc.world.getBlockState(Class228.method1489(posX, (double) var6, posZ)).getBlock() instanceof AirBlock)) {
                return false;
            }
        }

        return true;
    }

    private static native Object method1313(char var0);
}
