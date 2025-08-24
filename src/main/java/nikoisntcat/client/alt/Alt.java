package nikoisntcat.client.alt;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import nikoisntcat.client.utils.interfaces.ISession;

import java.util.UUID;

public class Alt {
    public final Session.AccountType type;
    public final String accessToken;
    public final String username;
    public final UUID uuid;

    public Alt(String string, UUID uUID, String string2, Session.AccountType class_3212) {
        this.username = string;
        this.uuid = uUID;
        this.accessToken = string2;
        this.type = class_3212;
    }

    public void login() {
        MinecraftClient mc = MinecraftClient.getInstance();
        ((ISession) mc.getSession()).setAccessToken(this.accessToken);
        ((ISession) mc.getSession()).setAccountType(this.type);
        ((ISession) mc.getSession()).setUserName(this.username);
        ((ISession) mc.getSession()).setUuid(this.uuid);
    }
}