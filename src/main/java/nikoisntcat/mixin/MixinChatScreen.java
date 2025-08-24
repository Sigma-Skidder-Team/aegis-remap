package nikoisntcat.mixin;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.ChatMessageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ChatScreen.class})
public class MixinChatScreen
extends Screen {
    protected MixinChatScreen(Text title) {
        super(title);
    }

    @Inject(method={"sendMessage"}, at={@At(value="HEAD")}, cancellable=true)
    public void sendMessage(String chatText, boolean addToHistory, CallbackInfo callbackInfo) {
        ChatMessageEvent messageEvent = new ChatMessageEvent(chatText);
        AegisClient.eventManager.onChat(messageEvent);
        if (messageEvent.isCancelled()) {
            this.client.inGameHud.getChatHud().addToMessageHistory(chatText);
            callbackInfo.cancel();
        }
    }
}
