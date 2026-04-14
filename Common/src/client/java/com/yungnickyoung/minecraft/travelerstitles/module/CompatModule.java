package com.yungnickyoung.minecraft.travelerstitles.module;

import com.yungnickyoung.minecraft.travelerstitles.services.ClientServices;
import com.yungnickyoung.minecraft.travelerstitles.services.Services;

public class CompatModule {
    public static boolean isWaystonesLoaded = false;

    public static void init() {
        if (Services.PLATFORM.isModLoaded("waystones") && Services.PLATFORM.getPlatformName().equals("NeoForge")) {
            ClientServices.WAYSTONES.init();
            isWaystonesLoaded = true;
        }
    }
}
