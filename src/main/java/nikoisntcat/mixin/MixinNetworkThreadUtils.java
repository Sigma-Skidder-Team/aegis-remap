package nikoisntcat.mixin;

import com.mojang.logging.LogUtils;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.OffThreadException;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.thread.ThreadExecutor;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.PacketReceiveEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({NetworkThreadUtils.class})
public class MixinNetworkThreadUtils {
    /**
     * @author idk
     * @reason black monkeys :wilted_rose:
     */
    @Overwrite
    public static <T extends PacketListener> void forceMainThread(Packet<T> packet, T listener, ThreadExecutor<?> engine) throws OffThreadException {
        if (!engine.isOnThread()) {
            engine.executeSync(() -> {
                if (listener.accepts(packet)) {
                    try {
                        PacketReceiveEvent packetReceiveEvent = new PacketReceiveEvent(packet);
                        AegisClient.eventManager.onReceivePacket(packetReceiveEvent);
                        if (packetReceiveEvent.isCancelled()) {
                            return;
                        }

                        packet.apply(listener);
                    } catch (Exception e) {
                        if (e instanceof CrashException crashException && crashException.getCause() instanceof OutOfMemoryError) {
                            throw NetworkThreadUtils.createCrashException(e, packet, listener);
                        }

                        listener.onPacketException(packet, e);
                    }
                } else {
                    LogUtils.getLogger().debug("Ignoring packet due to disconnection: {}", packet);
                }
            });
            throw OffThreadException.INSTANCE;
        }
    }
}
