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
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOW_TEXT;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHADOW;
    
    public static boolean showText;
    public static String text;
    public static boolean shadow;

    public static int positionX;
    public static int positionY;

    static {
        BUILDER.comment("Don't forget to go outside, your love may be waiting for you <3");

        TEXT = BUILDER.define("text", "[{0} | {1}] CPS");
        SHOW_TEXT = BUILDER.define("showText", true);
        SHADOW = BUILDER.define("shadow", true);

        SPEC = BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        text = TEXT.get();
        showText = SHOW_TEXT.get();
        shadow = SHADOW.get();
    }

    public static void save() {
        TEXT.set(text);
        SHOW_TEXT.set(showText);
        SHADOW.set(shadow);
    }
}
