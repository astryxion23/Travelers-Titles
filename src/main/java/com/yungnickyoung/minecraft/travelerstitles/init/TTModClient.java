package com.yungnickyoung.minecraft.travelerstitles.init;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

@SideOnly(Side.CLIENT)
public class TTModClient {
    public static void init() {
        MinecraftForge.EVENT_BUS.register(new TTModClient());
    }

    @SubscribeEvent
    public void playerTick(final TickEvent.PlayerTickEvent event) {
        TravelersTitles.titleManager.playerTick(event);
    }

    @SubscribeEvent
    public void playerChangedimension(final PlayerEvent.PlayerChangedDimensionEvent event) {
        TravelersTitles.titleManager.playerChangedDimension();
    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        TravelersTitles.titleManager.clientTick(event);
    }

    @SubscribeEvent
    public void renderTitles(RenderGameOverlayEvent.Pre event) {
        TravelersTitles.titleManager.renderTitles(event);
    }
}
