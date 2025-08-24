package nikoisntcat.client.utils.interfaces;

import net.minecraft.client.session.Session;

import java.util.Optional;
import java.util.UUID;

public interface ISession {
    public void setUserName(String var1);

    public void setAccessToken(String var1);

    public void setAccountType(Session.AccountType var1);

    public void setClientId(Optional<String> var1);

    public void setXuid(Optional<String> var1);

    public void setUuid(UUID var1);
}
