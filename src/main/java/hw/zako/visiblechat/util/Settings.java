package hw.zako.visiblechat.util;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "visiblechat")
public class Settings implements ConfigData {

    @ConfigEntry.BoundedDiscrete(min=1L, max=30L)
    @ConfigEntry.Gui.Tooltip
    private long chatRenderDelay = 10L;
    @ConfigEntry.BoundedDiscrete(min=5L, max=100L)
    @ConfigEntry.Gui.Tooltip
    private int maxLineSize = 25;
    @ConfigEntry.BoundedDiscrete(min=5L, max=100L)
    @ConfigEntry.Gui.Tooltip
    private int renderOffset = 5;
    private boolean enabled = true;

    public long getChatRenderDelay() {
        return this.chatRenderDelay;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getRenderOffset() {
        return renderOffset;
    }

    public int getMaxLineSize() {
        return this.maxLineSize;
    }
}