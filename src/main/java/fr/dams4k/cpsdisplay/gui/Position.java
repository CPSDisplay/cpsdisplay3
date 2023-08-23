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
        HOTBAR_RIGHT_RIGHT;
    }
    public enum Anchor {
        CENTER;
    }

    private Quad quad;
    private Anchor anchor;
    private int[] position = new int[2];

    public Position(Quad quad, Anchor anchor, int[] position) {
        this.quad = quad;
        this.anchor = anchor;
        this.position = position;
    }

    public Position(int[] globalPosition) {
        int x = globalPosition[0];
        int y = globalPosition[1];
        
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int gameWidth = scaledResolution.getScaledWidth();
        int gameHeight = scaledResolution.getScaledHeight();

        int hotBarWidth = 91;
        int hotBarHeight = 22;
        if (y > gameHeight - hotBarHeight) {
            // HotBar section
            System.out.println("In hotbar section");
        } else {
            System.out.println("Not in hotbar section");
        }
    }

    public int[] getGlobalPosition() {
        if (quad.equals(Quad.HOTBAR_LEFT_RIGHT)) {
            // if (anchor.equals(Anchor.CENTER))

        }

        return new int[2];
    }
}
