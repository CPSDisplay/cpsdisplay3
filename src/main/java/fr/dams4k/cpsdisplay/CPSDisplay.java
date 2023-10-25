package fr.dams4k.cpsdisplay;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import fr.dams4k.cpsdisplay.config.Config;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
@Mod(References.MOD_ID)
public class CPSDisplay {
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public CPSDisplay() {
        

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        System.out.println("------------------- Setup -------------------");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC, "cpsdisplay.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    // @Mod.EventBusSubscriber(modid = References.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    // public static class ClientModEvents
    // {
    //     @SubscribeEvent
    //     public static void onClientSetup(FMLClientSetupEvent event)
    //     {
    //         // Some client setup code
    //         LOGGER.info("HELLO FROM CLIENT SETUP");
    //         LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    //     }
    // }
}