package fr.dams4k.cpsdisplay.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MoveScreen extends Screen {
    public static final Component TITLE = Component.translatable("cpsdisplay.screen.move");

    protected MoveScreen() {
        super(TITLE);
    }
    
    @Override
    public void render(GuiGraphics guiGraphics, int p_281550_, int p_282878_, float p_282465_) {
        for(Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, p_281550_, p_282878_, p_282465_);
        }
    }

    @Override
    public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
        System.out.println("mouse released");
        return super.mouseReleased(p_94722_, p_94723_, p_94724_);
    }
}
