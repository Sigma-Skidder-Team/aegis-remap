package nikoisntcat;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.math.random.Random;
import nikoisntcat.client.alt.Alt;
import nikoisntcat.client.managers.*;
import nikoisntcat.client.screens.clickgui.ClickGuiScreen;
import nikoisntcat.client.utils.RotationUtil;
import nikoisntcat.client.utils.math.Shader;

import java.util.ArrayList;
import java.util.List;

public class AegisClient implements ModInitializer {
    public static boolean licenseCheck = false;
    public static CommandManager commandManager;
    public static MotionManager motionManager;
    public static String CLIENT_NAME = "Aegis";
    public static Class223 field2310;
    public static ClickGuiScreen clickGui;
    public static String CLIENT_VERSION = "1.0";
    public static Shader shaders;
    public static EventManager eventManager;
    public static BlinkUtil blinkUtil;
    public static RotationUtil rotationUtil;
    public static List<Alt> alts = new ArrayList<>();
    public static ModuleManager moduleManager;
    public static ConfigManager configManager;
    public static Random field2320 = Random.create();
    public static PacketUtil packetUtil;

	@Override
	public void onInitialize() {
        saveConfig();
	}

    public static void saveConfig() {
        configManager.save();
    }

    public static void initClient() {
        licenseCheck = true;
        eventManager = new EventManager();
        moduleManager = new ModuleManager();

        configManager = new ConfigManager();
        configManager.createClientDirectories();
        commandManager = new CommandManager();
        packetUtil = new PacketUtil();
        blinkUtil = new BlinkUtil();
        field2310 = new Class223();
        rotationUtil = new RotationUtil();
        motionManager = new MotionManager();
        clickGui = new ClickGuiScreen();
        shaders = new Shader();
        shaders.method1789();
        clickGui.method2055();
        licenseCheck = false;
    }
}