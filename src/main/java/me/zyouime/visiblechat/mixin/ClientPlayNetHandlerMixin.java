package me.zyouime.visiblechat.mixin;

import me.zyouime.visiblechat.chat.ChatManager;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.play.server.SChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientPlayNetHandler.class, priority = 500)
public class ClientPlayNetHandlerMixin {

    @Inject(method = "handleChat", at = @At("HEAD"))
    private void handleChat(SChatPacket p_147251_1_, CallbackInfo ci) {
        ChatManager.handleChatMessage(p_147251_1_.getChatComponent().getString());
    }
}
