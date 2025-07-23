package me.zyouime.visiblechat.settings;

import com.google.common.reflect.TypeToken;

public class NumberSetting extends Setting<Integer> {

    public NumberSetting(String configKey) {
        super(configKey, new TypeToken<Integer>() {}.getType());
    }

    public String toString() {
        return Integer.toString(this.getValue());
    }

}
