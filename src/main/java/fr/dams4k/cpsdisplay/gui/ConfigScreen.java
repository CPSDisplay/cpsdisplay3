package fr.dams4k.cpsdisplay.gui;

import fr.dams4k.cpsdisplay.References;
import fr.dams4k.cpsdisplay.config.Config;
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
    public static final Component TITLE = Component.translatable("cpsdisplay.config.title");

    private static final Component ENABLED = Component.translatable("cpsdisplay.config.enabled");
    private static final Component DISABLED = Component.translatable("cpsdisplay.config.disabled");

    private static final Component TEXT_DEFAULT = Component.translatable("cpsdisplay.config.defaultText");

    private static final Component SHADOW = Component.translatable("cpsdisplay.config.showShadow");

    private static final Component DONE = Component.translatable("gui.done");

    
    private MultiLineEditBox textEditBox;

    private CycleButton<Boolean> enableModCycle = CycleButton.booleanBuilder(ENABLED, DISABLED)
            .displayOnlyValue()
            .create(0, 0, 250, 20, null);
        
    private CycleButton<Boolean> shadowCycle = CycleButton.booleanBuilder(ENABLED, DISABLED).create(0, 0, 120, 20, SHADOW);

    private SliderButton sliderButton = new SliderButton(0, 0, 250, 20, title, 1, 0.5, 4);
    private EditBox textColorEditBox;


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

        sliderButton.setValue(Config.scale);

        textColorEditBox = new EditBox(font, 0, 0, 120, 20, title);
        textColorEditBox.setValue(Config.textColor);
        textColorEditBox.setMaxLength(6);

        shadowCycle.setValue(Config.shadow);
        enableModCycle.setValue(Config.showText);

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
        gridlayout$rowhelper.addChild(textColorEditBox);

        gridlayout$rowhelper.addChild(textEditBox, 2);
        gridlayout$rowhelper.addChild(sliderButton, 2);

        gridlayout$rowhelper.addChild(SpacerElement.height(2), 2);
        gridlayout$rowhelper.addChild(doneButton, 2);

        gridlayout.arrangeElements();
        FrameLayout.alignInRectangle(gridlayout, 0, this.height / 6 - 12, this.width, this.height, 0.5F, 0.0F);
        gridlayout.visitWidgets(this::addRenderableWidget);
    }

    @Override
    public void onClose() {
        Config.save();
        super.onClose();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (EditDisplayComponent.isOver(mouseX, mouseY) && mouseButton == 0) {
            int diffX = Config.positionX - (int) mouseX;
            int diffY = Config.positionY - (int) mouseY;
            
            minecraft.setScreen(new MoveScreen(diffX, diffY));
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void render(GuiGraphics guiGraphics, int p_281550_, int p_282878_, float p_282465_) {
        Config.text = textEditBox.getValue();
        Config.shadow = shadowCycle.getValue();
        Config.showText = enableModCycle.getValue();

        Config.scale = (float) sliderButton.getValue();
        
        String textColor = textColorEditBox.getValue().toLowerCase();
        if (textColor.length() == 6) {
            boolean correctCharacters = true;
            for (char c : textColor.toCharArray()) {
                correctCharacters = correctCharacters && "0123456789abcdef".indexOf(c) != -1;
            }
            if (correctCharacters) {
                Config.textColor = textColor;
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
        

        EditDisplayComponent.render(guiGraphics);
    }
}
