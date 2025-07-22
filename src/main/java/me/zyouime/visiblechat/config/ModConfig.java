package me.zyouime.visiblechat.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.labymod.addon.AddonLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfig {
    private static final File FILE = new File(AddonLoader.getConfigDirectory(), "visiblechat.json");
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static ConfigData configData;

    public static void loadConfig() {
        try (FileReader fileReader = new FileReader(FILE)) {
            ConfigData configData1 = GSON.fromJson(fileReader, ConfigData.class);
            if (configData1 != null) {
                configData = configData1;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveConfig() {
        try (FileWriter fileWriter = new FileWriter(FILE)) {
            GSON.toJson(configData, fileWriter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void initialize() {
        try {
            if (!FILE.exists()) {
                FILE.createNewFile();
                try (FileWriter fileWriter = new FileWriter(FILE)) {
                    configData = new ConfigData();
                    GSON.toJson(configData, fileWriter);
                }
            } else loadConfig();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить конфиг, ошибка: " + e);
        }
    }
}
