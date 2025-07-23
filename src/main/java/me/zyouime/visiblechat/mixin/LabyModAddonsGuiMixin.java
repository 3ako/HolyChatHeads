package me.zyouime.visiblechat.mixin;

import me.zyouime.visiblechat.VisibleChat;
import me.zyouime.visiblechat.settings.Setting;
import net.labymod.settings.LabyModAddonsGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LabyModAddonsGui.class)
public class LabyModAddonsGuiMixin {

    @Inject(method = "onClose", at = @At("RETURN"))
    private void onClose(CallbackInfo info) {
        VisibleChat.getInstance().settings.settingsList.forEach(Setting::save);
    }
}
