package hw.zako.visiblechat.chat;

import hw.zako.visiblechat.VisibleChat;
import hw.zako.visiblechat.util.StringUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.regex.Pattern;

public class ChatManager {
    private static final Pattern pattern = Pattern.compile("^([ɢʟ])\\s\\|\\s(?:«[^»]+»\\s)?(\\S+).*?:\\s(.+)$");

    public static void handleChatMessage(String rawMessage) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getNetworkHandler() == null) return;

        var matcher = pattern.matcher(rawMessage);
        if (matcher.find()) {
            String name = matcher.group(2);

            if (name == null || name.length() > 32 || name.length() < 4) return;
            String cookedMessage = matcher.group(3);

            int maxLineSize = VisibleChat.getSettings().getMaxLineSize();
            if (rawMessage.length() > maxLineSize) {
                VisibleChat.getMessageCache().put(name, StringUtils.chunkedMessage(cookedMessage, maxLineSize));
            } else {
                VisibleChat.getMessageCache().put(name, List.of(cookedMessage));
            }
        }
    }

    private static List<String> getMessages(String name) {
        return VisibleChat.getMessageCache().getIfPresent(name);
    }

    public static void render(
    EntityRenderDispatcher renderDispatcher,
    PlayerEntityRenderState entity,
    MatrixStack matrices,
    VertexConsumerProvider vertexConsumerProvider,
    int light
    ) {
        if (entity == null || matrices == null) return;

        if (entity.displayName == null) return;

        String name = entity.name;
        List<String> messages = getMessages(name);
        if (messages == null || messages.isEmpty()) return;

        int j = (int)(MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F) * 255.0F) << 24;

        Vec3d vec3d = entity.nameLabelPos;
        double height = (messages.size() * (0.3f)) + vec3d.y + 0.7f;

        matrices.push();
        matrices.translate(vec3d.x, height, vec3d.z);
        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();

        matrices.multiply(camera.getRotation());

        matrices.scale(0.025F, -0.025F, 0.025F);

        float i = 0f;
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        for (String msg : messages) {
            textRenderer.draw(
                msg,
                -textRenderer.getWidth(msg) / 2f,
                i,
                    0xffffff,
                false,
                matrices.peek().getPositionMatrix(),
                vertexConsumerProvider,
                TextRenderer.TextLayerType.NORMAL,
                j,
                light
            );
            i += (float)(10);
        }

        matrices.pop();
    }
}