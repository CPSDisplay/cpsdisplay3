package fr.dams4k.cpsdisplay.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.SpacerElement;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends Screen {
    private static final Minecraft mc = Minecraft.getInstance();

    private static final Component SCREEN_TITLE = Component.translatable("cpsdisplay.config.title");

    private static final Component ENABLED = Component.translatable("cpsdisplay.config.enabled");
    private static final Component DISABLED = Component.translatable("cpsdisplay.config.disabled");


    public ConfigScreen() {
        super(SCREEN_TITLE);
    }

    @Override
    protected void init() {
        EditBox editBox = new EditBox(font, 120, 20, ENABLED);
        Checkbox checkbox = new Checkbox(0, 0, 32, 32, ENABLED, true);

        MultiLineEditBox multiLineEditBox = new MultiLineEditBox(font, 120, 120, 120, 120, ENABLED, ENABLED);

        CycleButton cycleButton = CycleButton.onOffBuilder(true).create(0, 0, 120, 20, ENABLED);

        CycleButton<Boolean> cycleButton2 = CycleButton.booleanBuilder(ENABLED, DISABLED).create(0, 0, 120, 20, null);
        
        GridLayout gridlayout = new GridLayout();
        gridlayout.defaultCellSetting().paddingHorizontal(5).paddingBottom(4).alignHorizontallyCenter();
        GridLayout.RowHelper gridlayout$rowhelper = gridlayout.createRowHelper(2);
        
        gridlayout$rowhelper.addChild(SpacerElement.height(26), 2);
        gridlayout$rowhelper.addChild(Button.builder(ENABLED, (btn) -> {

        }).build());

        // gridlayout$rowhelper.addChild(editBox);

        // gridlayout$rowhelper.addChild(checkbox);
        gridlayout$rowhelper.addChild(cycleButton2);
        
        gridlayout.arrangeElements();
        FrameLayout.alignInRectangle(gridlayout, 0, this.height / 6 - 12, this.width, this.height, 0.5F, 0.0F);
        gridlayout.visitWidgets(this::addRenderableWidget);
    }
}
