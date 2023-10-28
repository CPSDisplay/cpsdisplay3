package fr.dams4k.cpsdisplay.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ConfigCommand {
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(Commands.literal("cpsdisplay").executes(
            (context) -> {
                return openConfigMenu();
            }
        ));
    }

    private static int openConfigMenu() {
        return 1;
    }
}
