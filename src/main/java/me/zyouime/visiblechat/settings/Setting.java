package me.zyouime.visiblechat.settings;

import com.google.gson.JsonObject;
import me.zyouime.visiblechat.config.ModConfig;

import java.lang.reflect.Type;

public class Setting<T> {

    private T value;
    private final String configKey;

    public Setting(String configKey, Type type) {
        this.configKey = configKey;
        JsonObject jsonObject = ModConfig.GSON.toJsonTree(ModConfig.configData).getAsJsonObject();
        this.value = ModConfig.GSON.fromJson(jsonObject.get(configKey), type);
    }

    public void save() {
        ModConfig.loadConfig();
        ModConfig.configData.setField(configKey, value);
        ModConfig.saveConfig();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
