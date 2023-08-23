package fr.dams4k.cpsdisplay.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class DebugScreen extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        int c = 0x45ff0000;
        int margin = 1;

        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        int middle = scaledresolution.getScaledWidth() / 2;

        // Screen
        this.drawHorizontalLine(margin,         width-margin-1,   margin, c);
        this.drawHorizontalLine(margin,         width-margin-1,   height-1-margin, c);
        this.drawVerticalLine(margin,           margin,             height-margin-1, c);
        this.drawVerticalLine(width-margin-1,   margin,             height-margin-1, c);
        

        // Screen splited
        this.drawHorizontalLine(margin, width-margin-1, height/2, c);
        this.drawVerticalLine(width/2, margin, height-margin, c);

        
        // Hotbar
        this.drawHorizontalLine(0, width, scaledresolution.getScaledHeight()-22, c);
        this.drawVerticalLine(middle-91,         scaledresolution.getScaledHeight(),  scaledresolution.getScaledHeight()-22-1, c);
        this.drawVerticalLine(middle+91-1,       scaledresolution.getScaledHeight(),  scaledresolution.getScaledHeight()-22-1, c);


        // Scoreboard
        // Scoreboard scoreboard = mc.theWorld.getScoreboard();
        // ScoreObjective scoreObjective = null;
        // EEEEEE

        // Bossbar
        int bossBarWidth = 182;
        int bossBarHeight = 5;

        int bossBarStartX = scaledresolution.getScaledWidth() / 2 - bossBarWidth / 2;
        int bossBarStartY = 12;
        int bossBarEndX = bossBarStartX+bossBarWidth;
        int bossBarEndY = bossBarStartY+bossBarHeight;

        this.drawHorizontalLine(bossBarStartX-margin, bossBarEndX+margin-1, bossBarStartY-margin, c);
        this.drawHorizontalLine(bossBarStartX-margin, bossBarEndX+margin-1, bossBarEndY+margin-1, c);
        this.drawVerticalLine(bossBarStartX-margin, bossBarStartY-margin, bossBarEndY+margin-1, c);
        this.drawVerticalLine(bossBarEndX+margin-1, bossBarStartY-margin, bossBarEndY+margin-1, c);


        // Cross
        this.drawHorizontalLine(width/2-7-margin, width/2+7+margin, height/2-7-margin, c);
        this.drawHorizontalLine(width/2-7-margin, width/2+7+margin, height/2+7+margin, c);
        this.drawVerticalLine(width/2-7-margin, height/2-7-margin, height/2+7+margin, c);
        this.drawVerticalLine(width/2+7+margin, height/2-7-margin, height/2+7+margin, c);

        // Quads
        this.drawHorizontalLine(0, width, height/4, c);
        this.drawVerticalLine(width/4, 0, height-32+3, c);
        this.drawVerticalLine(3*width/4, 0, height-32+3, c);

        this.drawHorizontalLine(0, width, height-32+3, c);
        this.drawHorizontalLine(0, width, height-32+7, c);
    }
}
