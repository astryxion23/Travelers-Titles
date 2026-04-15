package com.yungnickyoung.minecraft.travelerstitles.init;

import com.google.common.collect.Lists;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import com.yungnickyoung.minecraft.travelerstitles.compat.WaystonesCompat;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;

import java.util.ArrayList;
import java.util.List;

public class TTModConfig {
    public static void init() {
        MinecraftForge.EVENT_BUS.register(new TTModConfig());
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (event.getWorld().isRemote) {
            reloadConfig();
        }
    }

    @SubscribeEvent
    public void onConfigChanged(net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent event) {
        if (TravelersTitles.MOD_ID.equals(event.getModID())) {
            TTConfig.loadFromDisk();
            reloadConfig();
        }
    }

    /**
     * Bakes in updated config values.
     */
    public static void reloadConfig() {
        if (TravelersTitles.titleManager == null) {
            return;
        }

        // Biome
        TravelersTitles.titleManager.biomeTitleRenderer.maxRecentListSize = TTConfig.biomes.recentBiomeCacheSize.get();
        TravelersTitles.titleManager.biomeTitleRenderer.enabled = TTConfig.biomes.enabled.get();
        TravelersTitles.titleManager.biomeTitleRenderer.titleFadeInTicks = TTConfig.biomes.textFadeInTime.get();
        TravelersTitles.titleManager.biomeTitleRenderer.titleDisplayTime = TTConfig.biomes.textDisplayTime.get();
        TravelersTitles.titleManager.biomeTitleRenderer.titleFadeOutTicks = TTConfig.biomes.textFadeOutTime.get();
        TravelersTitles.titleManager.biomeTitleRenderer.titleDefaultTextColor = TTConfig.biomes.textColor.get();
        TravelersTitles.titleManager.biomeTitleRenderer.showTextShadow = TTConfig.biomes.renderShadow.get();
        TravelersTitles.titleManager.biomeTitleRenderer.titleTextSize = (float) TTConfig.biomes.textSize.get();
        TravelersTitles.titleManager.biomeTitleRenderer.titleXOffset = (float) TTConfig.biomes.textXOffset.get();
        TravelersTitles.titleManager.biomeTitleRenderer.titleYOffset = (float) TTConfig.biomes.textYOffset.get();
        TravelersTitles.titleManager.biomeTitleRenderer.isTextCentered = TTConfig.biomes.centerText.get();

        // Dimension
        TravelersTitles.titleManager.dimensionTitleRenderer.enabled = TTConfig.dimensions.enabled.get();
        TravelersTitles.titleManager.dimensionTitleRenderer.titleFadeInTicks = TTConfig.dimensions.textFadeInTime.get();
        TravelersTitles.titleManager.dimensionTitleRenderer.titleDisplayTime = TTConfig.dimensions.textDisplayTime.get();
        TravelersTitles.titleManager.dimensionTitleRenderer.titleFadeOutTicks = TTConfig.dimensions.textFadeOutTime.get();
        TravelersTitles.titleManager.dimensionTitleRenderer.titleDefaultTextColor = TTConfig.dimensions.textColor.get();
        TravelersTitles.titleManager.dimensionTitleRenderer.showTextShadow = TTConfig.dimensions.renderShadow.get();
        TravelersTitles.titleManager.dimensionTitleRenderer.titleTextSize = (float) TTConfig.dimensions.textSize.get();
        TravelersTitles.titleManager.dimensionTitleRenderer.titleXOffset = (float) TTConfig.dimensions.textXOffset.get();
        TravelersTitles.titleManager.dimensionTitleRenderer.titleYOffset = (float) TTConfig.dimensions.textYOffset.get();
        TravelersTitles.titleManager.dimensionTitleRenderer.isTextCentered = TTConfig.dimensions.centerText.get();

        // Waystones
        WaystonesCompat.updateRendererFromConfig(TTConfig.waystones);

        // Parse & save biome blacklist
        String rawStringofList = TTConfig.biomes.biomeBlacklist.get();
        int strLen = rawStringofList.length();

        // Validate the string's format
        if (strLen < 2 || rawStringofList.charAt(0) != '[' || rawStringofList.charAt(strLen - 1) != ']') {
            TravelersTitles.LOGGER.error("INVALID VALUE FOR SETTING 'Blacklisted Biomes'. Using empty list instead...");
            TravelersTitles.titleManager.blacklistedBiomes = new ArrayList<>();
            return;
        }

        // Parse string to list
        List<String> inputListOfStrings = Lists.newArrayList(rawStringofList.substring(1, strLen - 1).split(",\\s*"));
        TravelersTitles.titleManager.blacklistedBiomes = Lists.newArrayList(inputListOfStrings);

        // Parse & save dimension blacklist
        rawStringofList = TTConfig.dimensions.dimensionBlacklist.get();
        strLen = rawStringofList.length();

        // Validate the string's format
        if (strLen < 2 || rawStringofList.charAt(0) != '[' || rawStringofList.charAt(strLen - 1) != ']') {
            TravelersTitles.LOGGER.error("INVALID VALUE FOR SETTING 'Blacklisted Dimensions'. Using empty list instead...");
            TravelersTitles.titleManager.blacklistedDimensions = new ArrayList<>();
            return;
        }

        // Parse string to list
        inputListOfStrings = Lists.newArrayList(rawStringofList.substring(1, strLen - 1).split(",\\s*"));
        TravelersTitles.titleManager.blacklistedDimensions = Lists.newArrayList(inputListOfStrings);
    }
}
