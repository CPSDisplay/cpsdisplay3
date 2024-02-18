package fr.dams4k.cpsdisplay.gui.components;

import javax.annotation.Nonnull;

import net.minecraft.client.gui.GuiGraphics;

public class EditComponentDisplayer extends ComponentsDisplayer {
    public EditComponentDisplayer(Component config) {
        super(config);
    }

    public boolean isOver(double x, double y) {
        float[] boundaries = this.getFBoundaries(mc.font, getFormattedText());

        boolean correctX = x > boundaries[0] && boundaries[2] > x;
        boolean correctY = y > boundaries[1] && boundaries[3] > y;

        return correctX && correctY;
    }

    public static void renderComponents(GuiGraphics guiGraphics) {
        // ComponentsDisplayer.renderComponents(guiGraphics);

        // String text = getFormattedText();
        
        // if (mc.getEntityRenderDispatcher().shouldRenderHitBoxes()) {
        //     drawBoundaries(guiGraphics, text, HITBOX_COLOR);
        // }
    }

    public void drawBoundaries(GuiGraphics guiGraphics, @Nonnull String text, int color) {
        int[] boundaries = this.getIBoundaries(mc.font, text);
        guiGraphics.hLine(boundaries[0], boundaries[2], boundaries[1], color);
        guiGraphics.hLine(boundaries[0], boundaries[2], boundaries[3], color);
        guiGraphics.vLine(boundaries[0], boundaries[1], boundaries[3], color);
        guiGraphics.vLine(boundaries[2], boundaries[1], boundaries[3], color);
    }
}
