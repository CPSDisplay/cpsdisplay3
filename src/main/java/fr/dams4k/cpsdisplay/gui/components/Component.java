package fr.dams4k.cpsdisplay.gui.components;

import java.awt.Color;

import javax.annotation.Nonnull;

import fr.dams4k.cpsdisplay.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class Component {
    protected static final Minecraft mc = Minecraft.getInstance();

    public final ComponentConfig config;

    public Component(String path) {
        this.config = new ComponentConfig(path);
    }

    public void render(GuiGraphics guiGraphics) {
        if (!config.showText) return;

        String text = config.getText();
        String[] lines = text.split("\n");
       

        //- Render background
        // guiGraphics.fill

        //- Render text
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            drawCenteredString(
                guiGraphics, mc.font,
                line, i,
                config.getTextColor(), config.shadow
            );
        }
    }
    
    public void drawCenteredString(GuiGraphics guiGraphics, @Nonnull Font font, @Nonnull String text, int line, int color, boolean shadow) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(config.scale, config.scale, 1f); // Apply scale

        float[] boundaries = config.getFBoundaries(font, text);
        // We divide by Config.scale because positions will be scaled by pose().scale( ... )
        guiGraphics.drawString(font, text, boundaries[0] / config.scale, (boundaries[1] + boundaries[4] * line) / config.scale, color, shadow);
        
        guiGraphics.pose().popPose();
    }

    public int getTextColor() {
        if (!config.rainbow) {
            return Integer.valueOf(config.textColor, 16);
        } else {
            return Color.HSBtoRGB((float) (System.currentTimeMillis() * 0.01 % 100l)/ 100f, 0.8f, 0.8f);
        }
    }
}
