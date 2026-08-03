package com.yungnickyoung.minecraft.travelerstitles.services;

import com.yungnickyoung.minecraft.travelerstitles.module.CompatModule;

public class NeoForgeModulesLoader implements IModulesLoader {
    @Override
    public void loadModules() {
        IModulesLoader.super.loadModules();
        CompatModule.init();
    }
}
