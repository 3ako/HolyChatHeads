package me.zyouime.visiblechat.settings;

import com.google.common.reflect.TypeToken;

public class BooleanSetting extends Setting<Boolean> {

    public BooleanSetting(String configKey) {
        super(configKey, new TypeToken<Boolean>() {}.getType());
    }
}
