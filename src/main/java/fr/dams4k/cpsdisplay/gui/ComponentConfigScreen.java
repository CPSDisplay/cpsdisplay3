package fr.dams4k.cpsdisplay.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiScreen;

public class ComponentConfigScreen extends ModScreen {
    private Component component;

    public ComponentConfigScreen(Component component) {
        this.component = component;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawCutBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(new ConfigScreen());
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (component.clicked(mouseX, mouseY)) {
            int[] componentPosition = component.getPosition();
            int[] offset = new int[]{mouseX - componentPosition[0], mouseY - componentPosition[1]};

            mc.displayGuiScreen(new MoveComponentScreen(component, offset, true));
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void drawCutBackground() {
        int color = 0xc0101010;
        int margin = 32;

        int[] snaps = component.getSnaps();

        GuiScreen.drawRect(0, 0, snaps[0]-margin, height, color);
        GuiScreen.drawRect(snaps[2]+margin, 0, width, height, color);
        GuiScreen.drawRect(snaps[0]-margin, 0, snaps[2]+margin, snaps[1]-margin, color);
        GuiScreen.drawRect(snaps[0]-margin, snaps[3]+margin, snaps[2]+margin, height, color);
    }
}
