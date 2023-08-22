package fr.dams4k.cpsdisplay.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

public class ComponentConfigScreen extends ModScreen {
    private Component component;

    public ComponentConfigScreen(Component component) {
        this.component = component;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
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
}
