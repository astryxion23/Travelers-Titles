package com.yungnickyoung.minecraft.travelerstitles.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigWaystones {
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
    public final TTConfig.IntValue recentWaystoneCacheSize;
    public final TTConfig.BoolValue centerText;
    public final TTConfig.BoolValue resetWaystoneCacheOnDimensionChange;
    public final TTConfig.IntValue range;
    public final TTConfig.BoolValue waystonesOverrideBiomeTitle;
    public final TTConfig.BoolValue onlyUpdateAtSurface;

    public ConfigWaystones(final Configuration cfg) {
        final String c = "Waystone Titles";

        enabled = new TTConfig.BoolValue(cfg.get(c, "Enable Waystone Titles", true));

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
            " The minimum amount of time in ticks that must pass after a Waystone title is displayed before\n" +
                " another can be displayed.\n" +
                " Useful for preventing the player from being spammed if they are traveling quickly.\n" +
                " 20 ticks = 1 second.\n" +
                " Default: 80",
            0, Integer.MAX_VALUE));

        textColor = new TTConfig.StrValue(cfg.get(c, "Default Text Color", "c2b740",
            " The text's default RGB color.\n" +
                " Default: \"c2b740\""));

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

        recentWaystoneCacheSize = new TTConfig.IntValue(cfg.get(c, "Number of Most Recent Waystones Saved", 3,
            " Traveler's Titles tracks a list of Waystones the player most recently visited in order to\n" +
                " prevent the player from being spammed with titles when they move between the same few Waystones.\n" +
                " This is the size of that list.\n" +
                " For example, if this value is 5, then your 5 most recent Waystones will be saved.\n" +
                " Default: 3",
            0, Integer.MAX_VALUE));

        centerText = new TTConfig.BoolValue(cfg.get(c, "Center Title", true,
            " Whether or not the Waystone text should be centered on the screen.\n" +
                " The Text X Offset and Text Y Offset options are relative to the center of the screen if this is enabled.\n" +
                " Default: true"));

        resetWaystoneCacheOnDimensionChange = new TTConfig.BoolValue(cfg.get(c, "Reset Waystone Cache When Changing Dimensions", true,
            " Traveler's Titles tracks a list of Waystones the player most recently visited in order to\n" +
                " prevent the player from being spammed with titles when they move between the same few Waystones.\n" +
                " This option determines whether or not that list should be cleared every time\n" +
                " the player changes dimensions.\n" +
                " Default: true"));

        range = new TTConfig.IntValue(cfg.get(c, "Waystone Title Range", 30,
            " The distance from a Waystone (in blocks) at which the Waystone's title will trigger.\n" +
                " Default: 30",
            1, Integer.MAX_VALUE));

        waystonesOverrideBiomeTitle = new TTConfig.BoolValue(cfg.get(c, "Waystone Titles Override Biome Titles", true,
            " Whether or not Waystone titles should override Biome titles.\n" +
                " That is, if a player enters the area for a Waystone while also entering a new biome,\n" +
                " the Waystone title will take precedence.\n" +
                " Default: true"));

        onlyUpdateAtSurface = new TTConfig.BoolValue(cfg.get(c, "Only Show Waystone Titles When Exposed To Skylight", false,
            " If enabled, dimensions without ceilings (like the Overworld) will only display Waystone titles when the player is exposed to the skylight.\n" +
                " This prevents Waystone titles from showing while the player is underground.\n" +
                " Default: false"));
    }
}
