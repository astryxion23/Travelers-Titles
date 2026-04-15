package com.yungnickyoung.minecraft.travelerstitles.compat;

import com.yungnickyoung.minecraft.travelerstitles.config.ConfigWaystones;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfig;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModSound;
import com.yungnickyoung.minecraft.travelerstitles.render.TitleRenderer;
import net.blay09.mods.waystones.client.ClientWaystones;
import net.blay09.mods.waystones.util.WaystoneEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaystonesCompat {
    private static List<WaystoneEntry> knownWaystones = new ArrayList<>();
    private static WaystoneEntry closestWaystone;
    private static int waystoneUpdateTimer = 0;
    private static final TitleRenderer<WaystoneEntry> waystoneTitleRenderer = new TitleRenderer<>(
        TTConfig.waystones.recentWaystoneCacheSize.get(),
        TTConfig.waystones.enabled.get(),
        TTConfig.waystones.textFadeInTime.get(),
        TTConfig.waystones.textDisplayTime.get(),
        TTConfig.waystones.textFadeOutTime.get(),
        TTConfig.waystones.textColor.get(),
        TTConfig.waystones.renderShadow.get(),
        TTConfig.waystones.textSize.get(),
        TTConfig.waystones.textXOffset.get(),
        TTConfig.waystones.textYOffset.get(),
        TTConfig.waystones.centerText.get()
    );

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new WaystonesCompat());
    }

    private static void updateClosestWaystone(final TickEvent.PlayerTickEvent event) {
        waystoneUpdateTimer++;

        if (waystoneUpdateTimer % 10 == 0) {
            int playerDimension = event.player.dimension;
            BlockPos playerPos = event.player.getPosition();
            double minDist = Double.MAX_VALUE;

            for (WaystoneEntry waystone : knownWaystones) {
                int waystoneDimension = waystone.getDimensionId();
                BlockPos waystonePos = waystone.getPos();

                if (waystone.getName() == null || waystone.getName().isEmpty()) {
                    continue;
                }

                if (playerDimension == waystoneDimension) {
                    double distance = playerPos.distanceSq(
                        waystonePos.getX() + 0.5,
                        waystonePos.getY() + 0.5,
                        waystonePos.getZ() + 0.5
                    );
                    if (distance < minDist) {
                        minDist = distance;
                        closestWaystone = waystone;
                    }
                }
            }

            int range = TTConfig.waystones.range.get();
            if (minDist > range * range) {
                closestWaystone = null;
            }
        }
    }

    /**
     * Mirrors 1.16 {@code KnownWaystonesEvent} plus the closest-waystone scan from {@code PlayerTickEvent}.
     */
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == Side.CLIENT && event.phase == TickEvent.Phase.END && event.player.world.isRemote) {
            knownWaystones = new ArrayList<>(Arrays.asList(ClientWaystones.getKnownWaystones()));
            updateClosestWaystone(event);
        }
    }

    public static boolean updateWaystoneTitle(EntityPlayer player) {
        if (closestWaystone == null || closestWaystone.getName() == null || closestWaystone.getName().isEmpty()) {
            return waystoneTitleRenderer.titleTimer > 0;
        }

        if (
            waystoneTitleRenderer.enabled &&
            waystoneTitleRenderer.cooldownTimer <= 0 &&
            !waystoneTitleRenderer.containsEntry(w -> w.getName().equals(closestWaystone.getName()))
        ) {
            if (
                waystoneTitleRenderer.displayedTitle == null ||
                !closestWaystone.getName().equals(waystoneTitleRenderer.displayedTitle.getFormattedText())
            ) {
                waystoneTitleRenderer.setColor(waystoneTitleRenderer.titleDefaultTextColor);
                waystoneTitleRenderer.displayTitle(new TextComponentString(closestWaystone.getName()), null);
                waystoneTitleRenderer.cooldownTimer = TTConfig.waystones.textCooldownTime.get();
                waystoneTitleRenderer.addRecentEntry(closestWaystone);

                player.playSound(TTModSound.WAYSTONE, (float) TTConfig.sound.waystoneVolume.get(), (float) TTConfig.sound.waystonePitch.get());
            }
        }
        return waystoneTitleRenderer.titleTimer > 0;
    }

    public static void clientTick() {
        waystoneTitleRenderer.tick();
    }

    public static void renderText(float partialTicks) {
        waystoneTitleRenderer.renderText(partialTicks);
    }

    public static void reset() {
        waystoneTitleRenderer.clearTimer();
        waystoneTitleRenderer.recentEntries.clear();
    }

    public static void updateRendererFromConfig(ConfigWaystones config) {
        waystoneTitleRenderer.maxRecentListSize = config.recentWaystoneCacheSize.get();
        waystoneTitleRenderer.enabled = config.enabled.get();
        waystoneTitleRenderer.titleFadeInTicks = config.textFadeInTime.get();
        waystoneTitleRenderer.titleDisplayTime = config.textDisplayTime.get();
        waystoneTitleRenderer.titleFadeOutTicks = config.textFadeOutTime.get();
        waystoneTitleRenderer.titleDefaultTextColor = config.textColor.get();
        waystoneTitleRenderer.showTextShadow = config.renderShadow.get();
        waystoneTitleRenderer.titleTextSize = (float) config.textSize.get();
        waystoneTitleRenderer.titleXOffset = (float) config.textXOffset.get();
        waystoneTitleRenderer.titleYOffset = (float) config.textYOffset.get();
        waystoneTitleRenderer.isTextCentered = config.centerText.get();
    }
}
