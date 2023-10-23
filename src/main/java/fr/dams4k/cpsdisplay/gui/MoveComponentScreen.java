package fr.dams4k.cpsdisplay.gui;

import net.minecraft.client.gui.GuiScreen;

public class MoveComponentScreen extends GuiScreen {
    private Component component;
    private int[] offset = new int[2];
    private boolean openComponentConfig = false;

    public MoveComponentScreen(Component component, int[] offset) {
        this.component = component;
        this.offset = offset;
    }

    public MoveComponentScreen(Component component, int[] offset, boolean openComponentConfig) {
        this.component = component;
        this.offset = offset;
        this.openComponentConfig = openComponentConfig;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        
        //TODO: use the mouse position for accurate snaping

        // Change position, no need to draw components, they are already drawn in ModEvents.java
        // Position position = new Position(mouseX-offset[0], mouseY-offset[1]);
        // component.pos = position;
        component.setPosition(new int[]{mouseX-offset[0], mouseY-offset[1]});

        int hotBarWidth = 91*2;
        int hotBarHeight = 20;

        Container inventoryLeftContainer = new Container(0, height-hotBarHeight, (width-hotBarWidth)/2, hotBarHeight);
        System.out.println(inventoryLeftContainer.isInside(component));

        // int[] newPosition = new int[]{mouseX-offset[0], mouseY-offset[1]};
        // component.setPosition(newPosition);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (this.openComponentConfig) {
            mc.displayGuiScreen(new ComponentConfigScreen(component));
        } else {
            mc.displayGuiScreen(new ConfigScreen());
        }
    }
}
