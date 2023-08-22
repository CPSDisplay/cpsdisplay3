package fr.dams4k.cpsdisplay.gui;

import net.minecraft.client.gui.GuiScreen;

public class MoveComponentScreen extends GuiScreen {
    private Component component;
    private int[] offset = new int[2];

    public MoveComponentScreen(Component component, int[] offset) {
        this.component = component;
        this.offset = offset;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        // Change position, no need to draw components, they are already drawn in ModEvents.java
        int[] newPosition = new int[]{mouseX - offset[0], mouseY - offset[1]};
        component.setPosition(newPosition);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        mc.displayGuiScreen(new ConfigScreen());
    }
}
