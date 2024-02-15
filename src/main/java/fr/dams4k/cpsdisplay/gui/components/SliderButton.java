package fr.dams4k.cpsdisplay.gui.components;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public class SliderButton extends AbstractSliderButton {
    public double minValue;
    public double maxValue;

    public SliderButton(int x, int y, int width, int height, Component title, double value, double minValue, double maxValue) {
        super(x, y, width, height, title, (value-minValue) / (maxValue-minValue));
        System.out.println(value);
        System.out.println((value-minValue) / (maxValue-minValue));
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    protected void updateMessage() {
    }

    @Override
    protected void applyValue() {
    }

    public void setValue(double value) {
        double percentValue = (value-minValue) / (maxValue-minValue);
        this.value = percentValue;
    }

    public double getValue() {
        return this.value * (maxValue-minValue) + minValue;
    }
}
