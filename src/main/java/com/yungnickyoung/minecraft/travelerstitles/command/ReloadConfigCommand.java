package com.yungnickyoung.minecraft.travelerstitles.command;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import com.yungnickyoung.minecraft.travelerstitles.compat.WaystonesCompat;
import com.yungnickyoung.minecraft.travelerstitles.config.TTConfig;
import com.yungnickyoung.minecraft.travelerstitles.init.TTModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Loader;

public class ReloadConfigCommand extends CommandBase {
    @Override
    public String getName() {
        return "tt_reload";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/tt_reload";
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
        Minecraft.getMinecraft().addScheduledTask(() -> {
            TTConfig.loadFromDisk();
            TTModConfig.reloadConfig();
            if (TravelersTitles.titleManager != null) {
                TravelersTitles.titleManager.biomeTitleRenderer.clearTimer();
                TravelersTitles.titleManager.biomeTitleRenderer.recentEntries.clear();
                TravelersTitles.titleManager.dimensionTitleRenderer.clearTimer();
                TravelersTitles.titleManager.dimensionTitleRenderer.recentEntries.clear();
            }
            if (Loader.isModLoaded("waystones")) {
                WaystonesCompat.reset();
            }
            sender.sendMessage(new TextComponentString("Loading changes from Traveler's Titles config..."));
        });
    }
}
