package fr.dams4k.cpsdisplay.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class DebugScreen extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);


        int c = 0xffff0000;
        int margin = 1;

        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        int middle = scaledresolution.getScaledWidth() / 2;

        // Screen
        this.drawHorizontalLine(margin,         width-margin-1,   margin, c);
        this.drawHorizontalLine(margin,         width-margin-1,   height-1-margin, c);
        this.drawVerticalLine(margin,           margin,             height-margin-1, c);
        this.drawVerticalLine(width-margin-1,   margin,             height-margin-1, c);
        
        // Hotbar
        this.drawHorizontalLine(middle-91-margin,       middle + 91 + margin-1,                   scaledresolution.getScaledHeight()+margin-1, c);
        this.drawHorizontalLine(middle-91-margin,       middle + 91 + margin-1,                   scaledresolution.getScaledHeight()-22-margin, c);
        this.drawVerticalLine(middle-91-margin,         scaledresolution.getScaledHeight()+margin,  scaledresolution.getScaledHeight()-22-margin-1, c);
        this.drawVerticalLine(middle+91+margin-1,       scaledresolution.getScaledHeight()+margin,  scaledresolution.getScaledHeight()-22-margin-1, c);

        // Scoreboard
        

        // Bossbar


        // System.out.println(width); // get bigger when changing scale

        // mc.ingameGUI.
    }
}
