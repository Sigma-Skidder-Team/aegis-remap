package nikoisntcat.client.config.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nikoisntcat.AegisClient;
import nikoisntcat.client.config.Config;
import nikoisntcat.client.managers.ModuleManager;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.Setting;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.ColorSetting;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.settings.impl.NumberSetting;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Map;

public class ModuleConfig extends Config {
    public ModuleConfig(File file) {
        super(file);
    }

    @Override
    public void method1974() throws IOException {
        if (!this.method1979()) {
            this.method1978();
        }
        JsonObject jsonObject = new JsonObject();
        for (Module module : AegisClient.moduleManager.modules) {
            JsonObject jsonObject2 = new JsonObject();

            jsonObject2.addProperty("state", Boolean.valueOf(module.isEnabled()));
            jsonObject2.addProperty("key", (Number) module.key);
            try {
                for (Field field : module.getClass().getFields()) {
                    Object object;
                    Object object2 = field.get(module);
                    if (object2 instanceof Setting) {
                        object = (Setting) object2;
                        if (object instanceof NumberSetting) {
                            object2 = (NumberSetting) object;
                            jsonObject2.addProperty(((Setting) object2).getName(), (Number) ((NumberSetting) object2).getValue());
                        }
                        if (object instanceof BooleanSetting) {
                            object2 = (BooleanSetting) object;
                            jsonObject2.addProperty(((Setting) object2).getName(), Boolean.valueOf(((BooleanSetting) object2).getValue()));
                        }
                        if (object instanceof ModeSetting) {
                            object2 = (ModeSetting) object;
                            jsonObject2.addProperty(((Setting) object2).getName(), ((ModeSetting) object2).getValue());
                        }
                    }
                    if (!((object2 = field.get(module)) instanceof ColorSetting)) continue;
                    object = (ColorSetting) object2;
                    jsonObject2.addProperty(((ColorSetting) object).field2327.getName(), (Number) ((ColorSetting) object).field2327.getValue());
                    jsonObject2.addProperty(((ColorSetting) object).field2324.getName(), (Number) ((ColorSetting) object).field2324.getValue());
                    jsonObject2.addProperty(((ColorSetting) object).field2328.getName(), (Number) ((ColorSetting) object).field2328.getValue());
                    jsonObject2.addProperty(((ColorSetting) object).field2326.getName(), (Number) ((ColorSetting) object).field2326.getValue());
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            jsonObject.add(module.name, (JsonElement) jsonObject2);
        }
        PrintWriter printWriter = new PrintWriter(new FileWriter(this.getFile()));
        printWriter.println(Config.gson.toJson((JsonElement) jsonObject));
        printWriter.close();
    }

    @Override
    public void method1975() throws IOException {
        JsonElement jsonElement = new JsonParser().parse((Reader) new BufferedReader(new FileReader(this.getFile())));
        if (jsonElement instanceof JsonNull) {
            return;
        }

        for (Map.Entry entry : jsonElement.getAsJsonObject().entrySet()) {
            Module module = ModuleManager.method1481((String) entry.getKey());
            if (module == null) continue;
            JsonObject jsonObject = (JsonObject) entry.getValue();
            if (jsonObject.has("state")) {
                module.setState(jsonObject.get("state").getAsBoolean());
            }

            if (jsonObject.has("key")) {
                module.setKey(jsonObject.get("key").getAsInt());
            }

            try {
                for (Field field : module.getClass().getFields()) {
                    Object object;
                    Object object2 = field.get(module);
                    if (object2 instanceof Setting && jsonObject.has(((Setting) (object = (Setting) object2)).getName())) {
                        Setting class261;
                        if (object instanceof BooleanSetting) {
                            class261 = (BooleanSetting) object;
                            ((BooleanSetting) class261).setValue(jsonObject.get(class261.getName()).getAsBoolean());
                        }
                        if (object instanceof NumberSetting) {
                            class261 = (NumberSetting) object;
                            ((NumberSetting) class261).setValue(jsonObject.get(class261.getName()).getAsDouble());
                        }
                        if (object instanceof ModeSetting) {
                            class261 = (ModeSetting) object;
                            ((ModeSetting) class261).setValue(jsonObject.get(class261.getName()).getAsString());
                        }
                    }
                    if (!(object2 instanceof ColorSetting)) continue;
                    object = (ColorSetting) object2;
                    if (jsonObject.has(((ColorSetting) object).field2327.getName())) {
                        ((ColorSetting) object).field2327.setValue(jsonObject.get(((ColorSetting) object).field2327.getName()).getAsInt());
                    }
                    if (jsonObject.has(((ColorSetting) object).field2324.getName())) {
                        ((ColorSetting) object).field2324.setValue(jsonObject.get(((ColorSetting) object).field2324.getName()).getAsInt());
                    }
                    if (jsonObject.has(((ColorSetting) object).field2328.getName())) {
                        ((ColorSetting) object).field2328.setValue(jsonObject.get(((ColorSetting) object).field2328.getName()).getAsInt());
                    }
                    if (!jsonObject.has(((ColorSetting) object).field2326.getName())) continue;
                    ((ColorSetting) object).field2326.setValue(jsonObject.get(((ColorSetting) object).field2326.getName()).getAsInt());
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
