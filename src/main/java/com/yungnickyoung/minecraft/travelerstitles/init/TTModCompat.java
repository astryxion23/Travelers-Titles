package com.yungnickyoung.minecraft.travelerstitles.init;

import com.yungnickyoung.minecraft.travelerstitles.compat.WaystonesCompat;
import net.minecraftforge.fml.common.Loader;

public class TTModCompat {
    public static void init() {
        if (Loader.isModLoaded("waystones")) {
            WaystonesCompat.init();
        }
    }
}
