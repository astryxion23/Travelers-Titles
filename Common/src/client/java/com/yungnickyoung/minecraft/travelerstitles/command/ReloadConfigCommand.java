package com.yungnickyoung.minecraft.travelerstitles.command;

import com.mojang.brigadier.CommandDispatcher;
import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesClient;
import com.yungnickyoung.minecraft.travelerstitles.module.CompatModule;
import com.yungnickyoung.minecraft.travelerstitles.services.ClientServices;
import com.yungnickyoung.minecraft.travelerstitles.services.Services;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.PermissionProviderCheck;

public class ReloadConfigCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection environment) {
        dispatcher.register(Commands
            .literal("tt_reload")
            .requires(source -> new PermissionProviderCheck(Commands.LEVEL_GAMEMASTERS).test(source))
            .executes(ctx -> reloadConfig(ctx.getSource())));
    }

    public static int reloadConfig(CommandSourceStack commandSource) {
        Services.CONFIG_RELOADER.reloadConfig();
        TravelersTitlesClient.TITLE_MANAGER.biomeTitleRenderer.clearTimer();
        TravelersTitlesClient.TITLE_MANAGER.biomeTitleRenderer.recentEntries.clear();
        TravelersTitlesClient.TITLE_MANAGER.dimensionTitleRenderer.clearTimer();
        TravelersTitlesClient.TITLE_MANAGER.dimensionTitleRenderer.recentEntries.clear();
        if (CompatModule.isWaystonesLoaded) {
            ClientServices.WAYSTONES.reset();
        }
        commandSource.sendSuccess(() -> Component.literal("Loading changes from Traveler's Titles config..."), false);
        return 1;
    }
}
