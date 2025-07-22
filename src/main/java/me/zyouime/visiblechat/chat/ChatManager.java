package me.zyouime.visiblechat.chat;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.zyouime.visiblechat.VisibleChat;
import me.zyouime.visiblechat.util.StringUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatManager {

    private static final Pattern pattern = Pattern.compile("^([ɢʟ])\\s\\|\\s(?:«[^»]+»\\s)?(\\S+).*?:\\s(.+)$");

    public static void handleChatMessage(String rawMessage) {
        if (!VisibleChat.getInstance().settings.enabled.getValue()) {
            return;
        }
        Minecraft client = Minecraft.getInstance();
        if (client.getConnection() == null)
            return;
        Matcher matcher = pattern.matcher(rawMessage);
        if (matcher.find()) {
            String name = matcher.group(2);
            if (name == null || name.length() > 32 || name.length() < 4)
                return;
            String cookedMessage = matcher.group(3);
            int maxLineSize = VisibleChat.getInstance().settings.maxLineSize.getValue();
            if (rawMessage.length() > maxLineSize) {
                VisibleChat.getMessageCache().put(name, StringUtils.chunkedMessage(cookedMessage, maxLineSize));
            } else {
                VisibleChat.getMessageCache().put(name, Collections.singletonList(cookedMessage));
            }
        }
    }

    private static List<String> getMessages(String name) {
        return VisibleChat.getMessageCache().getIfPresent(name);
    }

    public static void render(PlayerEntity entity, MatrixStack matrices, IRenderTypeBuffer vertexConsumerProvider, int light) {
        if (entity == null || matrices == null)
            return;
        entity.getDisplayName();
        String name = entity.getName().getString();
        List<String> messages = getMessages(name);
        if (messages == null || messages.isEmpty()) {
            return;
        }
        int j = (int)((Minecraft.getInstance()).gameSettings.getTextBackgroundOpacity(0.25F) * 255.0F) << 24;
        double height = (messages.size() * 0.3F) + (entity.getHeight() + (VisibleChat.getInstance().settings.renderOffset.getValue() / 100f)) + 0.499999988079071D;
        double distance = Minecraft.getInstance().getRenderManager().squareDistanceTo(entity);
        matrices.push();
        matrices.translate(0.0f, height, 0.0f);
        if (distance < 100.0 && entity.getWorldScoreboard().getObjectiveInDisplaySlot(2) != null) {
            matrices.translate(0.0D, 9.0F * 1.15F * 0.025F, 0.0D);
        }
        matrices.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
        matrices.scale(-0.025F, -0.025F, 0.025F);
        float i = 0.0F;
        FontRenderer textRenderer = Minecraft.getInstance().fontRenderer;
        Matrix4f matrix4f = matrices.getLast().getMatrix();
        for (String msg : messages) {
            textRenderer.renderString(msg, -textRenderer.getStringWidth(msg) / 2.0F, i, 0xFFFFFF, false, matrix4f, vertexConsumerProvider, false, j, light);
            i += 10.0F;
        }
        matrices.pop();
    }
}
