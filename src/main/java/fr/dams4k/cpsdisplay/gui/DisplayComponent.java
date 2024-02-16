package fr.dams4k.cpsdisplay.gui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import fr.dams4k.cpsdisplay.References;
import fr.dams4k.cpsdisplay.config.Config;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DisplayComponent {
    public static final int HITBOX_COLOR = 0xffffffff; // aarrggbb

    protected static final Minecraft mc = Minecraft.getInstance();
    // Minecraft keys
    private static final KeyMapping KEY_ATTACK = mc.options.keyAttack;
    private static final KeyMapping KEY_USE = mc.options.keyUse;

    private static boolean attackIsPressed = false;
    private static boolean useIsPressed = false;

    private static List<Long> attackClicks = new ArrayList<Long>();
    private static List<Long> useClicks = new ArrayList<Long>();

    public static final IGuiOverlay OVERLAY = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        if (mc.screen != null) {
            if (mc.screen.getTitle() == ConfigScreen.TITLE) return;
        }
        DisplayComponent.render(guiGraphics);
    };

    @SuppressWarnings("null")
    public static void render(GuiGraphics guiGraphics) {
        if (!Config.showText) return;

        String text = getFormattedText();
        String[] lines = text.split("\n");
       

        //- Render background
        // guiGraphics.fill

        //- Render text
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            drawCenteredString(
                guiGraphics, mc.font,
                line, i,
                Integer.valueOf(Config.textColor, 16), Config.shadow
            );
        }
    }

    public static void drawCenteredString(GuiGraphics guiGraphics, @Nonnull Font font, @Nonnull String text, int line, int color, boolean shadow) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(Config.scale, Config.scale, 1f); // Apply scale

        float[] boundaries = getFBoundaries(font, text);
        // We divide by Config.scale because positions will be scaled by pose().scale( ... )
        guiGraphics.drawString(font, text, boundaries[0] / Config.scale, (boundaries[1] + boundaries[4] * line) / Config.scale, color, shadow);
        
        guiGraphics.pose().popPose();
    }


    @SubscribeEvent
    public static void onInput(InputEvent event) {
        if (KEY_ATTACK.isDown()) {
            if (!attackIsPressed) {
                attackIsPressed = true;
                attackClicks.add(System.currentTimeMillis());
            }
        } else {
            attackIsPressed = false;
        }

        if (KEY_USE.isDown()) {
            if (!useIsPressed) {
                useIsPressed = true;
                useClicks.add(System.currentTimeMillis());
            }
        } else {
            useIsPressed = false;
        }
    }

    public static Integer getAttackCPS() {
        long currentTime = System.currentTimeMillis();
        attackClicks.removeIf(e -> (e.longValue() + 1000l < currentTime));
        return attackClicks.size();
    }

    public static Integer getUseCPS() {
        long currentTime = System.currentTimeMillis();
        useClicks.removeIf(e -> (e.longValue() + 1000l < currentTime));
        return useClicks.size();
    }

    // [startX, startY, endX, endY, lineWidth]
    public static int[] getIBoundaries(Font font, @Nonnull String text) {
        float[] boundaries = getFBoundaries(font, text);
        return new int[]{
            (int) boundaries[0],
            (int) boundaries[1],
            (int) boundaries[2],
            (int) boundaries[3],
            (int) boundaries[4]
        };
    }


    // [startX, startY, endX, endY, lineWidth]
    public static float[] getFBoundaries(Font font, @Nonnull String text) {
        int textWidth = font.width(longuestLine(text));
        float x = Config.positionX - textWidth / 2;
        float y = Config.positionY;

        int nb_lines = text.split("\n").length;

        return new float[]{
            x,
            y,
            x + textWidth * Config.scale,
            y + font.lineHeight * nb_lines * Config.scale,
            mc.font.lineHeight * Config.scale
        };
    }

    @SuppressWarnings("null")
    public static @Nonnull String getFormattedText() {
        String text = Config.text;
        text = text.replace("{0}", getAttackCPS().toString());
        text = text.replace("{1}", getUseCPS().toString());
        text = text.replace("&", "§");
        return text;
    }

    @SuppressWarnings("null")
    public static @Nonnull String longuestLine(@Nonnull String text) {
        int maxLength = 0;
        int idx = 0;

        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length; i++) {
            int l = lines[i].length();
            if (l > maxLength) {
                maxLength = lines[i].length();
                idx = i;
            }
        }

        return lines[idx];
    }
}
