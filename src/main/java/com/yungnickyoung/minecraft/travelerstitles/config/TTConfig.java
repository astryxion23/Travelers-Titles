package com.yungnickyoung.minecraft.travelerstitles.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

public class TTConfig {
    public static Configuration configuration;

    public static ConfigBiomes biomes;
    public static ConfigDimensions dimensions;
    public static ConfigWaystones waystones;
    public static ConfigSound sound;

    public static void init(File configDir) {
        configuration = new Configuration(new File(configDir, "travelerstitles-forge-1_16.cfg"), "UTF-8");
        configuration.load();
        biomes = new ConfigBiomes(configuration);
        dimensions = new ConfigDimensions(configuration);
        waystones = new ConfigWaystones(configuration);
        sound = new ConfigSound(configuration);
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static void loadFromDisk() {
        if (configuration != null) {
            configuration.load();
        }
    }

    public static final class BoolValue {
        private final Property property;

        public BoolValue(Property property) {
            this.property = property;
        }

        public boolean get() {
            return property.getBoolean();
        }
    }

    public static final class IntValue {
        private final Property property;

        public IntValue(Property property) {
            this.property = property;
        }

        public int get() {
            return property.getInt();
        }
    }

    public static final class DoubleValue {
        private final Property property;

        public DoubleValue(Property property) {
            this.property = property;
        }

        public double get() {
            return property.getDouble();
        }
    }

    public static final class StrValue {
        private final Property property;

        public StrValue(Property property) {
            this.property = property;
        }

        public String get() {
            return property.getString();
        }
    }
}
