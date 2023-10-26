package fr.dams4k.cpsdisplay.gui;

import fr.dams4k.cpsdisplay.config.Config;
import net.minecraft.client.gui.GuiGraphics;

public class EditDisplayComponent extends DisplayComponent {
    public static boolean isOver(double x, double y) {
        int width = mc.font.width(Config.text);
        int height = mc.font.lineHeight;

        int minX = Config.positionX - width/2;
        int maxX = Config.positionX + width/2;

        int minY = Config.positionY;
        int maxY = Config.positionY + height;

        boolean correctX = x > minX && maxX > x;
        boolean correctY = y > minY && maxY > y;

        return correctX && correctY;
    }

    public static void render(GuiGraphics guiGraphics) {
        DisplayComponent.render(guiGraphics);
    }
}
