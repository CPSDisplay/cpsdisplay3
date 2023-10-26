package fr.dams4k.cpsdisplay;

import fr.dams4k.cpsdisplay.config.Config;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@OnlyIn(Dist.CLIENT)
@Mod(References.MOD_ID)
public class CPSDisplay {
    public CPSDisplay() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC, "cpsdisplay.toml");
    }
}