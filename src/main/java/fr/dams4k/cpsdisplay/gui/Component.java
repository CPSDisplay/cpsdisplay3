package fr.dams4k.cpsdisplay.gui;

import java.io.File;
import java.nio.file.Path;

import fr.dams4k.cpsdisplay.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Component {
    protected final Minecraft mc = Minecraft.getMinecraft();

    private String filename;

    private final File configFile;
    private final Configuration config;

    private Property pText;
    private Property pPosition;

    private String text = "[0 | 0] CPS";
    private int[] position = {0, 0};

    public Position pos = new Position(0, 0);

    public Component(String filename) {
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

    // Only when ConfigScreen is open
    public boolean clicked(int mouseX, int mouseY) {
        int width = this.mc.fontRendererObj.getStringWidth(this.text);
        int height = this.mc.fontRendererObj.FONT_HEIGHT;

        int textHeight = mc.fontRendererObj.FONT_HEIGHT;
        int textWidth = mc.fontRendererObj.getStringWidth(text);

        int[] position = pos.getGlobalPosition();
        return position[0] - textWidth/2 < mouseX && mouseX < position[0] - textWidth/2 + width && position[1] - textHeight/2 < mouseY && mouseY < position[1] - textHeight/2 + height;
    }
    
    public void draw() {
        int textHeight = mc.fontRendererObj.FONT_HEIGHT;
        int textWidth = mc.fontRendererObj.getStringWidth(text);

        // draw centered
        int[] position = pos.getGlobalPosition();
        mc.fontRendererObj.drawString(text, position[0] - textWidth/2, position[1] - textHeight/2, 0xffffff);
    }

    public String getFilename() {
        return filename;
    }

    public void setText(String text) {
        this.text = text;
        save();
    }

    public String getText() {
        return text;
    }

    public void setPosition(int[] position) {
        this.position = position;
        // TODO: add a "auto save" parameter to not spam saving the config when moving
        save();
    }

    public int[] getPosition() {
        return position;
    }
}
