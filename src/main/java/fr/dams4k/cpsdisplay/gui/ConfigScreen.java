package fr.dams4k.cpsdisplay.gui;

import java.io.IOException;

import fr.dams4k.cpsdisplay.CPSDisplay;
import net.minecraft.client.gui.ScaledResolution;

public class ConfigScreen extends ModScreen {
    private Component componentSelected = null;
    private int[] selectPosition = new int[2];
    private long selectTimestamp = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        for (Component component : CPSDisplay.proxy.components) {
            component.draw();
        }


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

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        boolean componentClicked = false;
        for (Component component : CPSDisplay.proxy.components) {
            if (component.clicked(mouseX, mouseY)) {
                componentClicked = true;
                System.out.println("clicked");
                componentSelected = component;
                selectPosition = new int[]{mouseX, mouseY};
                selectTimestamp = System.currentTimeMillis();
            }
        }
        
        if (!componentClicked) {
            componentSelected = null;
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        
        if (componentSelected != null && selectPosition != new int[]{mouseX, mouseY}) {
            // int[] offset = new int[]{mouseX - componentSelected.getPosition()[0], mouseY - componentSelected.getPosition()[1]};
            int[] componentPosition = componentSelected.getPosition();
            int[] offset = new int[]{mouseX - componentPosition[0], mouseY - componentPosition[1]};

            mc.displayGuiScreen(new MoveComponentScreen(componentSelected, offset));
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        boolean openTimeExpired = System.currentTimeMillis() - selectTimestamp > 500;
        boolean mouseHasMoved = selectPosition.equals(new int[]{mouseX, mouseY});

        if (componentSelected != null && !openTimeExpired && !mouseHasMoved) {
            mc.displayGuiScreen(new ComponentConfigScreen(componentSelected));
        }
        
        componentSelected = null;

        super.mouseReleased(mouseX, mouseY, state);
    }
}
