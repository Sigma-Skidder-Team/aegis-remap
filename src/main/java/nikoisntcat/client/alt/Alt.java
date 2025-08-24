package nikoisntcat.client.alt;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import nikoisntcat.client.utils.interfaces.ISession;

import java.util.UUID;

public class Alt {
    public final Session.AccountType field2428;
    public final String field2429;
    public final String field2430;
    public final UUID field2431;

    public Alt(String string, UUID uUID, String string2, Session.AccountType class_3212) {
        this.field2430 = string;
        this.field2431 = uUID;
        this.field2429 = string2;
        this.field2428 = class_3212;
    }

    public void method1990() {
        MinecraftClient class_3102 = MinecraftClient.getInstance();
        ((ISession) class_3102.getSession()).setAccessToken(this.field2429);
        ((ISession) class_3102.getSession()).setAccountType(this.field2428);
        ((ISession) class_3102.getSession()).setUserName(this.field2430);
        ((ISession) class_3102.getSession()).setUuid(this.field2431);
    }
}