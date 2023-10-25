package fr.dams4k.cpsdisplay.config;

import fr.dams4k.cpsdisplay.References;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = References.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    
    public static final ForgeConfigSpec.ConfigValue<String> TEXT;

    public static String text;

    static {
        BUILDER.comment("Don't forget to go outside, your love may be waiting for you <3");

        TEXT = BUILDER.define("text", "[{0} | {1}] CPS");

        SPEC = BUILDER.build();
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        System.out.println("------------------- Load -------------------");
        text = TEXT.get();
    }
}
