package com.yungnickyoung.minecraft.travelerstitles.module;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class ConfigModule {
    public final Biomes biomes = new Biomes();
    public final Dimensions dimensions = new Dimensions();
    public final Sound sound = new Sound();
    public final Waystones waystones = new Waystones();

    public static class Biomes {
        public boolean enabled = true;
        public int textFadeInTime = 10;
        public int textDisplayTime = 50;
        public int textFadeOutTime = 10;
        public int textCooldownTime = 80;
        public String textColor = "ffffff";
        public double textSize = 2.1;
        public boolean renderShadow = true;
        public int textYOffset = -33;
        public int textXOffset = 0;
        public List<String> biomeBlacklist = Lists.newArrayList("minecraft:the_end", "minecraft:river", "minecraft:frozen_river");
        public int recentBiomeCacheSize = 5;
        public boolean centerText = true;
        public boolean resetBiomeCacheOnDimensionChange = true;
        public boolean onlyUpdateAtSurface = true;
    }

    public static class Dimensions {
        public boolean enabled = true;
        public int textFadeInTime = 10;
        public int textDisplayTime = 70;
        public int textFadeOutTime = 20;
        public String textColor = "ffffff";
        public double textSize = 3.0;
        public boolean renderShadow = true;
        public int textYOffset = -32;
        public int textXOffset = 0;
        public List<String> dimensionBlacklist = new ArrayList<>();
        public boolean centerText = true;
        public boolean onlyUpdateAtSurface = false;
    }

    public static class Sound {
        public double biomeVolume = 1.0;
        public double biomePitch = 1.0;
        public double dimensionVolume = 1.0;
        public double dimensionPitch = 1.0;
        public double waystoneVolume = 1.0;
        public double waystonePitch = 1.0;
    }

    public static class Waystones {
        public boolean enabled = true;
        public int textFadeInTime = 10;
        public int textDisplayTime = 50;
        public int textFadeOutTime = 10;
        public int textCooldownTime = 80;
        public String textColor = "c2b740";
        public double textSize = 2.1;
        public boolean renderShadow = true;
        public int textYOffset = -33;
        public int textXOffset = 0;
        public int recentWaystoneCacheSize = 3;
        public boolean centerText = true;
        public boolean resetWaystoneCacheOnDimensionChange = true;
        public int range = 30;
        public boolean waystonesOverrideBiomeTitle = true;
        public boolean onlyUpdateAtSurface = false;
    }
}
