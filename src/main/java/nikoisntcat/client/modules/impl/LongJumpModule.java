package nikoisntcat.client.modules.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import nikoisntcat.client.events.impl.MoveInputEvent;
import nikoisntcat.client.events.impl.PacketReceiveEvent;
import nikoisntcat.client.managers.Class223;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.settings.impl.NumberSetting;
import nikoisntcat.client.utils.Class224;
import nikoisntcat.client.utils.player.Class205;

public class LongJumpModule extends Module {
    public NumberSetting field1716;
    int field1717;
    private LinkedList field1718;
    public ModeSetting mode = new ModeSetting("Mode", "Times", List.of("Times"));
    private LinkedBlockingQueue field1720;
    static Object field1721;

    @Override
    public void onMoveInput(MoveInputEvent moveInputEvent) {
        moveInputEvent.field1981 = true;
    }

    private void method1272() {
        while (!this.field1720.isEmpty()) {
            ((Packet) this.field1720.poll()).apply(mc.getNetworkHandler());
        }
    }

    public LongJumpModule() {
        super("LongJump", 0, Category.MOVE);
        this.field1716 = new NumberSetting("Times", 1.0, 3.0, 1.0, 1.0);
        this.field1720 = new LinkedBlockingQueue();
        this.field1718 = new LinkedList();
    }

    @Override
    public void onTick() {
        if (!Class224.nullCheck()) {
            if (mode.getValue().equals("Fireball")) {
                if (mc.options.attackKey.wasPressed()) {
                    method1273();
                }
            }
        }
    }

    @Override
    public void onEnable() {
        this.field1717 = Class205.method1390();
    }

    @Override
    public void onReceivePacket(PacketReceiveEvent event) {
        if (Class224.nullCheck()) {
            this.setState(false);
        } else {
            String var2 = this.mode.getValue();
            switch (var2) {
                case "Fireball":
                    if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket var5 && mc.player.getId() == var5.getEntityId()
                            || event.getPacket() instanceof ExplosionS2CPacket) {
                        this.field1718.add(event.getPacket());
                    }

                    this.field1720.add(event.getPacket());
                    event.cancel();
            }
        }
    }

    @Override
    public void onDisable() {
        if (this.field1717 != -1) {
            Class223.method1277();
        }

        this.method1272();
        this.field1717 = -1;
    }

    private void method1273() {
        boolean var1 = false;
        if (this.field1718.size() <= 1) {
            this.method1272();
        } else {
            label33:
            while (true) {
                if (!this.field1718.isEmpty()) {
                    Packet var2;
                    if (var1) {
                        var2 = (Packet) this.field1718.get(0);
                    } else {
                        var2 = (Packet) this.field1718.get(1);
                    }

                    Packet var3;
                    do {
                        if (this.field1720.isEmpty()) {
                            continue label33;
                        }

                        var3 = (Packet) this.field1720.poll();
                        var3.apply(mc.getNetworkHandler());
                    } while (!var3.equals(var2));

                    if (!var1) {
                        var1 = true;
                        continue;
                    }
                }

                this.field1718.remove(0);
                break;
            }
        }
    }
}
