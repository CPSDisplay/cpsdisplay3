package fr.dams4k.cpsdisplay.proxy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.lwjgl.input.Keyboard;

import fr.dams4k.cpsdisplay.References;
import fr.dams4k.cpsdisplay.commands.ConfigCommand;
import fr.dams4k.cpsdisplay.component.ModComponent;
import fr.dams4k.cpsdisplay.events.ModEvents;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	public static final KeyBinding CPS_OVERLAY_CONFIG = new KeyBinding("cpsdisplay.key.opengui", Keyboard.KEY_P, "cpsdisplay.category.cpsdisplay");
	
	public final List<ModComponent> components = new ArrayList<>();

	@Override
	public void preInit() {
		Path componentsFolder = ClientProxy.getComponentsFolder();
		File[] files = componentsFolder.toFile().listFiles();
		
		// No files -> no components
		if (files == null) return;

		Set<String> componentFiles = Stream.of(componentsFolder.toFile().listFiles())
			.filter(file -> !file.isDirectory())
			.filter(file -> file.getName().substring(file.getName().lastIndexOf(".") + 1).equalsIgnoreCase("cps"))
			.map(File::getName).collect(Collectors.toSet());

		// Load all components
		for (String filename : componentFiles) {
			components.add(new ModComponent(filename));
		}
	}

	@Override
	public void init() {
		ClientRegistry.registerKeyBinding(CPS_OVERLAY_CONFIG);
		MinecraftForge.EVENT_BUS.register(new ModEvents(this));
		ClientCommandHandler.instance.registerCommand(new ConfigCommand());
	}

	public static Path getConfigFolder() {
		if (Launch.minecraftHome == null) {
			Launch.minecraftHome = new File(".");
		}
		
		return Launch.minecraftHome.toPath().resolve("config").resolve(References.MOD_ID);
	}

	public static Path getComponentsFolder() {
		Path componentsFolderPath = ClientProxy.getConfigFolder().resolve("components");
		
		if (!componentsFolderPath.toFile().exists()) {
			try {
				Files.createDirectories(componentsFolderPath);
				ModComponent component = new ModComponent("0.cps");
				component.forceSave();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return componentsFolderPath;
	}
}
