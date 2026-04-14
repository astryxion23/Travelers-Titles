package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesCommon;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

/**
 * Registers {@link SoundModule} entries during {@link RegisterEvent}. The mod constructor runs after
 * built-in registries are frozen on the client, so {@link SoundModule#init()} cannot be used on NeoForge.
 */
public final class SoundModuleNeoForge {
    private SoundModuleNeoForge() {}

    public static void init(IEventBus modBus) {
        modBus.register(SoundModuleNeoForge.class);
    }

    @SubscribeEvent
    static void onRegisterSounds(RegisterEvent event) {
        event.register(Registries.SOUND_EVENT, helper -> {
            helper.register(TravelersTitlesCommon.id("biome"), SoundModule.BIOME);
            helper.register(TravelersTitlesCommon.id("dimension"), SoundModule.DIMENSION);
            helper.register(TravelersTitlesCommon.id("waystone"), SoundModule.WAYSTONE);
        });
    }
}
