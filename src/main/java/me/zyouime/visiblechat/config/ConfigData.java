package me.zyouime.visiblechat.config;

import java.lang.reflect.Field;

public class ConfigData {

    private int chatRenderDelay = 10;
    private int maxLineSize = 25;
    private int renderOffset = 5;
    private boolean enabled = true;

    public void setField(String fieldName, Object value) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(this, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Object getField(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getChatRenderDelay() {
        return chatRenderDelay;
    }

    public void setChatRenderDelay(int chatRenderDelay) {
        this.chatRenderDelay = chatRenderDelay;
    }

    public int getMaxLineSize() {
        return maxLineSize;
    }

    public void setMaxLineSize(int maxLineSize) {
        this.maxLineSize = maxLineSize;
    }

    public int getRenderOffset() {
        return renderOffset;
    }

    public void setRenderOffset(int renderOffset) {
        this.renderOffset = renderOffset;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
