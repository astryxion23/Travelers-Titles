package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesClient;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import com.yungnickyoung.minecraft.travelerstitles.services.ClientServices;

public final class ClientConfigModule {
    private ClientConfigModule() {
    }

    /**
     * Bakes in updated config values.
     */
    public static void updateRenderersFromConfig() {
        TravelersTitlesClient.TITLE_MANAGER.biomeTitleRenderer.maxRecentListSize = TravelersTitlesCommon.CONFIG.biomes.recentBiomeCacheSize;
        TravelersTitlesClient.TITLE_MANAGER.biomeTitleRenderer.enabled = TravelersTitlesCommon.CONFIG.biomes.enabled;
        TravelersTitlesClient.TITLE_MANAGER.biomeTitleRenderer.titleFadeInTicks = TravelersTitlesCommon.CONFIG.biomes.textFadeInTime;
        TravelersTitlesClient.TITLE_MANAGER.biomeTitleRenderer.titleDisplayTime = TravelersTitlesCommon.CONFIG.biomes.textDisplayTime;
        TravelersTitlesClient.TITLE_MANAGER.biomeTitleRenderer.titleFadeOutTicks = TravelersTitlesCommon.CONFIG.biomes.textFadeOutTime;
        TravelersTitlesClient.TITLE_MANAGER.biomeTitleRenderer.titleDefaultTextColor = TravelersTitlesCommon.CONFIG.biomes.textColor;
        TravelersTitlesClient.TITLE_MANAGER.biomeTitleRenderer.showTextShadow = TravelersTitlesCommon.CONFIG.biomes.renderShadow;
        TravelersTitlesClient.TITLE_MANAGER.biomeTitleRenderer.titleTextSize = (float) TravelersTitlesCommon.CONFIG.biomes.textSize;
        TravelersTitlesClient.TITLE_MANAGER.biomeTitleRenderer.titleXOffset = TravelersTitlesCommon.CONFIG.biomes.textXOffset;
        TravelersTitlesClient.TITLE_MANAGER.biomeTitleRenderer.titleYOffset = TravelersTitlesCommon.CONFIG.biomes.textYOffset;
        TravelersTitlesClient.TITLE_MANAGER.biomeTitleRenderer.isTextCentered = TravelersTitlesCommon.CONFIG.biomes.centerText;

        TravelersTitlesClient.TITLE_MANAGER.dimensionTitleRenderer.enabled = TravelersTitlesCommon.CONFIG.dimensions.enabled;
        TravelersTitlesClient.TITLE_MANAGER.dimensionTitleRenderer.titleFadeInTicks = TravelersTitlesCommon.CONFIG.dimensions.textFadeInTime;
        TravelersTitlesClient.TITLE_MANAGER.dimensionTitleRenderer.titleDisplayTime = TravelersTitlesCommon.CONFIG.dimensions.textDisplayTime;
        TravelersTitlesClient.TITLE_MANAGER.dimensionTitleRenderer.titleFadeOutTicks = TravelersTitlesCommon.CONFIG.dimensions.textFadeOutTime;
        TravelersTitlesClient.TITLE_MANAGER.dimensionTitleRenderer.titleDefaultTextColor = TravelersTitlesCommon.CONFIG.dimensions.textColor;
        TravelersTitlesClient.TITLE_MANAGER.dimensionTitleRenderer.showTextShadow = TravelersTitlesCommon.CONFIG.dimensions.renderShadow;
        TravelersTitlesClient.TITLE_MANAGER.dimensionTitleRenderer.titleTextSize = (float) TravelersTitlesCommon.CONFIG.dimensions.textSize;
        TravelersTitlesClient.TITLE_MANAGER.dimensionTitleRenderer.titleXOffset = TravelersTitlesCommon.CONFIG.dimensions.textXOffset;
        TravelersTitlesClient.TITLE_MANAGER.dimensionTitleRenderer.titleYOffset = TravelersTitlesCommon.CONFIG.dimensions.textYOffset;
        TravelersTitlesClient.TITLE_MANAGER.dimensionTitleRenderer.isTextCentered = TravelersTitlesCommon.CONFIG.dimensions.centerText;

        ClientServices.WAYSTONES.updateRendererFromConfig(TravelersTitlesCommon.CONFIG.waystones);
    }
}
