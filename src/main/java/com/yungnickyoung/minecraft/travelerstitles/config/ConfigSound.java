package com.yungnickyoung.minecraft.travelerstitles.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigSound {
    public final TTConfig.DoubleValue biomeVolume;
    public final TTConfig.DoubleValue biomePitch;
    public final TTConfig.DoubleValue dimensionVolume;
    public final TTConfig.DoubleValue dimensionPitch;
    public final TTConfig.DoubleValue waystoneVolume;
    public final TTConfig.DoubleValue waystonePitch;

    public ConfigSound(final Configuration cfg) {
        final String c = "Custom Sound Settings";

        biomeVolume = new TTConfig.DoubleValue(cfg.get(c, "Biome Sound Effect Volume", 1.0,
            " The volume of the sound that plays when a biome title displays.\n" +
                " Default: 1.0",
            0.0d, 10.0d));

        biomePitch = new TTConfig.DoubleValue(cfg.get(c, "Biome Sound Effect Pitch", 1.0,
            " The pitch of the sound that plays when a biome title displays.\n" +
                " Default: 1.0",
            0.0d, 10.0d));

        dimensionVolume = new TTConfig.DoubleValue(cfg.get(c, "Dimension Sound Effect Volume", 1.0,
            " The volume of the sound that plays when a dimension title displays.\n" +
                " Default: 1.0",
            0.0d, 10.0d));

        dimensionPitch = new TTConfig.DoubleValue(cfg.get(c, "Dimension Sound Effect Pitch", 1.0,
            " The pitch of the sound that plays when a dimension title displays.\n" +
                " Default: 1.0",
            0.0d, 10.0d));

        waystoneVolume = new TTConfig.DoubleValue(cfg.get(c, "Waystone Sound Effect Volume", 1.0,
            " The volume of the sound that plays when a Waystone title displays.\n" +
                " The Waystones mod must be installed for this to have any effect.\n" +
                " Default: 1.0",
            0.0d, 10.0d));

        waystonePitch = new TTConfig.DoubleValue(cfg.get(c, "Waystone Sound Effect Pitch", 1.0,
            " The pitch of the sound that plays when a Waystone title displays.\n" +
                " The Waystones mod must be installed for this to have any effect.\n" +
                " Default: 1.0",
            0.0d, 10.0d));
    }
}
