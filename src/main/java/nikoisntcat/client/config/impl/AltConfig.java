package nikoisntcat.client.config.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.session.Session;
import nikoisntcat.AegisClient;
import nikoisntcat.client.alt.Alt;
import nikoisntcat.client.config.Config;

import java.io.*;
import java.util.Map;
import java.util.UUID;

public class AltConfig extends Config {
    public AltConfig(File file) {
        super(file);
    }

    @Override
    public void method1974() throws IOException {
        if (!this.method1979()) {
            this.method1978();
        }
        JsonObject jsonObject = new JsonObject();
        for (Alt alt : AegisClient.alts) {
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("AccessToken", alt.accessToken);
            jsonObject2.addProperty("Type", alt.type.getName());
            jsonObject2.addProperty("UUID", alt.uuid.toString());
            jsonObject.add(alt.username, jsonObject2);
        }
        PrintWriter printWriter = new PrintWriter(new FileWriter(this.getFile()));
        printWriter.println(Config.gson.toJson(jsonObject));
        printWriter.close();
    }

    @Override
    public void method1975() throws IOException {
        JsonElement jsonElement = new JsonParser().parse(new BufferedReader(new FileReader(this.getFile())));
        if (jsonElement instanceof JsonNull) {
            return;
        }
        for (Map.Entry entry : jsonElement.getAsJsonObject().entrySet()) {
            String string = (String) entry.getKey();
            JsonObject jsonObject = (JsonObject) entry.getValue();
            try {
                String string2 = jsonObject.get("AccessToken").getAsString();
                String string3 = jsonObject.get("Type").getAsString();
                String string4 = jsonObject.get("UUID").getAsString();
                Alt newAlt = new Alt(string, UUID.fromString(string4), string2, Session.AccountType.byName((String) string3));
                AegisClient.alts.add(newAlt);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}