package com.yungnickyoung.minecraft.travelerstitles;

import com.yungnickyoung.minecraft.travelerstitles.config.TTConfig;
import com.yungnickyoung.minecraft.travelerstitles.command.BiomeTitleCommand;
import com.yungnickyoung.minecraft.travelerstitles.command.DimensionTitleCommand;
import com.yungnickyoung.minecraft.travelerstitles.command.ReloadConfigCommand;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModClient;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModCompat;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModConfig;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModSound;
import com.yungnickyoung.minecraft.travelerstitles.render.TitleRenderManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = TravelersTitles.MOD_ID)
public class TravelersTitles {
    public static final String MOD_ID = "travelerstitles";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static TitleRenderManager titleManager;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            TTConfig.init(event.getModConfigurationDirectory());
            titleManager = new TitleRenderManager();
            TTModConfig.init();
            TTModCompat.init();
            TTModClient.init();
            TTModSound.init();
        }
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        if (!event.getServer().isDedicatedServer()) {
            event.registerServerCommand(new ReloadConfigCommand());
            event.registerServerCommand(new BiomeTitleCommand());
            event.registerServerCommand(new DimensionTitleCommand());
        }
    }
}
