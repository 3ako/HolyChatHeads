package hw.zako.visiblechat.mixins;

import hw.zako.visiblechat.chat.ChatManager;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(at = @At("HEAD"), method = "onGameMessage")
    public void onGameMessage(GameMessageS2CPacket packet, CallbackInfo ci) {
       ChatManager.handleChatMessage(packet.getMessage().getString());
    }
}
