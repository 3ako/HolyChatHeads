package hw.zako.visiblechat;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import hw.zako.visiblechat.util.Settings;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.ActionResult;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VisibleChat implements ClientModInitializer {
    private static Cache<String, List<String>> messageCache;

    private static Settings settings;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(
                Settings.class,
                JanksonConfigSerializer::new
        );

        ConfigHolder<Settings> configHolder = AutoConfig.getConfigHolder(Settings.class);
        configHolder.registerSaveListener((manager, newSettings) -> {
            messageCache = CacheBuilder.newBuilder()
                    .expireAfterWrite(newSettings.getChatRenderDelay(), TimeUnit.SECONDS)
                    .build();
            return ActionResult.SUCCESS;
        });

        settings = configHolder.getConfig();

        messageCache = CacheBuilder.newBuilder()
                .expireAfterWrite(settings.getChatRenderDelay(), TimeUnit.SECONDS)
                .build();
    }

    public static Cache<String, List<String>> getMessageCache() {
        return messageCache;
    }

    public static Settings getSettings() {
        return settings;
    }
}