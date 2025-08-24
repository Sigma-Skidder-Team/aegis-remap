package nikoisntcat.client.managers;

import nikoisntcat.client.config.Config;
import nikoisntcat.client.config.impl.AltConfig;
import nikoisntcat.client.config.impl.ModuleConfig;
import nikoisntcat.client.utils.MinecraftUtil;
import java.io.File;

public class ConfigManager extends MinecraftUtil {
    public final Config alts;
    public Config modules;
    public final File clientDir;
    public final File configDir;

    public void method1393() {
        try {
            if (!this.clientDir.exists()) {
                this.clientDir.mkdir();
            }
            this.alts.method1974();
            this.modules.method1974();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public ConfigManager() {
        this.clientDir = new File(ConfigManager.mc.runDirectory, "AegisClient");
        this.configDir = new File(this.clientDir, "Configs");
        this.modules = new ModuleConfig(new File(this.clientDir, "Module.json"));
        this.alts = new AltConfig(new File(this.clientDir, "Accounts.json"));
    }

    public void createClientDirectories() {
        try {
            if (!this.clientDir.exists()) {
                this.clientDir.mkdir();
            }
            if (!this.configDir.exists()) {
                this.configDir.mkdir();
            }
            if (!this.modules.method1979()) {
                this.modules.method1974();
            } else {
                this.modules.method1975();
            }
            if (!this.alts.method1979()) {
                this.alts.method1974();
            } else {
                this.alts.method1975();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
