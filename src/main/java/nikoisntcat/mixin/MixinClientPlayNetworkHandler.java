package nikoisntcat.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BundleS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.thread.ThreadExecutor;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.Class212;
import nikoisntcat.client.modules.impl.nrsModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {ClientPlayNetworkHandler.class})
public abstract class MixinClientPlayNetworkHandler
        extends ClientCommonNetworkHandler {
    @Shadow
    protected abstract void playSpawnSound(Entity var1);

    protected MixinClientPlayNetworkHandler(MinecraftClient client, ClientConnection connection, ClientConnectionState connectionState) {
        super(client, connection, connectionState);
    }

    @Inject(method = {"onPlayerPositionLook"}, at = {@At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/network/NetworkThreadUtils;forceMainThread(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;Lnet/minecraft/util/thread/ThreadExecutor;)V")}, cancellable = true)
    public void onPlayerPositionLook(PlayerPositionLookS2CPacket packet, CallbackInfo ci) {
        nrsModule nrs = (nrsModule) AegisClient.moduleManager.field2010.get(nrsModule.class);
        if (nrs.isEnabled()) {
            //nrs.method1298(packet, (class_1657) this.field_45588.field_1724, this.connection);
            ci.cancel();
        }
    }

    @Overwrite
    public void onBundle(BundleS2CPacket packet) {
        NetworkThreadUtils.forceMainThread((Packet) packet, (PacketListener) (this), (ThreadExecutor) this.client);
        for (Packet packet2 : packet.getPackets()) {
            Class212 event = new Class212(packet2);
            AegisClient.eventManager.method2011(event);
            if (event.isCancelled()) continue;
            packet2.apply(this);
        }
    }
}