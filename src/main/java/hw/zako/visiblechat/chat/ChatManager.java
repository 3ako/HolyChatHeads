package hw.zako.visiblechat.chat;

import hw.zako.visiblechat.VisibleChat;
import hw.zako.visiblechat.util.StringUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatManager {
    private static final Pattern pattern = Pattern.compile("^([ɢʟ])\\s\\|\\s(?:«[^»]+»\\s)?(\\S+).*?:\\s(.+)$");

    public static void handleChatMessage(String rawMessage) {
        if (!VisibleChat.getSettings().isEnabled()) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getNetworkHandler() == null)
            return;
        Matcher matcher = pattern.matcher(rawMessage);
        if (matcher.find()) {
            String name = matcher.group(2);
            if (name == null || name.length() > 32 || name.length() < 4)
                return;
            String cookedMessage = matcher.group(3);
            int maxLineSize = VisibleChat.getSettings().getMaxLineSize();
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

    public static void render(PlayerEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light) {
        if (entity == null || matrices == null)
            return;
        if (entity.getDisplayName() == null)
            return;
        String name = entity.getName().getString();
        List<String> messages = getMessages(name);
        if (messages == null || messages.isEmpty()) {

            return;
        }
        int j = (int)(MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F) * 255.0F) << 24;
        double height = (messages.size() * 0.3F) + (entity.getHeight() + (VisibleChat.getSettings().getRenderOffset() / 100f)) + 0.499999988079071D;
        double distance = MinecraftClient.getInstance().getEntityRenderDispatcher().getSquaredDistanceToCamera(entity);
        matrices.push();
        matrices.translate(0.0f, height, 0.0f);
        if (distance < 100.0 && entity.getScoreboard().getObjectiveForSlot(ScoreboardDisplaySlot.BELOW_NAME) != null) {
            matrices.translate(0.0D, 9.0F * 1.15F * 0.025F, 0.0D);
        }
        matrices.multiply(MinecraftClient.getInstance().getEntityRenderDispatcher().getRotation());
        matrices.scale(0.025F, -0.025F, 0.025F);
        float i = 0.0F;
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        for (String msg : messages) {
            textRenderer.draw(msg, -textRenderer.getWidth(msg) / 2.0F, i, 553648127, false, matrix4f, vertexConsumerProvider, TextRenderer.TextLayerType.SEE_THROUGH, j, light);
            textRenderer.draw(msg, -textRenderer.getWidth(msg) / 2.0F, i, 0xffffff, false, matrix4f, vertexConsumerProvider, TextRenderer.TextLayerType.NORMAL, 0, light);
            i += 10.0F;
        }
        matrices.pop();
    }
}