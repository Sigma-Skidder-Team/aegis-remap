package nikoisntcat.mixin;

import com.mojang.util.UndashedUuid;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.session.Session;
import net.minecraft.client.session.Session.AccountType;
import nikoisntcat.client.utils.interfaces.ISession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Session.class})
public class MixinSession implements ISession {
    @Unique
    private String iUsername = "";
    @Unique
    private UUID iUuid = null;
    @Unique
    private String iAccessToken = "";
    @Unique
    private Optional<String> iXuid = Optional.empty();
    @Unique
    private Optional<String> iClientId = Optional.empty();
    @Unique
    private AccountType iAccountType = null;

    @Inject(
            method = {"<init>"},
            at = {@At("TAIL")}
    )
    public void init(String username, UUID uuid, String accessToken, Optional xuid, Optional clientId, AccountType accountType, CallbackInfo ci) {
        this.iXuid = xuid;
        this.iUsername = username;
        this.iAccountType = accountType;
        this.iAccessToken = accessToken;
        this.iUuid = uuid;
        this.iClientId = clientId;
    }

    @Override
    public void setUserName(String string) {
        this.iUsername = string;
    }

    @Override
    public void setAccessToken(String string) {
        this.iAccessToken = string;
    }

    @Override
    public void setAccountType(AccountType accountType) {
        this.iAccountType = accountType;
    }

    @Override
    public void setClientId(Optional<String> clientId) {
        this.iClientId = clientId;
    }

    @Override
    public void setXuid(Optional<String> xuid) {
        this.iXuid = xuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.iUuid = uuid;
    }

    @Inject(
            method = {"getUuidOrNull"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void getUuidOrNull(CallbackInfoReturnable<UUID> cir) {
        cir.setReturnValue(this.iUuid);
    }

    @Inject(
            method = {"getSessionId"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void getSessionId(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("token:" + this.iAccessToken + ":" + UndashedUuid.toString(this.iUuid));
    }

    @Inject(
            method = {"getUsername"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void p(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(this.iUsername);
    }

    @Inject(
            method = {"getAccessToken"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void getAccessToken(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(this.iAccessToken);
    }

    @Inject(
            method = {"getClientId"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void getClientId(CallbackInfoReturnable<Optional<String>> cir) {
        cir.setReturnValue(this.iClientId);
    }

    @Inject(
            method = {"getXuid"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void getXuid(CallbackInfoReturnable<Optional<String>> cir) {
        cir.setReturnValue(this.iXuid);
    }

    @Inject(
            method = {"getAccountType"},
            at = {@At("HEAD")},
            cancellable = true
    )
    public void getAccountType(CallbackInfoReturnable<AccountType> cir) {
        cir.setReturnValue(this.iAccountType);
    }
}
