package fr.dams4k.cpsdisplay.gui;

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
            int[] boundaries = DisplayComponent.getIBoundaries(mc.font, text);
            guiGraphics.hLine(boundaries[0], boundaries[2], boundaries[1], HITBOX_COLOR);
            guiGraphics.hLine(boundaries[0], boundaries[2], boundaries[3], HITBOX_COLOR);
            guiGraphics.vLine(boundaries[0], boundaries[1], boundaries[3], HITBOX_COLOR);
            guiGraphics.vLine(boundaries[2], boundaries[1], boundaries[3], HITBOX_COLOR);
        }
    }
}
