package fr.dams4k.cpsdisplay.events;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import org.lwjgl.glfw.GLFW;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.InputConstants;

import fr.dams4k.cpsdisplay.References;
import fr.dams4k.cpsdisplay.VersionChecker;
import fr.dams4k.cpsdisplay.gui.ConfigScreen;
import fr.dams4k.cpsdisplay.gui.DisplayComponent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
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
        public static boolean used = false;

        @SubscribeEvent
        public static void onInput(InputEvent.Key event) {
            if (CPS_OVERLAY_CONFIG.consumeClick()) {
                mc.setScreen(new ConfigScreen());
            }
        }

        @SubscribeEvent
        public static void onClientJoinWorld(EntityJoinLevelEvent event) {
            if (used) return;
            if (!(event.getEntity() instanceof Player)) return;

            boolean updateAvailable = false;
            try {
                URL githubLatestRelease = new URL(References.MOD_GITHUB_LASTEST_RELEASE);
                Scanner scanner = new Scanner(githubLatestRelease.openStream());
                String response = scanner.useDelimiter("\\Z").next();

                JsonParser parser = new JsonParser();
                JsonObject jsonObject = (JsonObject) parser.parse(response);

                String latestTag = jsonObject.get("tag_name").getAsString();
                String version = latestTag;
                int releaseVersion = -1;

                if (latestTag.indexOf("-") != -1) {
                    version = latestTag.split("-")[0];
                    releaseVersion = Integer.parseInt(latestTag.split("-")[1]);
                }

                VersionChecker versionChecker = new VersionChecker(References.MOD_VERSION);
                int comparison = versionChecker.compareTo(version);
                if (comparison == VersionChecker.UPPER) {
                    updateAvailable = true;
                } else if (comparison == VersionChecker.SAME && releaseVersion != -1 && References.RELEASE_TYPE.getVersion() < releaseVersion) {
                    updateAvailable = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!updateAvailable) return;

            Player player = (Player) event.getEntity();
            
            MutableComponent message = Component.translatable("cpsdisplay.version.modName");
            message.append(Component.translatable("cpsdisplay.version.description"));
            
            Style urlStyle = Style.EMPTY.withClickEvent(
                new ClickEvent(ClickEvent.Action.OPEN_URL, References.CURSEFORGE_URL));
            Component url = Component.translatable("cpsdisplay.version.url").setStyle(urlStyle);
            message.append(url);

            player.sendSystemMessage(message);
            used = true;
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
