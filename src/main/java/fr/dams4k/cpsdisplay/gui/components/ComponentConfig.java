package fr.dams4k.cpsdisplay.gui.components;

import java.awt.Color;
import java.io.File;

import javax.annotation.Nonnull;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import net.minecraft.client.gui.Font;


public class ComponentConfig {
    private final CommentedFileConfig config;

    public boolean showText = true;
    public String text = "[{0} | {1}] CPS";
    public boolean shadow = true;

    public int positionX = 50;
    public int positionY = 50;

    public float scale = 1f;

    public String textColor = "ffffff";
    public boolean rainbow = false;

    public boolean loaded = false;


    public ComponentConfig(String path) {
        File file = new File(path);
        this.config = CommentedFileConfig.builder(file).build();
        
        if (file.exists()) {
            System.out.println("ALREADY EXISTS");
            this.config.load();
        } else {
            save();
        }
         
        load();
    }

    public void load() {
        showText = config.getOrElse("showText", showText);
        text = config.getOrElse("text", text);
        shadow = config.getOrElse("shadow", showText);

        positionX = config.getIntOrElse("positionX", positionX);
        positionY = config.getIntOrElse("positionY", positionY);

        scale = (config.getOrElse("scale", (double) scale).floatValue());

        textColor = config.getOrElse("textColor", textColor);
        rainbow = config.getOrElse("rainbow", rainbow);
    }

    public void save() {
        config.set("showText", showText);
        config.set("text", text);
        config.set("shadow", shadow);

        config.set("positionX", positionX);
        config.set("positionY", positionY);

        config.set("scale", (double) scale);

        config.set("textColor", textColor);
        config.set("rainbow", rainbow);

        config.save();
    }



    public String getRawText() {
        return this.text;
    }

    public String getText() {
        return this.text
            .replace("{0}", ComponentsDisplayer.getAttackCPS().toString())
            .replace("{1}", ComponentsDisplayer.getUseCPS().toString())
            .replace("&", "§");
    }

    public int getTextColor() {
        if (!rainbow) {
            return Integer.valueOf(textColor, 16);
        } else {
            return Color.HSBtoRGB((float) (System.currentTimeMillis() * 0.01 % 100l)/ 100f, 0.8f, 0.8f);
        }
    }

    public static @Nonnull String longuestLine(@Nonnull String text) {
        int maxLength = 0;
        int idx = 0;

        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length; i++) {
            int l = lines[i].length();
            if (l > maxLength) {
                maxLength = lines[i].length();
                idx = i;
            }
        }

        return lines[idx];
    }

    // [startX, startY, endX, endY, lineWidth]
    public int[] getIBoundaries(Font font, @Nonnull String text) {
        float[] boundaries = getFBoundaries(font, text);
        return new int[]{
            (int) boundaries[0],
            (int) boundaries[1],
            (int) boundaries[2],
            (int) boundaries[3],
            (int) boundaries[4]
        };
    }


    // [startX, startY, endX, endY, lineWidth]
    public float[] getFBoundaries(Font font, @Nonnull String text) {
        int textWidth = font.width(longuestLine(text));
        float x = positionX - textWidth * scale / 2;
        float y = positionY;

        int nb_lines = text.split("\n").length;

        return new float[]{
            x,
            y,
            x + textWidth * scale,
            y + font.lineHeight * nb_lines * scale,
            font.lineHeight * scale
        };
    }
}
