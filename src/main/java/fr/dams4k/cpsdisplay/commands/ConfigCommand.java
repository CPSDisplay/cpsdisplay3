package fr.dams4k.cpsdisplay.commands;

import com.mojang.brigadier.CommandDispatcher;

import fr.dams4k.cpsdisplay.gui.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ConfigCommand {
    public static final Minecraft mc = Minecraft.getInstance();

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(Commands.literal("cpsdisplay").executes(
            (context) -> {
                return openConfigMenu();
            }
        ));
    }

    private static int openConfigMenu() {
        mc.setScreen(new ConfigScreen());
        return 1;
    }
}
