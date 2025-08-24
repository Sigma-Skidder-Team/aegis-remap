package nikoisntcat;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.math.random.Random;
import nikoisntcat.client.alt.Alt;
import nikoisntcat.client.managers.*;
import nikoisntcat.client.screens.ClickGuiScreen;
import nikoisntcat.client.utils.Class231;
import nikoisntcat.client.utils.math.Class281;

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
    public static Class281 field2313;
    public static EventManager eventManager;
    public static Class326 field2315;
    public static Class231 field2316;
    public static List<Alt> alts = new ArrayList<>();
    public static ModuleManager moduleManager;
    public static ConfigManager configManager;
    public static Random field2320 = Random.create();
    public static Class230 field2321;

	@Override
	public void onInitialize() {
        method1897();
	}

    public static void method1897() {
        configManager.save();
    }

    public static void initClient() {
        licenseCheck = true;
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        configManager = new ConfigManager();
        configManager.createClientDirectories();
        commandManager = new CommandManager();
        field2321 = new Class230();
        field2315 = new Class326();
        field2310 = new Class223();
        field2316 = new Class231();
        motionManager = new MotionManager();
        clickGui = new ClickGuiScreen();
        field2313 = new Class281();
        field2313.method1789();
        clickGui.method2055();
        licenseCheck = false;
    }
}