package com.yungnickyoung.minecraft.travelerstitles.config.gui;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfigFabric;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigManager;
import me.shedaniel.autoconfig.gui.ConfigScreenProvider;
import me.shedaniel.autoconfig.gui.DefaultGuiProviders;
import me.shedaniel.autoconfig.gui.DefaultGuiTransformers;
import me.shedaniel.autoconfig.gui.registry.ComposedGuiRegistryAccess;
import me.shedaniel.autoconfig.gui.registry.DefaultGuiRegistryAccess;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class TTModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new ConfigScreenProvider<>(
            (ConfigManager<TTConfigFabric>) (Object) AutoConfig.getConfigHolder(TTConfigFabric.class),
            new ComposedGuiRegistryAccess(
                new GuiRegistry(),
                DefaultGuiTransformers.apply(DefaultGuiProviders.apply(new GuiRegistry())),
                new DefaultGuiRegistryAccess()
            ),
            parent
        ).get();
    }
}
