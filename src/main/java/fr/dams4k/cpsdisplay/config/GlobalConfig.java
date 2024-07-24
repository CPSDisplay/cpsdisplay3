package fr.dams4k.cpsdisplay.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import fr.dams4k.cpsdisplay.References;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod.EventBusSubscriber(modid = References.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GlobalConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static boolean loaded = false; // We want to load this only one time

    private static ForgeConfigSpec.IntValue LAST_SELECTED_ID;

    private static int lastSelectedID;

    public static Path getConfigFolder() {
        Path path = FMLPaths.CONFIGDIR.get().resolve(References.MOD_ID);
		
        // Create folders if needed
		if (!path.toFile().exists()) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return path;
	}
   
    
    static {
        BUILDER.comment("Hope you like this mod, and if you have any issues, don't forget you can always join the support discord server: https://discord.gg/sscHRUZ3cA");
    
        LAST_SELECTED_ID = BUILDER.defineInRange("lastSelectedID", 0, 0, Integer.MAX_VALUE);

        SPEC = BUILDER.build();
    }

    
    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (loaded) return;
        loaded = true;

        lastSelectedID = LAST_SELECTED_ID.get();
    }
    public static void save() {
        LAST_SELECTED_ID.set(lastSelectedID);
    }

    public static int getLastSelectedID() {
        return lastSelectedID;
    }
    public static void setLastSelectedID(int value) {
        lastSelectedID = value;
        save();
    }
}
