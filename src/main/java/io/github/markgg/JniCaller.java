package io.github.markgg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

public class JniCaller {
    public static final Logger LOGGER = LoggerFactory.getLogger("JniCaller");

    private final ClassLoader loader;

    public static final String AbstractModule = "skidonion.Dwz.\u2000\u2000\u2000\u2000\u2000\u2000\u2009\u200b";
    public static final String KillAuraModule = "";

    public JniCaller(File jarFile) throws Exception {
        if (!jarFile.exists()) {
            LOGGER.error("Obfuscated Aegis jar is nowhere to be found - {}", jarFile.getAbsolutePath());
            throw new RuntimeException("fuckass");
        }

        this.loader = new URLClassLoader(new URL[]{jarFile.toURL()}, getClass().getClassLoader());
    }

    public Object load(String className) {
        try {
            Class<?> clazz = Class.forName(className.replace('/', '.'), true, loader);

            Constructor<?> ctor = clazz.getDeclaredConstructor();
            ctor.setAccessible(true);

            Object instance = ctor.newInstance();

            LOGGER.error("Class with the name {} was found", className);

            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}