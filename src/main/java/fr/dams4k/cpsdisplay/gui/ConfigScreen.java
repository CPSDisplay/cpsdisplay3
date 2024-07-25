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

    private static final Component DELETE_COMPONENT = Component.translatable("cpsdisplay.config.deleteComponent");


    private static final Component TEXT_COLOR_LABEL = Component.translatable("cpsdisplay.config.label.textColor");

    private static final Component TEXT_DEFAULT = Component.translatable("cpsdisplay.config.defaultText");

    private static final Component SHADOW = Component.translatable("cpsdisplay.config.showShadow");
    private static final Component RAINBOW = Component.translatable("cpsdisplay.config.showRainbow");

    private static final Component DONE = Component.translatable("gui.done");

    private static final Component NEW_COMPONENT = Component.translatable("cpsdisplay.config.addComponent");

    // Components settings
    private MultiLineEditBox textEditBox;

    private CycleButton<Boolean> enableModCycle = CycleButton.booleanBuilder(ENABLED, DISABLED)
            .displayOnlyValue()
            .create(0, 0, 120, 20, null);
        
    private CycleButton<Boolean> shadowCycle = CycleButton.booleanBuilder(ENABLED, DISABLED)
            .create(0, 0, 120, 20, SHADOW);
    
    
    private CycleButton<Boolean> rainbowCycle = CycleButton.booleanBuilder(ENABLED, DISABLED)
            .create(0, 0, 120, 20, RAINBOW);

    private SliderButton sliderButton = new SliderButton(0, 0, 250, 20, "cpsdisplay.config.scale", 1, 0.5, 4);
    private EditBox textColorEditBox;

    
    public MComponent selectedComponent = MComponentsManager.getLastSelectedOrFirst();

    // Component creation/destruction
    private Button newComponentButton = Button.builder(NEW_COMPONENT, (btn) -> {
        selectedComponent = MComponentsManager.createComponent();
        setConfigValues();
        minecraft.setScreen(new ConfigScreen());
    }).width(160).build();
    private Button deleteComponentButton = Button.builder(DELETE_COMPONENT, (btn) -> {
        selectedComponent.delete();
        selectedComponent = MComponentsManager.getFirstComponent();
        setConfigValues();
        minecraft.setScreen(new ConfigScreen());
    }).width(120).build();

    public ConfigScreen() {
        super(TITLE);
    }

    @Override
    protected void init() {
        GridLayout bottomGrid = new GridLayout();
        bottomGrid.defaultCellSetting().paddingHorizontal(5).alignVerticallyBottom();
        GridLayout.RowHelper bottomGrid$rowhelper = bottomGrid.createRowHelper(2);
        bottomGrid$rowhelper.addChild(newComponentButton, 2);

        bottomGrid.arrangeElements();
        FrameLayout.alignInRectangle(bottomGrid, 0, 0, this.width, this.height, 0.5F, 1F);
        bottomGrid.visitWidgets(this::addRenderableWidget);

        if (selectedComponent == null) {
            selectedComponent = MComponentsManager.getFirstComponent(); // Maybe we can find another
        }

        // Must be created here, else the game crash
        textEditBox = new MultiLineEditBox(
            font, 0, 0, 250, 30,
            TEXT_DEFAULT, title
        );
        StringWidget textColorLabel = new StringWidget(120, 20, TEXT_COLOR_LABEL, font);
        textColorLabel.alignRight();

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
        if (selectedComponent != null) {
            gridlayout$rowhelper.addChild(enableModCycle);
            gridlayout$rowhelper.addChild(deleteComponentButton);
            gridlayout$rowhelper.addChild(SpacerElement.height(2), 2);
            gridlayout$rowhelper.addChild(shadowCycle);
            gridlayout$rowhelper.addChild(rainbowCycle);

            gridlayout$rowhelper.addChild(textColorLabel);
            gridlayout$rowhelper.addChild(textColorEditBox);

            gridlayout$rowhelper.addChild(textEditBox, 2);
            gridlayout$rowhelper.addChild(sliderButton, 2);

            gridlayout$rowhelper.addChild(SpacerElement.height(2), 2);
        }

        gridlayout$rowhelper.addChild(doneButton, 2);

        gridlayout.arrangeElements();
        FrameLayout.alignInRectangle(gridlayout, 0, 12, this.width, this.height, 0.5F, 0.0F);
        gridlayout.visitWidgets(this::addRenderableWidget);
    }

    public void setConfigValues() {
        if (selectedComponent == null) return;
        
        GlobalConfig.setLastSelectedID(selectedComponent.id);
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
        if (selectedComponent != null) selectedComponent.config.save();
        super.onClose();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        for (MComponent component : MComponentsManager.components.values()) {
            if (component.isOver(mouseX, mouseY) && mouseButton == 0) {
                if (selectedComponent != null) {
                    // Then save previously selected component
                    selectedComponent.config.save();
                }
                System.out.println("CLICK");
                System.out.println(component.id);
                // And change the selected component
                selectedComponent = component;
                setConfigValues();

                // And display the moving screen
                //TODO: find a way to change the screen only if we have dragged to component? Or thing about something else..
                int diffX = component.config.positionX - (int) mouseX;
                int diffY = component.config.positionY - (int) mouseY;
                
                minecraft.setScreen(new MoveScreen(diffX, diffY));

                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void render(GuiGraphics guiGraphics, int p_281550_, int p_282878_, float p_282465_) {
        if (selectedComponent != null) {
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
        }

        if ("1.20 1.20.1".contains(SharedConstants.getCurrentVersion().getId())) {
            this.renderBackground(guiGraphics);
        }
        super.render(guiGraphics, p_281550_, p_282878_, p_282465_);

        // Display all components
        // Must be after render background else it will be overdrawn
        for (MComponent component : MComponentsManager.components.values()) {
            component.render(guiGraphics);
            // Draw gray border if you can select the component
            // This is usefull to see were are disabled components
            int color = SELECTABLE_COLOR;
            if (component.id == selectedComponent.id) {
                color = SELECTED_COLOR;
            }

            int[] boundaries = component.getIBoundaries();
            guiGraphics.hLine(boundaries[0], boundaries[2], boundaries[1], color);
            guiGraphics.hLine(boundaries[0], boundaries[2], boundaries[3], color);
            guiGraphics.vLine(boundaries[0], boundaries[1], boundaries[3], color);
            guiGraphics.vLine(boundaries[2], boundaries[1], boundaries[3], color);
        }
    }
}
