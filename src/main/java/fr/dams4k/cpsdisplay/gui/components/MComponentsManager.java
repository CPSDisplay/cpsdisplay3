package fr.dams4k.cpsdisplay.gui.components;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.dams4k.cpsdisplay.References;
import fr.dams4k.cpsdisplay.config.GlobalConfig;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MComponentsManager {
	public static HashMap<Integer, MComponent> components = new HashMap<>();

	private static int biggestID = -1;

    public static void loadComponentConfigs() {
        Path componentsFolder = MComponentsManager.getComponentsFolder();
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
			
			MComponent c = new MComponent(filepath);
			components.put(c.id, c);
			// If this id is the biggest, then we need to register it if we want to create a new component
			if (c.id > biggestID) {
				biggestID = c.id;
			}
		}
    }

	public static MComponent getLastSelected() {
		return components.getOrDefault(GlobalConfig.getLastSelectedID(), null);
	}
	public static MComponent getLastSelectedOrFirst() {
		return components.getOrDefault(GlobalConfig.getLastSelectedID(), getFirstComponent());
	}

    public static Path getComponentsFolder() {
		Path path = GlobalConfig.getConfigFolder().resolve("components");
		if (!path.toFile().exists()) {
			try {
				Files.createDirectories(path);
				// First launch of the mod, we create the first component
				//TODO: when we call createComponent and components folder isn't created, 2 components will be created
				MComponent firstComponent = new MComponent(path.resolve("0.toml").toString());
				firstComponent.config.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return path;
	}

	public static MComponent createComponent() {
		Path path = getComponentsFolder();
		biggestID++;
		MComponent newComponent = new MComponent(path.resolve(biggestID + ".toml").toString());
		newComponent.config.save();
		components.put(biggestID, newComponent);
		return newComponent;
	}

	public static MComponent getFirstComponent() {
		if (components.size() == 0) return null;
		return components.values().iterator().next();
	}
}
