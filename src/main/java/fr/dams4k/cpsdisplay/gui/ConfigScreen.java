package fr.dams4k.cpsdisplay.gui;

import fr.dams4k.cpsdisplay.References;
import fr.dams4k.cpsdisplay.config.GlobalConfig;
import fr.dams4k.cpsdisplay.gui.components.MComponent;
import fr.dams4k.cpsdisplay.gui.components.MComponentsManager;
import fr.dams4k.cpsdisplay.gui.components.SliderButton;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.SpacerElement;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends Screen {
    public static final int SELECTED_COLOR = 0xffffff00; //aarrggbb - Color is YELLOW
    public static final int SELECTABLE_COLOR = 0xff868686;

    public static final Component TITLE = Component.translatable("cpsdisplay.config.title");

    private static final Component ENABLED = Component.translatable("cpsdisplay.config.enabled");
    private static final Component DISABLED = Component.translatable("cpsdisplay.config.disabled");

    private static final Component TEXT_DEFAULT = Component.translatable("cpsdisplay.config.defaultText");

    private static final Component SHADOW = Component.translatable("cpsdisplay.config.showShadow");
    private static final Component RAINBOW = Component.translatable("cpsdisplay.config.showRainbow");

    private static final Component DONE = Component.translatable("gui.done");

    // private static final Component SCALE = Component.translatable("cpsdisplay.config.scale");

    private MultiLineEditBox textEditBox;

    private CycleButton<Boolean> enableModCycle = CycleButton.booleanBuilder(ENABLED, DISABLED)
            .displayOnlyValue()
            .create(0, 0, 250, 20, null);
        
    private CycleButton<Boolean> shadowCycle = CycleButton.booleanBuilder(ENABLED, DISABLED)
            .create(0, 0, 120, 20, SHADOW);
    
    
    private CycleButton<Boolean> rainbowCycle = CycleButton.booleanBuilder(ENABLED, DISABLED)
            .create(0, 0, 120, 20, RAINBOW);

    private SliderButton sliderButton = new SliderButton(0, 0, 250, 20, "cpsdisplay.config.scale", 1, 0.5, 4);
    private EditBox textColorEditBox;


    public MComponent selectedComponent = MComponentsManager.getLastSelected();

    public ConfigScreen() {
        super(TITLE);
    }

    @Override
    protected void init() {
        if (selectedComponent == null) {
            return; //TODO: there is no component.. show it to the player
        }

        // Must be created here, else the game crash
        textEditBox = new MultiLineEditBox(
            font, 0, 0, 250, 60,
            TEXT_DEFAULT, title
        );
        textColorEditBox = new EditBox(font, 0, 0, 120, 20, title);

        setConfigValues();

        GridLayout gridlayout = new GridLayout();
        gridlayout.defaultCellSetting().paddingHorizontal(5).paddingBottom(4).alignHorizontallyCenter();
        GridLayout.RowHelper gridlayout$rowhelper = gridlayout.createRowHelper(2);
        
        Button doneButton = Button.builder(DONE, (btn) -> {
            onClose();
        }).build();

        String modVersion = References.MOD_VERSION;
        if (References.RELEASE_TYPE != References.ReleaseType.RELEASE) {
            modVersion += " (" + References.RELEASE_TYPE.getString() + ")";
        }
        StringWidget modNameWidget = new StringWidget(Component.translatable("cpsdisplay.title", References.MOD_NAME, modVersion), font);

        gridlayout$rowhelper.addChild(modNameWidget, 2);
        gridlayout$rowhelper.addChild(enableModCycle, 2);
        gridlayout$rowhelper.addChild(SpacerElement.height(2), 2);
        gridlayout$rowhelper.addChild(shadowCycle);
        gridlayout$rowhelper.addChild(rainbowCycle);
        gridlayout$rowhelper.addChild(textColorEditBox, 2);

        gridlayout$rowhelper.addChild(textEditBox, 2);
        gridlayout$rowhelper.addChild(sliderButton, 2);

        gridlayout$rowhelper.addChild(SpacerElement.height(2), 2);
        gridlayout$rowhelper.addChild(doneButton, 2);

        gridlayout.arrangeElements();
        FrameLayout.alignInRectangle(gridlayout, 0, this.height / 6 - 12, this.width, this.height, 0.5F, 0.0F);
        gridlayout.visitWidgets(this::addRenderableWidget);
    }

    public void setConfigValues() {
        textEditBox.setValue(selectedComponent.config.text);

        sliderButton.setValue(selectedComponent.config.scale);
        textColorEditBox.setValue(selectedComponent.config.textColor);
        textColorEditBox.setMaxLength(6);

        shadowCycle.setValue(selectedComponent.config.shadow);
        rainbowCycle.setValue(selectedComponent.config.rainbow);
        enableModCycle.setValue(selectedComponent.config.showText);
    }

    @Override
    public void onClose() {
        selectedComponent.config.save();
        super.onClose();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        for (MComponent component : MComponentsManager.components.values()) {
            if (component.isOver(mouseX, mouseY)) {
                // Then save previously selected component
                selectedComponent.config.save();
                // And change the selected component
                selectedComponent = component;
                GlobalConfig.setLastSelectedID(component.id);
                setConfigValues();
            }
        }
        // if (DisplayManager.getEditDisplay().isOver(mouseX, mouseY) && mouseButton == 0) {
        //     int diffX = Config.positionX - (int) mouseX;
        //     int diffY = Config.positionY - (int) mouseY;
            
        //     minecraft.setScreen(new MoveScreen(diffX, diffY));
        //     return true;
        // }
        
        //TODO: when component clicked, change selected component and save previous component
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void render(GuiGraphics guiGraphics, int p_281550_, int p_282878_, float p_282465_) {
        selectedComponent.config.text = textEditBox.getValue();
        selectedComponent.config.shadow = shadowCycle.getValue();
        selectedComponent.config.showText = enableModCycle.getValue();
        selectedComponent.config.rainbow = rainbowCycle.getValue();

        selectedComponent.config.scale = (float) sliderButton.getValue();
        
        String textColor = textColorEditBox.getValue().toLowerCase();
        if (textColor.length() == 6) {
            boolean correctCharacters = true;
            for (char c : textColor.toCharArray()) {
                correctCharacters = correctCharacters && "0123456789abcdef".indexOf(c) != -1;
            }
            if (correctCharacters) {
                // Then update config value
                selectedComponent.config.textColor = textColor;
            }
        } else if (textColor.length() == 0) {
            textColorEditBox.setSuggestion("ffffff");
        } else {
            textColorEditBox.setSuggestion("");
        }

        if ("1.20 1.20.1".contains(SharedConstants.getCurrentVersion().getId())) {
            this.renderBackground(guiGraphics);
        }
        super.render(guiGraphics, p_281550_, p_282878_, p_282465_);
        
        



        // Display all components
        for (MComponent component : MComponentsManager.components.values()) {
            component.render(guiGraphics);
            // Draw gray border if you can select the component
            // This is usefull to see were are disabled components
            if (component.id != selectedComponent.id) {
                int[] boundaries = component.getIBoundaries();
                guiGraphics.hLine(boundaries[0], boundaries[2], boundaries[1], SELECTABLE_COLOR);
                guiGraphics.hLine(boundaries[0], boundaries[2], boundaries[3], SELECTABLE_COLOR);
                guiGraphics.vLine(boundaries[0], boundaries[1], boundaries[3], SELECTABLE_COLOR);
                guiGraphics.vLine(boundaries[2], boundaries[1], boundaries[3], SELECTABLE_COLOR);
            }
        }

        // Draw yellow border for the selected component
        int[] boundaries = selectedComponent.getIBoundaries();
        guiGraphics.hLine(boundaries[0], boundaries[2], boundaries[1], SELECTED_COLOR);
        guiGraphics.hLine(boundaries[0], boundaries[2], boundaries[3], SELECTED_COLOR);
        guiGraphics.vLine(boundaries[0], boundaries[1], boundaries[3], SELECTED_COLOR);
        guiGraphics.vLine(boundaries[2], boundaries[1], boundaries[3], SELECTED_COLOR);
    }
}
