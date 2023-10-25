package fr.dams4k.cpsdisplay.gui;

import fr.dams4k.cpsdisplay.config.Config;
import fr.dams4k.cpsdisplay.gui.components.SliderButton;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.SpacerElement;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends Screen {
    private static final Component TITLE = Component.translatable("cpsdisplay.config.title");

    private static final Component ENABLED = Component.translatable("cpsdisplay.config.enabled");
    private static final Component DISABLED = Component.translatable("cpsdisplay.config.disabled");

    private static final Component TEXT_DEFAULT = Component.translatable("cpsdisplay.config.defaultText");

    private static final Component SHADOW = Component.translatable("cpsdisplay.config.showShadow");

    private MultiLineEditBox textEditBox;

    public ConfigScreen() {
        super(TITLE);
    }

    @Override
    protected void init() {
        textEditBox = new MultiLineEditBox(
            font, 0, 0, 250, 60,
            TEXT_DEFAULT, title
        );
        textEditBox.setValue(Config.text);

        GridLayout gridlayout = new GridLayout();
        gridlayout.defaultCellSetting().paddingHorizontal(5).paddingBottom(4).alignHorizontallyCenter();
        GridLayout.RowHelper gridlayout$rowhelper = gridlayout.createRowHelper(2);
        
        CycleButton<Boolean> enableModCycle = CycleButton.booleanBuilder(ENABLED, DISABLED)
            .displayOnlyValue()
            .create(0, 0, 250, 20, null);
        
        CycleButton<Boolean> shadowCycle = CycleButton.booleanBuilder(ENABLED, DISABLED).create(0, 0, 120, 20, SHADOW);

        
        SliderButton sliderButton = new SliderButton(0, 0, 120, 20, title, 3);

        gridlayout$rowhelper.addChild(enableModCycle, 2);
        gridlayout$rowhelper.addChild(SpacerElement.height(2), 2);
        gridlayout$rowhelper.addChild(shadowCycle);
        gridlayout$rowhelper.addChild(sliderButton);

        gridlayout$rowhelper.addChild(textEditBox, 2);
        
        gridlayout.arrangeElements();
        FrameLayout.alignInRectangle(gridlayout, 0, this.height / 6 - 12, this.width, this.height, 0.5F, 0.0F);
        gridlayout.visitWidgets(this::addRenderableWidget);
    }

    @Override
    public void onClose() {
        Config.TEXT.set(textEditBox.getValue());
        super.onClose();
    }
}
