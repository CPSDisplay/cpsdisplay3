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

    private String text = "[0 | 0] CPS";

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

        text = pText.getString();
    }

    public void save() {
        pText.set(text);

        if (config.hasChanged()) {
            config.save();
        }
    }

    public void forceSave() {
        System.out.println("FORCE SAVE");
        config.save();
    }

    public void draw() {
        mc.fontRendererObj.drawString(text, 0, 0, 0xffffff);
    }
}
