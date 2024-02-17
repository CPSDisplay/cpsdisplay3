package fr.dams4k.cpsdisplay.gui;

import javax.annotation.Nonnull;

import net.minecraft.client.gui.GuiGraphics;

public class EditDisplayComponent extends DisplayComponent {
    public static boolean isOver(double x, double y) {
        float[] boundaries = DisplayComponent.getFBoundaries(mc.font, DisplayComponent.getFormattedText());

        boolean correctX = x > boundaries[0] && boundaries[2] > x;
        boolean correctY = y > boundaries[1] && boundaries[3] > y;

        return correctX && correctY;
    }

    public static void render(GuiGraphics guiGraphics) {
        DisplayComponent.render(guiGraphics);

        String text = getFormattedText();
        
        if (mc.getEntityRenderDispatcher().shouldRenderHitBoxes()) {
            drawBoundaries(guiGraphics, text, HITBOX_COLOR);
        }
    }

    public static void drawBoundaries(GuiGraphics guiGraphics, @Nonnull String text, int color) {
        int[] boundaries = DisplayComponent.getIBoundaries(mc.font, text);
        guiGraphics.hLine(boundaries[0], boundaries[2], boundaries[1], color);
        guiGraphics.hLine(boundaries[0], boundaries[2], boundaries[3], color);
        guiGraphics.vLine(boundaries[0], boundaries[1], boundaries[3], color);
        guiGraphics.vLine(boundaries[2], boundaries[1], boundaries[3], color);
    }
}
