package hw.zako.visiblechat.util;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "visiblechat")
public class Settings implements ConfigData {
    @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
    @ConfigEntry.Gui.Tooltip
    private long chatRenderDelay = 10L;

    @ConfigEntry.BoundedDiscrete(min = 5, max = 100)
    @ConfigEntry.Gui.Tooltip
    private int maxLineSize = 25;

    public long getChatRenderDelay() {
        return chatRenderDelay;
    }

    public int getMaxLineSize() {
        return maxLineSize;
    }
}