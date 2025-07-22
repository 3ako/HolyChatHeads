package me.zyouime.visiblechat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.zyouime.visiblechat.config.ModConfig;
import me.zyouime.visiblechat.settings.BooleanSetting;
import me.zyouime.visiblechat.settings.NumberSetting;
import me.zyouime.visiblechat.settings.Setting;
import net.labymod.addon.About;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.*;
import net.labymod.utils.Material;

public class VisibleChat extends LabyModAddon {

  private static VisibleChat instance;
  public Settings settings;
  private static Cache<String, List<String>> messageCache;

  @Override
  public void onEnable() {
    instance = this;
    ModConfig.initialize();
    settings = new Settings();
    messageCache = CacheBuilder.newBuilder().expireAfterWrite(settings.chatRenderDelay.getValue(), TimeUnit.SECONDS).build();
  }

  public static VisibleChat getInstance() {
    return instance;
  }

  public static Cache<String, List<String>> getMessageCache() {
    return messageCache;
  }

  @Override
  public void init(String addonName, UUID uuid) {
    this.about = new About(uuid, addonName);
    this.fillSettings(this.getSubSettings());
    this.about.loaded = true;
  }

  @Override
  public void loadConfig() {

  }

  @Override
  protected void fillSettings(List<SettingsElement> list) {
    SliderElement chatRenderDelaySlider = new SliderElement("Время жизни текста", new ControlElement.IconData(Material.CLOCK), settings.chatRenderDelay.getValue());
    chatRenderDelaySlider.addCallback(v -> settings.chatRenderDelay.setValue(v));
    chatRenderDelaySlider.setMinValue(3);
    chatRenderDelaySlider.setMaxValue(30);
    SliderElement maxLineSizeSlider = new SliderElement("Ширина строки", new ControlElement.IconData(Material.CYAN_BANNER), settings.maxLineSize.getValue());
    maxLineSizeSlider.addCallback(v -> settings.maxLineSize.setValue(v));
    maxLineSizeSlider.setMinValue(10);
    maxLineSizeSlider.setMaxValue(100);
    SliderElement renderOffsetSlider = new SliderElement("Высота над ником", new ControlElement.IconData(Material.SUNFLOWER), settings.renderOffset.getValue());
    renderOffsetSlider.addCallback(v -> settings.renderOffset.setValue(v));
    renderOffsetSlider.setMinValue(5);
    renderOffsetSlider.setMaxValue(100);
    BooleanElement enabledButton = new BooleanElement("Включить аддон", new ControlElement.IconData(Material.BARRIER), bl -> settings.enabled.setValue(bl), settings.enabled.getValue());
    list.add(chatRenderDelaySlider);
    list.add(maxLineSizeSlider);
    list.add(renderOffsetSlider);
    list.add(enabledButton);
  }

  public static class Settings {
    public List<Setting<?>> settingsList = new ArrayList<>();

    public NumberSetting chatRenderDelay = register(new NumberSetting("chatRenderDelay"));
    public NumberSetting maxLineSize = register(new NumberSetting("maxLineSize"));
    public NumberSetting renderOffset = register(new NumberSetting("renderOffset"));
    public BooleanSetting enabled = register(new BooleanSetting("enabled"));

    private <T extends Setting<?>> T register(T t) {
      this.settingsList.add(t);
      return t;
    }
  }
}
