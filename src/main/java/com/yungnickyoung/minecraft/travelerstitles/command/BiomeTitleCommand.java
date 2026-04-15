package com.yungnickyoung.minecraft.travelerstitles.command;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfig;
import com.yungnickyoung.minecraft.travelerstitles.render.TitleRenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.biome.Biome;

public class BiomeTitleCommand extends CommandBase {
    @Override
    public String getName() {
        return "biometitle";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/biometitle <namespace:path>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (server.isDedicatedServer()) {
            return;
        }
        if (args.length < 1) {
            throw new WrongUsageException(getUsage(sender));
        }
        ResourceLocation biomeId = new ResourceLocation(args[0]);
        if (Biome.REGISTRY.getObject(biomeId) == null) {
            throw new CommandException("commands.locatebiome.invalid", biomeId);
        }

        Minecraft.getMinecraft().addScheduledTask(() -> {
            Biome biome = Biome.REGISTRY.getObject(biomeId);
            if (biome == null || TravelersTitles.titleManager == null) {
                return;
            }

            ResourceLocation biomeBaseKey = Biome.REGISTRY.getNameForObject(biome);
            if (biomeBaseKey == null) {
                return;
            }

            String overrideBiomeNameKey = TitleRenderManager.makeTranslationKey(TravelersTitles.MOD_ID + ".biome", biomeBaseKey);
            String normalBiomeNameKey = TitleRenderManager.makeTranslationKey("biome", biomeBaseKey);

            if (TravelersTitles.titleManager.blacklistedBiomes.contains(biomeBaseKey.toString())) {
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString("That biome is blacklisted, so its title won't normally show!"));
            }

            ITextComponent biomeTitle = TitleRenderManager.createBiomeTitleComponent(biome, biomeBaseKey);

            String overrideBiomeColorKey = overrideBiomeNameKey + ".color";
            String normalBiomeColorKey = normalBiomeNameKey + ".color";
            String biomeColorStr;
            if (I18n.hasKey(overrideBiomeColorKey)) {
                biomeColorStr = I18n.format(overrideBiomeColorKey);
            } else if (I18n.hasKey(normalBiomeColorKey)) {
                biomeColorStr = I18n.format(normalBiomeColorKey);
            } else {
                biomeColorStr = TravelersTitles.titleManager.biomeTitleRenderer.titleDefaultTextColor;
            }

            TravelersTitles.titleManager.biomeTitleRenderer.setColor(biomeColorStr);
            TravelersTitles.titleManager.biomeTitleRenderer.displayTitle(biomeTitle, null);
            TravelersTitles.titleManager.biomeTitleRenderer.cooldownTimer = TTConfig.biomes.textCooldownTime.get();
        });
    }
}
