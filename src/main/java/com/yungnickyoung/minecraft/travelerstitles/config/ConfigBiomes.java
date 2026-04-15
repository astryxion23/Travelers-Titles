package com.yungnickyoung.minecraft.travelerstitles.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigBiomes {
    public final TTConfig.BoolValue enabled;
    public final TTConfig.IntValue textFadeInTime;
    public final TTConfig.IntValue textDisplayTime;
    public final TTConfig.IntValue textFadeOutTime;
    public final TTConfig.IntValue textCooldownTime;
    public final TTConfig.StrValue textColor;
    public final TTConfig.DoubleValue textSize;
    public final TTConfig.BoolValue renderShadow;
    public final TTConfig.DoubleValue textYOffset;
    public final TTConfig.DoubleValue textXOffset;
    public final TTConfig.StrValue biomeBlacklist;
    public final TTConfig.IntValue recentBiomeCacheSize;
    public final TTConfig.BoolValue centerText;
    public final TTConfig.BoolValue resetBiomeCacheOnDimensionChange;
    public final TTConfig.BoolValue onlyUpdateAtSurface;

    public ConfigBiomes(final Configuration cfg) {
        final String c = "Biome Titles";

        enabled = new TTConfig.BoolValue(cfg.get(c, "Enable Biome Titles", true));

        textFadeInTime = new TTConfig.IntValue(cfg.get(c, "Text Fade-In Time", 10,
            " How long the fade-in text effect lasts, in ticks.\n" +
                " 20 ticks = 1 second.\n" +
                " Default: 10",
            0, Integer.MAX_VALUE));

        textDisplayTime = new TTConfig.IntValue(cfg.get(c, "Text Display Time", 50,
            " How long the text displays, in ticks.\n" +
                " 20 ticks = 1 second.\n" +
                " Default: 50",
            0, Integer.MAX_VALUE));

        textFadeOutTime = new TTConfig.IntValue(cfg.get(c, "Text Fade-Out Time", 10,
            " How long the fade-out text effect lasts, in ticks.\n" +
                " 20 ticks = 1 second.\n" +
                " Default: 10",
            0, Integer.MAX_VALUE));

        textCooldownTime = new TTConfig.IntValue(cfg.get(c, "Text Cooldown Time", 80,
            " The minimum amount of time in ticks that must pass after a biome title is displayed before\n" +
                " another can be displayed.\n" +
                " Useful for preventing the player from being spammed if they are traveling quickly.\n" +
                " 20 ticks = 1 second.\n" +
                " Default: 80",
            0, Integer.MAX_VALUE));

        textColor = new TTConfig.StrValue(cfg.get(c, "Default Text Color", "ffffff",
            " The text's default RGB color.\n" +
                " Default: \"ffffff\""));

        textSize = new TTConfig.DoubleValue(cfg.get(c, "Text Size", 2.1,
            " The text's scale.\n" +
                " Default: 2.1",
            0.01d, 1000d));

        renderShadow = new TTConfig.BoolValue(cfg.get(c, "Display Text Shadow", true,
            " If enabled, will render a shadow below the text making it easier to read.\n" +
                " Default: true"));

        textYOffset = new TTConfig.DoubleValue(cfg.get(c, "Text Y Offset", -33.0,
            " The text's vertical position on the screen.\n" +
                " If Horizontally Center Title is enabled, this number is relative to the center of the screen.\n" +
                " If Horizontally Center Title is disabled, this number is relative to the top of the screen.\n" +
                " Default: -33.0",
            -10000d, 10000d));

        textXOffset = new TTConfig.DoubleValue(cfg.get(c, "Text X Offset", 0.0,
            " The text's horizontal position on the screen.\n" +
                " If Horizontally Center Title is enabled, this number is relative to the center of the screen.\n" +
                " If Horizontally Center Title is disabled, this number is relative to the left side of the screen.\n" +
                " Default: 0.0",
            -10000d, 10000d));

        biomeBlacklist = new TTConfig.StrValue(cfg.get(c, "Blacklisted Biomes", "[minecraft:the_end, minecraft:river, minecraft:frozen_river]",
            " Biomes that should not have any title displayed when the player enters them.\n" +
                " Example: \"[minecraft:plains, minecraft:desert]\"\n" +
                " Default: \"[minecraft:the_end, minecraft:river, minecraft:frozen_river]\""));

        recentBiomeCacheSize = new TTConfig.IntValue(cfg.get(c, "Number of Most Recent Biomes Saved", 5,
            " Traveler's Titles tracks a list of biomes the player most recently visited in order to\n" +
                " prevent the player from being spammed with titles when they move between the same few biomes.\n" +
                " This is the size of that list.\n" +
                " For example, if this value is 5, then your 5 most recent biomes will be saved.\n" +
                " Default: 5",
            0, Integer.MAX_VALUE));

        centerText = new TTConfig.BoolValue(cfg.get(c, "Center Title", true,
            " Whether or not the biome text should be centered on the screen.\n" +
                " The Text X Offset and Text Y Offset options are relative to the center of the screen if this is enabled.\n" +
                " Default: true"));

        resetBiomeCacheOnDimensionChange = new TTConfig.BoolValue(cfg.get(c, "Reset Biome Cache When Changing Dimensions", true,
            " Traveler's Titles tracks a list of biomes the player most recently visited in order to\n" +
                " prevent the player from being spammed with titles when they move between the same few biomes.\n" +
                " This option determines whether or not that list should be cleared every time\n" +
                " the player changes dimensions.\n" +
                " Default: true"));

        onlyUpdateAtSurface = new TTConfig.BoolValue(cfg.get(c, "Only Show Biome Titles When Exposed To Skylight", true,
            " If enabled, dimensions without ceilings (like the Overworld) will only display biome titles when the player is exposed to the skylight.\n" +
                " This prevents biome titles from showing while the player is underground.\n" +
                " Default: true"));
    }
}
