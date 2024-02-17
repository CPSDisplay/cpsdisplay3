package fr.dams4k.cpsdisplay.gui.components;

import javax.annotation.Nonnull;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class SliderButton extends AbstractSliderButton {
    public @Nonnull String titleKey = "";
    
    public double minValue;
    public double maxValue;


    public SliderButton(int x, int y, int width, int height, @Nonnull String titleKey, double value, double minValue, double maxValue) {
        super(x, y, width, height, CommonComponents.EMPTY, (value-minValue) / (maxValue-minValue));

        this.titleKey = titleKey;
        this.minValue = minValue;
        this.maxValue = maxValue;

        this.updateMessage();
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Component.translatable(titleKey, Math.round(this.getValue()*100f))); //TODO: put this somewhere else
    }

    @Override
    protected void applyValue() {
    }

    public void setValue(double value) {
        double percentValue = (value-minValue) / (maxValue-minValue);
        this.value = percentValue;
        this.updateMessage();
    }

    public double getValue() {
        return this.value * (maxValue-minValue) + minValue;
    }
}
