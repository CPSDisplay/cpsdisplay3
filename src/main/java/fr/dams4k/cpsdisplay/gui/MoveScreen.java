package fr.dams4k.cpsdisplay.gui;

import fr.dams4k.cpsdisplay.config.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MoveScreen extends Screen {
    private int diffX = 0;
    private int diffY = 0;

    public static final Component TITLE = Component.translatable("cpsdisplay.screen.move");

    protected MoveScreen(int diffX, int diffY) {
        super(TITLE);

        this.diffX = diffX;
        this.diffY = diffY;
    }
    
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        int newX = diffX + mouseX;
        int newY = diffY + mouseY;
        Config.positionX = newX;
        Config.positionY = newY;
    }

    @Override
    public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
        minecraft.setScreen(new ConfigScreen());

        return super.mouseReleased(p_94722_, p_94723_, p_94724_);
    }
}
