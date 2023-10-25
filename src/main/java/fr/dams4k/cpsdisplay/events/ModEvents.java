package fr.dams4k.cpsdisplay.events;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import fr.dams4k.cpsdisplay.References;
import fr.dams4k.cpsdisplay.gui.ConfigScreen;
import fr.dams4k.cpsdisplay.gui.DisplayComponent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {
    public static final Minecraft mc = Minecraft.getInstance();

    // Mod keys
    public static final KeyMapping CPS_OVERLAY_CONFIG = new KeyMapping(
        "cpsdisplay.key.opengui",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_P,
        "cpsdisplay.category.cpsdisplay");

    @Mod.EventBusSubscriber(modid = References.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {
        public static final DisplayComponent overlay = new DisplayComponent();

        @SubscribeEvent
        public static void onInput(InputEvent.Key event) {
            if (CPS_OVERLAY_CONFIG.consumeClick()) {
                mc.setScreen(new ConfigScreen());
            }
        }
    }
    
    @Mod.EventBusSubscriber(modid = References.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(CPS_OVERLAY_CONFIG);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("cpsdisplay", DisplayComponent.OVERLAY);
        }
    }
}
