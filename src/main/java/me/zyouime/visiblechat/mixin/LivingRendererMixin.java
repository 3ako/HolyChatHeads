package me.zyouime.visiblechat.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.zyouime.visiblechat.VisibleChat;
import me.zyouime.visiblechat.chat.ChatManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingRenderer.class, priority = 200)
public abstract class LivingRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements IEntityRenderer<T, M> {

    protected LivingRendererMixin(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;I)V", at = @At("HEAD"))
    private void render(T p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_, CallbackInfo ci) {
        if (this.renderManager.squareDistanceTo(p_225623_1_) > 4096) {
            return;
        }
        if (p_225623_1_ instanceof PlayerEntity && VisibleChat.getInstance().settings.enabled.getValue() && !p_225623_1_.isInvisible()) {
            ChatManager.render((PlayerEntity) p_225623_1_, p_225623_4_, p_225623_5_, p_225623_6_);
        }
    }

}
