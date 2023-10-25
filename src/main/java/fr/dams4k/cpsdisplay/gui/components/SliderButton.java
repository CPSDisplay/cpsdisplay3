package fr.dams4k.cpsdisplay.gui.components;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public class SliderButton extends AbstractSliderButton {
    public SliderButton(int x, int y, int width, int height, Component title, double value) {
        super(x, y, width, height, title, value);
    }

    @Override
    protected void updateMessage() {
    }

    @Override
    protected void applyValue() {
    }
}
