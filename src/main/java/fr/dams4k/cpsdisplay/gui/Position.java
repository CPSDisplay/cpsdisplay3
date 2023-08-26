package fr.dams4k.cpsdisplay.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Position {
    public enum Quad {
        TOP_LEFT_LEFT_UP,
        TOP_LEFT_RIGHT_UP,
        TOP_LEFT_LEFT_DOWN,
        TOP_LEFT_RIGHT_DOWN,
        HOTBAR_LEFT_LEFT,
        HOTBAR_LEFT_RIGHT,
        HOTBAR_RIGHT_LEFT,
        HOTBAR_RIGHT_RIGHT,
        HOTBAR_MIDDLE;
    }
    public enum Anchor {
        RELATIVE,
        CENTER,
        V_CENTER,
        H_CENTER,
        LEFT,
        TOP,
        RIGHT,
        BOTTOM,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_RIGHT,
        BOTTOM_LEFT;
    }

    private Quad quad;
    private Anchor anchor;
    private int[] position = new int[2];

    private int snapStrength = 6;

    public Position(Quad quad, Anchor anchor, int[] position) {
        this.quad = quad;
        this.anchor = anchor;
        this.position = position;
    }

    public Position(int[] globalPosition) {
        this(globalPosition[0], globalPosition[1]);
    }

    public Position(int x, int y) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        position[0] = x;
        position[1] = y;

        int gameWidth = scaledResolution.getScaledWidth();
        int gameHeight = scaledResolution.getScaledHeight();

        int hotBarWidth = 91*2;
        int hotBarHeight = 22;
        int hotBarMiddlePosition = gameHeight - hotBarHeight/2;

        if (y > gameHeight - hotBarHeight) {
            // HotBar section
            if (y > hotBarMiddlePosition-snapStrength && y < hotBarMiddlePosition+snapStrength) {
                // position[1] = hotBarMiddlePosition;
                this.anchor = Anchor.V_CENTER;
            }

            // Check what quad
            if (x < (gameWidth-hotBarWidth)/2) {
                // Left
                if (x < (gameWidth-hotBarWidth)/4) {
                    this.quad = Quad.HOTBAR_LEFT_LEFT;
                } else {
                    this.quad = Quad.HOTBAR_LEFT_RIGHT;
                }
            } else if (x > (gameWidth+hotBarWidth)/2) {
                // Right
                int startX = (gameWidth+hotBarWidth)/2 + (gameWidth-(gameWidth+hotBarWidth)/2)/2;
                if (x > startX) {
                    this.quad = Quad.HOTBAR_RIGHT_RIGHT;
                    this.position[0] = x - startX;
                } else {
                    this.quad = Quad.HOTBAR_RIGHT_LEFT;
                }
            } else {
                this.quad = Quad.HOTBAR_MIDDLE;
            }
        } else {
            // System.out.println("Not in hotbar section");
        }
    }

    public int[] getGlobalPosition() {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int gameWidth = scaledResolution.getScaledWidth();
        int gameHeight = scaledResolution.getScaledHeight();

        int hotBarWidth = 91*2;
        int hotBarHeight = 22;
        int hotBarMiddlePosition = gameHeight - hotBarHeight/2;

        if (this.quad == Quad.HOTBAR_LEFT_LEFT) {
            if (this.anchor == Anchor.V_CENTER) {
                return new int[]{this.position[0], hotBarMiddlePosition};
            }
        } else if (this.quad == Quad.HOTBAR_RIGHT_RIGHT) {
            if (this.anchor == Anchor.V_CENTER) {
                int startX = (gameWidth+hotBarWidth)/2 + (gameWidth-(gameWidth+hotBarWidth)/2)/2;
                return new int[]{startX + position[0], hotBarMiddlePosition};
            }
        }

        return position;
    }
}
