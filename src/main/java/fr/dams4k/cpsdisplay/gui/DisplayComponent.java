package fr.dams4k.cpsdisplay.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

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
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = References.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DisplayComponent {
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
        // String text = Config.TEXT.get();
        // text = text.replace("{0}", getAttackCPS().toString());
        // text = text.replace("{1}", getUseCPS().toString());
        // text = text.replace("&", "§");

        // String[] lines = text.split("\n");

        // if (mc.screen != null) {
        //     if (mc.screen.getTitle() == ConfigScreen.TITLE) return;
        // }

        // for (int i = 0; i < lines.length; i++) {
        //     String line = lines[i];
        //     guiGraphics.drawCenteredString(mc.font, line, mc.getWindow().getGuiScaledWidth()/2, 200 + i * mc.font.lineHeight, 0xffffff);
        // }
    };

    public static void render(GuiGraphics guiGraphics) {
        if (!Config.showText) return;

        String text = Config.text;
        text = text.replace("{0}", getAttackCPS().toString());
        text = text.replace("{1}", getUseCPS().toString());
        text = text.replace("&", "§");

        String[] lines = text.split("\n");
       
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            drawCenteredString(
                guiGraphics, mc.font,
                line, Config.positionX, Config.positionY + i * mc.font.lineHeight,
                0xffffff, Config.shadow
            );
        }
    }

    public static void drawCenteredString(GuiGraphics guiGraphics, Font font, String text, int x, int y, int color, boolean shadow) {
        guiGraphics.drawString(font, text, x - font.width(text) / 2, y, color, shadow);
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
}
