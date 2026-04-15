package com.yungnickyoung.minecraft.travelerstitles.init;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;

// Sound events must register on the common registry path; a client-only subscriber can miss
// RegistryEvent.Register<SoundEvent> and leave events without sounds.json bindings (vanilla warns).
@Mod.EventBusSubscriber(modid = TravelersTitles.MOD_ID)
public class TTModSound {
    public static final SoundEvent BIOME = createSoundEvent("biome");
    public static final SoundEvent DIMENSION = createSoundEvent("dimension");
    public static final SoundEvent WAYSTONE = createSoundEvent("waystone");

    public static void init() {
        // Registration happens via @SubscribeEvent on this class.
    }

    private static SoundEvent createSoundEvent(final String soundName) {
        final ResourceLocation id = new ResourceLocation(TravelersTitles.MOD_ID, soundName);
        return new SoundEvent(id).setRegistryName(id);
    }

    @SubscribeEvent
    public static void registerSoundEvents(final RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(BIOME);
        event.getRegistry().register(DIMENSION);
        event.getRegistry().register(WAYSTONE);
    }
}
