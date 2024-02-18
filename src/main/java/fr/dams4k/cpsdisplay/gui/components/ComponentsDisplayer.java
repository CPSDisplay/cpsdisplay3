package fr.dams4k.cpsdisplay.gui.components;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import fr.dams4k.cpsdisplay.References;
import fr.dams4k.cpsdisplay.config.Config;
import fr.dams4k.cpsdisplay.gui.ConfigScreen;
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
public class ComponentsDisplayer {
    public static final int HITBOX_COLOR = 0xffffffff; // aarrggbb

    protected static final Minecraft mc = Minecraft.getInstance();

    private final Component component;
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
        renderComponents(guiGraphics);
    };

    public ComponentsDisplayer(Component component) {
        this.component = component;
    }

    public static void renderComponents(GuiGraphics guiGraphics) {
        for (Component component : ComponentsManager.components) {
            System.out.println("------------- DISPLAY");
            component.render(guiGraphics);
        }
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

    //TODO: move this in a special class registering all the inputs
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
    public int[] getIBoundaries(Font font, @Nonnull String text) {
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
    public float[] getFBoundaries(Font font, @Nonnull String text) {
        int textWidth = font.width(longuestLine(text));
        float x = component.config.positionX - textWidth * Config.scale / 2;
        float y = component.config.positionY;

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
    public @Nonnull String getFormattedText() {
        String text = component.config.text;
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
