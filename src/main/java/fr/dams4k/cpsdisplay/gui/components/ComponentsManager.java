package fr.dams4k.cpsdisplay.gui.components;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.dams4k.cpsdisplay.References;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod.EventBusSubscriber(modid = References.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ComponentsManager {
	public static ArrayList<Component> components = new ArrayList<>();

    private static ArrayList<Component> displays = new ArrayList<>();

    public static void loadComponentConfigs() {
        Path componentsFolder = ComponentsManager.getComponentsFolder();
		File[] files = componentsFolder.toFile().listFiles();

		if (files == null) return;

		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i]);
		}

		Set<String> componentFiles = Stream.of(files)
				.filter(file -> !file.isDirectory())
				.filter(file -> file.getName().substring(file.getName().lastIndexOf(".") + 1).equalsIgnoreCase("toml"))
				.map(File::getName).collect(Collectors.toSet());
		
		for (String filename : componentFiles) {
			String filepath = getComponentsFolder().resolve(filename).toString();
			components.add(new Component(filepath));
		}
    }

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

    public static Path getComponentsFolder() {
		Path path = getConfigFolder().resolve("components");
		if (!path.toFile().exists()) {
			try {
				Files.createDirectories(path);
				// First launch of the mod, we create the first component
				Component firstComponent = new Component(path.resolve("0.toml").toString());
				firstComponent.config.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return path;
	}


	@SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
		System.out.println("On load");
		System.out.println(event.getConfig().getFileName());
	}

    public static void registerAllOverlays(RegisterGuiOverlaysEvent event) {
        for (int i = 0; i < displays.size(); i++) {
            event.registerAboveAll("cpsdisplay_" + i, new ComponentsDisplayer(displays.get(i)).OVERLAY);
        }
    }
}
