package fr.dams4k.cpsdisplay.gui;

import java.io.IOException;

import fr.dams4k.cpsdisplay.CPSDisplay;

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
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        boolean componentClicked = false;
        for (Component component : CPSDisplay.proxy.components) {
            if (component.clicked(mouseX, mouseY)) {
                componentClicked = true;

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
            int[] offset = new int[]{mouseX - componentSelected.getPosition()[0], mouseY - componentSelected.getPosition()[1]};
            mc.displayGuiScreen(new MoveComponentScreen(componentSelected, offset));
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        boolean openTimeExpired = System.currentTimeMillis() - selectTimestamp > 500;
        boolean mouseHasMoved = selectPosition.equals(new int[]{mouseX, mouseY});

        if (componentSelected != null && !openTimeExpired && !mouseHasMoved) {
            // mc.displayGuiScreen(new ComponentConfigScreen(componentSelected));
        }
        
        componentSelected = null;

        super.mouseReleased(mouseX, mouseY, state);
    }
}
