package fr.dams4k.cpsdisplay.gui.components;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class MComponent {
    protected static final Minecraft mc = Minecraft.getInstance();

    public final MComponentConfig config;

    public final int id;

    public MComponent(String path) {
        this.config = new MComponentConfig(path);
        

        // i know regex is better but i can't get it working on java.. i don't understand why and i don't want to lose more time
        System.out.println("CHECK HERE");
        String[] s = path.split("/");
        String filename = s[s.length-1];
        System.out.println(filename);
        String[] sf = filename.split("\\.");
        String sID = sf[0];
        System.out.println(sID);
        id = Integer.parseInt(sID);
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

    public float[] getFBoundaries() {
        return config.getFBoundaries(mc.font, config.getText()); //TODO: investigate why i don't directly use config.getText() in config.getFBoundaries()
    }

    public int[] getIBoundaries() {
        return config.getIBoundaries(mc.font, config.getText());
    }

    public boolean isOver(double x, double y) {
        float[] boundaries = getFBoundaries();

        boolean correctX = x > boundaries[0] && boundaries[2] > x;
        boolean correctY = y > boundaries[1] && boundaries[3] > y;

        return correctX && correctY;
    }
}
