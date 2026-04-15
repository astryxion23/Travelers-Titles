package com.yungnickyoung.minecraft.travelerstitles.render;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import com.yungnickyoung.minecraft.travelerstitles.compat.WaystonesCompat;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfig;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.ArrayList;
import java.util.List;

public class TitleRenderManager {
    public final TitleRenderer<Biome> biomeTitleRenderer = new TitleRenderer<>(
        TTConfig.biomes.recentBiomeCacheSize.get(),
        TTConfig.biomes.enabled.get(),
        TTConfig.biomes.textFadeInTime.get(),
        TTConfig.biomes.textDisplayTime.get(),
        TTConfig.biomes.textFadeOutTime.get(),
        TTConfig.biomes.textColor.get(),
        TTConfig.biomes.renderShadow.get(),
        TTConfig.biomes.textSize.get(),
        TTConfig.biomes.textXOffset.get(),
        TTConfig.biomes.textYOffset.get(),
        TTConfig.biomes.centerText.get()
    );

    public final TitleRenderer<DimensionType> dimensionTitleRenderer = new TitleRenderer<>(
        1,
        TTConfig.dimensions.enabled.get(),
        TTConfig.dimensions.textFadeInTime.get(),
        TTConfig.dimensions.textDisplayTime.get(),
        TTConfig.dimensions.textFadeOutTime.get(),
        TTConfig.dimensions.textColor.get(),
        TTConfig.dimensions.renderShadow.get(),
        TTConfig.dimensions.textSize.get(),
        TTConfig.dimensions.textXOffset.get(),
        TTConfig.dimensions.textYOffset.get(),
        TTConfig.dimensions.centerText.get()
    );

    public List<String> blacklistedBiomes = new ArrayList<>();
    public List<String> blacklistedDimensions = new ArrayList<>();

    public static String makeTranslationKey(String type, ResourceLocation id) {
        return type + "." + id.getResourceDomain() + "." + id.getResourcePath();
    }

    /**
     * Resolves the displayed dimension title: mod lang entry if present, otherwise a readable label from the registry path (no placeholder stubs).
     */
    public static ITextComponent createDimensionTitleComponent(final ResourceLocation dimensionBaseKey) {
        final String dimensionNameKey = makeTranslationKey(TravelersTitles.MOD_ID, dimensionBaseKey);
        if (I18n.hasKey(dimensionNameKey)) {
            return new TextComponentTranslation(dimensionNameKey);
        }
        return new TextComponentString(formatRegistryPathForDisplay(dimensionBaseKey.getResourcePath()));
    }

    /**
     * Resolves biome title: TT override, then {@code biome.<namespace>.<path>} if present (1.13+ style lang / resource packs),
     * otherwise the 1.12 {@link Biome#getBiomeName()} label (vanilla 1.12 lang does not define biome registry keys).
     */
    public static ITextComponent createBiomeTitleComponent(final Biome biome, final ResourceLocation biomeBaseKey) {
        final String overrideBiomeNameKey = makeTranslationKey(TravelersTitles.MOD_ID + ".biome", biomeBaseKey);
        final String normalBiomeNameKey = makeTranslationKey("biome", biomeBaseKey);
        if (I18n.hasKey(overrideBiomeNameKey)) {
            return new TextComponentTranslation(overrideBiomeNameKey);
        }
        if (I18n.hasKey(normalBiomeNameKey)) {
            return new TextComponentTranslation(normalBiomeNameKey);
        }
        return new TextComponentString(spacedWordsFromVanillaBiomeInternalName(biome.getBiomeName()));
    }

