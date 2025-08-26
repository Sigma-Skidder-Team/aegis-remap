package nikoisntcat.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.PacketSendEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ClientConnection.class})
public class MixinClientConnection {
    @Inject(at = @At("HEAD"), method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;Z)V", cancellable=true)
    private void sendInternal(Packet<?> packet, @Nullable PacketCallbacks __, boolean ___, CallbackInfo ci) {
        // nice packet hooking system bro, you could just hook send(Packet) instead
        // and then send it silently using the overload this is hooking...
        if (!AegisClient.packetUtil.sendPacketWithoutEvent()) {
            PacketSendEvent packetSendEvent = new PacketSendEvent(packet);
            AegisClient.eventManager.onSendPacket(packetSendEvent);
            if (packetSendEvent.isCancelled()) {
                ci.cancel();
            }
        }
    }
}
