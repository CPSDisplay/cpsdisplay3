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
    public static final ForgeConfigSpec.BooleanValue SHOW_TEXT;
    public static final ForgeConfigSpec.BooleanValue SHADOW;

    public static final ForgeConfigSpec.IntValue POSITION_X;
    public static final ForgeConfigSpec.IntValue POSITION_Y;
    
    public static boolean showText;
    public static String text;
    public static boolean shadow;

    public static int positionX;
    public static int positionY;

    public static boolean loaded = false; // TODO: Why did i need to have this fking variable? Change i change Config values, it automatically save and reload, that's annoying af

    static {
        BUILDER.comment("Don't forget to go outside, your love may be waiting for you <3");

        TEXT = BUILDER.define("text", "[{0} | {1}] CPS");
        SHOW_TEXT = BUILDER.define("showText", true);
        SHADOW = BUILDER.define("shadow", true);

        POSITION_X = BUILDER.defineInRange("positionX", 50, Integer.MIN_VALUE, Integer.MAX_VALUE);
        POSITION_Y = BUILDER.defineInRange("positionY", 50, Integer.MIN_VALUE, Integer.MAX_VALUE);

        SPEC = BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (loaded) return;

        text = TEXT.get();
        showText = SHOW_TEXT.get();
        shadow = SHADOW.get();

        positionX = POSITION_X.get();
        positionY = POSITION_Y.get();

        loaded = true;
    }

    public static void save() {
        POSITION_X.set(positionX);
        POSITION_Y.set(positionY);

        TEXT.set(text);
        SHOW_TEXT.set(showText);
        SHADOW.set(shadow);
    }
}