    /**
     * 1.12 {@link Biome#getBiomeName()} is CamelCase with no spaces (e.g. {@code ForestHills}); insert spaces for display.
     */
    private static String spacedWordsFromVanillaBiomeInternalName(final String internalName) {
        if (internalName == null || internalName.isEmpty()) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(internalName.length() + 8);
        for (int i = 0; i < internalName.length(); i++) {
            final char c = internalName.charAt(i);
            if (c == '_') {
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) != ' ') {
                    sb.append(' ');
                }
                continue;
            }
            if (i > 0 && Character.isUpperCase(c) && Character.isLowerCase(internalName.charAt(i - 1))) {
                sb.append(' ');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static String formatRegistryPathForDisplay(final String path) {
        if (path == null || path.isEmpty()) {
            return "";
        }
        final String[] segments = path.replace('_', ' ').split("\\s+");
        final StringBuilder sb = new StringBuilder();
        for (String seg : segments) {
            if (seg.isEmpty()) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(Character.toUpperCase(seg.charAt(0)));
            if (seg.length() > 1) {
                sb.append(seg.substring(1).toLowerCase());
            }
        }
        return sb.length() > 0 ? sb.toString() : path;
    }

    /**
     * Resource key used for dimension title translations, aligned with 1.16 {@code DimensionType} / registry naming where possible.
     */
    public static ResourceLocation getDimensionResourceLocation(World world) {
        DimensionType dimensionType = world.provider.getDimensionType();
        return new ResourceLocation("minecraft", dimensionType.getName());
    }

    /**
     * Ticks all renderers.
     */
    public void clientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (!Minecraft.getMinecraft().isGamePaused()) {
                dimensionTitleRenderer.tick();
                WaystonesCompat.clientTick();
                biomeTitleRenderer.tick();
            }
        }
    }

    /**
     * Renders all titles.
     */
    public void renderTitles(final RenderGameOverlayEvent.Pre event) {
        if (!Minecraft.getMinecraft().gameSettings.showDebugInfo && event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            float partialTicks = event.getPartialTicks();

            dimensionTitleRenderer.renderText(partialTicks);
            WaystonesCompat.renderText(partialTicks);
            biomeTitleRenderer.renderText(partialTicks);
        }
    }

    /**
     * Initializes rendering titles if conditions are met (e.g. player changed biome or dimension)
     */
    public void playerTick(final TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        BlockPos playerPos = event.player.getPosition();
        World world = player.world;

        if (player instanceof EntityPlayerSP && world != null && world.isBlockLoaded(playerPos)) {
            boolean skylightDimension = !(world.provider instanceof WorldProviderHell) && !(world.provider instanceof WorldProviderEnd);
            boolean isPlayerUnderground = skylightDimension && !world.canBlockSeeSky(playerPos);

            updateDimensionTitle(world, player, isPlayerUnderground);

            boolean isRenderingWaystoneTitle = updateWaystoneTitle(player, isPlayerUnderground);

            if (!TTConfig.waystones.waystonesOverrideBiomeTitle.get() || !isRenderingWaystoneTitle) {
                updateBiomeTitle(world, playerPos, player, isPlayerUnderground);
            } else {
                biomeTitleRenderer.clearTimer();
            }
        }
    }

    public void playerChangedDimension() {
        if (
            TTConfig.biomes.enabled.get() &&
            TTConfig.biomes.resetBiomeCacheOnDimensionChange.get()
        ) {
            biomeTitleRenderer.clearTimer();
            biomeTitleRenderer.recentEntries.clear();
        }

        if (
            Loader.isModLoaded("waystones") &&
            TTConfig.waystones.enabled.get() &&
            TTConfig.waystones.resetWaystoneCacheOnDimensionChange.get()
        ) {
            WaystonesCompat.reset();
        }
    }

    private void updateDimensionTitle(World world, EntityPlayer player, boolean isPlayerUnderground) {
        if (isPlayerUnderground && TTConfig.dimensions.onlyUpdateAtSurface.get()) {
            return;
        }

        DimensionType currDimension = world.provider.getDimensionType();

        if (dimensionTitleRenderer.enabled && !dimensionTitleRenderer.containsEntry(d -> d == currDimension)) {
            ResourceLocation dimensionBaseKey = getDimensionResourceLocation(world);
            String dimensionNameKey = makeTranslationKey(TravelersTitles.MOD_ID, dimensionBaseKey);

            if (!blacklistedDimensions.contains(dimensionBaseKey.toString())) {
                ITextComponent dimensionTitle = createDimensionTitleComponent(dimensionBaseKey);

                String dimensionColorKey = dimensionNameKey + ".color";
                String dimensionColorStr = I18n.hasKey(dimensionColorKey)
                    ? I18n.format(dimensionColorKey)
                    : dimensionTitleRenderer.titleDefaultTextColor;

                dimensionTitleRenderer.setColor(dimensionColorStr);
                dimensionTitleRenderer.displayTitle(dimensionTitle, null);
                dimensionTitleRenderer.addRecentEntry(currDimension);

                player.playSound(TTModSound.DIMENSION, (float) TTConfig.sound.dimensionVolume.get(), (float) TTConfig.sound.dimensionPitch.get());
            }
        }
    }

    private void updateBiomeTitle(World world, BlockPos playerPos, EntityPlayer player, boolean isPlayerUnderground) {
        if (isPlayerUnderground && TTConfig.biomes.onlyUpdateAtSurface.get()) {
            return;
        }

        Biome currBiome = world.getBiome(playerPos);
        ResourceLocation biomeBaseKey = Biome.REGISTRY.getNameForObject(currBiome);

        if (biomeBaseKey == null) {
            return;
        }

        if (
            biomeTitleRenderer.enabled &&
            biomeTitleRenderer.cooldownTimer <= 0 &&
            !biomeTitleRenderer.containsEntry(b -> biomeBaseKey.equals(Biome.REGISTRY.getNameForObject(b)))
        ) {
            String overrideBiomeNameKey = makeTranslationKey(TravelersTitles.MOD_ID + ".biome", biomeBaseKey);
            String normalBiomeNameKey = makeTranslationKey("biome", biomeBaseKey);

            if (!blacklistedBiomes.contains(biomeBaseKey.toString())) {
                ITextComponent biomeTitle = createBiomeTitleComponent(currBiome, biomeBaseKey);

                String overrideBiomeColorKey = overrideBiomeNameKey + ".color";
                String normalBiomeColorKey = normalBiomeNameKey + ".color";
                String biomeColorStr;
                if (I18n.hasKey(overrideBiomeColorKey)) {
                    biomeColorStr = I18n.format(overrideBiomeColorKey);
                } else if (I18n.hasKey(normalBiomeColorKey)) {
                    biomeColorStr = I18n.format(normalBiomeColorKey);
                } else {
                    biomeColorStr = biomeTitleRenderer.titleDefaultTextColor;
                }

                if (biomeTitleRenderer.displayedTitle != null && biomeTitle.getFormattedText().equals(biomeTitleRenderer.displayedTitle.getFormattedText())) {
                    return;
                }

                biomeTitleRenderer.setColor(biomeColorStr);
                biomeTitleRenderer.displayTitle(biomeTitle, null);
                biomeTitleRenderer.cooldownTimer = TTConfig.biomes.textCooldownTime.get();
                biomeTitleRenderer.addRecentEntry(currBiome);

                player.playSound(TTModSound.BIOME, (float) TTConfig.sound.biomeVolume.get(), (float) TTConfig.sound.biomePitch.get());
            }
        }
    }

    private boolean updateWaystoneTitle(EntityPlayer player, boolean isPlayerUnderground) {
        if (isPlayerUnderground && TTConfig.waystones.onlyUpdateAtSurface.get()) {
            return false;
        }

        if (Loader.isModLoaded("waystones") && TTConfig.waystones.enabled.get()) {
            return WaystonesCompat.updateWaystoneTitle(player);
        }

        return false;
    }
}
