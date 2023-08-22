package fr.dams4k.cpsdisplay.gui;

import fr.dams4k.cpsdisplay.CPSDisplay;
import fr.dams4k.cpsdisplay.component.ModComponent;

public class ModConfigGui extends ModScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        for (ModComponent component : CPSDisplay.proxy.components) {
            component.draw();
        }
    }
}
