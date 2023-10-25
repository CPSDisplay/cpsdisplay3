package fr.dams4k.cpsdisplay.gui;

import java.util.ArrayList;
import java.util.List;

import fr.dams4k.cpsdisplay.References;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GuiComponent {
    private static final Minecraft mc = Minecraft.getInstance();
    // Minecraft keys
    private static final KeyMapping KEY_ATTACK = mc.options.keyAttack;
    private static final KeyMapping KEY_USE = mc.options.keyUse;

    private static boolean attackIsPressed = false;
    private static boolean useIsPressed = false;

    private static List<Long> attackClicks = new ArrayList<Long>();
    private static List<Long> useClicks = new ArrayList<Long>();

    public static final IGuiOverlay OVERLAY = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        guiGraphics.drawCenteredString(mc.font, "[" + getAttackCPS() + " | " + getUseCPS() + "] CPS", 200, 200, 0xffffff);
    };

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

    public static int getAttackCPS() {
        long currentTime = System.currentTimeMillis();
        attackClicks.removeIf(e -> (e.longValue() + 1000l < currentTime));
        return attackClicks.size();
    }

    public static int getUseCPS() {
        long currentTime = System.currentTimeMillis();
        useClicks.removeIf(e -> (e.longValue() + 1000l < currentTime));
        return useClicks.size();
    }
}
