package fr.dams4k.cpsdisplay.component;

import java.io.File;
import java.nio.file.Path;

import fr.dams4k.cpsdisplay.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ModComponent {
    private final Minecraft mc = Minecraft.getMinecraft();

    private String filename;

    private final File configFile;
    private final Configuration config;

    private Property pText;
    private Property pPosition;

    private String text = "[0 | 0] CPS";
    private int[] position = {0, 0};

    public ModComponent(String filename) {
        this.filename = filename;

        Path configPath = ClientProxy.getComponentsFolder().resolve(this.filename);
        this.configFile = configPath.toFile();
        this.config = new Configuration(this.configFile);
        
        if (this.configFile.exists()) {
            this.load();
        }
    }

    public void load() {
        pText = config.get("appearance", "text", text);
        pPosition = config.get("appearance", "position", position);

        text = pText.getString();
        position = pPosition.getIntList();
    }

    public void save() {
        pText.set(text);
        pPosition.set(position);

        if (config.hasChanged()) {
            config.save();
        }
    }

    public void forceSave() {
        config.save();
    }

    public void draw() {
        mc.fontRendererObj.drawString(text, position[0], position[1], 0xffffff);
    }
}
