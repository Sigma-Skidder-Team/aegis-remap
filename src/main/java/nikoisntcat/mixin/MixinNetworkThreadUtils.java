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

@Mixin(value={NetworkThreadUtils.class})
public class MixinNetworkThreadUtils {
    @Overwrite
    public static <T extends PacketListener> void forceMainThread(Packet<T> packet, T listener, ThreadExecutor<?> engine) throws OffThreadException {
        if (!engine.isOnThread()) {
            engine.executeSync(() -> {
                if (listener.accepts(packet)) {
                    try {
                        PacketReceiveEvent packetReceiveEvent = new PacketReceiveEvent(packet);
                        AegisClient.eventManager.method2011(packetReceiveEvent);
                        if (packetReceiveEvent.isCancelled()) {
                            return;
                        }
                        packet.apply(listener);
                    }
                    catch (Exception var4) {
                        CrashException crashException;
                        if (var4 instanceof CrashException && (crashException = (CrashException)var4).getCause() instanceof OutOfMemoryError) {
                            throw NetworkThreadUtils.createCrashException((Exception)var4, (Packet)packet, (PacketListener)listener);
                        }
                        listener.onPacketException(packet, var4);
                    }
                } else {
                    LogUtils.getLogger().debug("Ignoring packet due to disconnection: {}", (Object)packet);
                }
            });
            throw OffThreadException.INSTANCE;
        }
    }
}
