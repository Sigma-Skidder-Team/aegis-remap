package nikoisntcat.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {MultiplayerScreen.class})
public abstract class MixinMultiplayerScreen
        extends Screen {
    @Shadow
    public abstract void close();

    protected MixinMultiplayerScreen(Text title) {
        super(title);
    }

    @Inject(method = {"init"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/DirectionalLayoutWidget;refreshPositions()V")})
    public void init(CallbackInfo ci, @Local DirectionalLayoutWidget layoutWidget) {
        DirectionalLayoutWidget directionalLayoutWidget = DirectionalLayoutWidget.vertical();
        //ButtonWidget account = (ButtonWidget)this.addDrawableChild((Element)ButtonWidget.builder((Text)Text.empty().append("Account"), button -> this.client.setScreen((Screen)new Class269(this))).width(60).build());
        //directionalLayoutWidget.add((Widget)account);
        directionalLayoutWidget.refreshPositions();
        SimplePositioningWidget.setPos((Widget) directionalLayoutWidget, (int) 0, (int) 0, (int) 64, (int) 32);
    }
}
