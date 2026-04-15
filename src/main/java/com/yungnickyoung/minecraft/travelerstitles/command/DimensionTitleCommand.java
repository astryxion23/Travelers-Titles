package com.yungnickyoung.minecraft.travelerstitles.command;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
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
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class DimensionTitleCommand extends CommandBase {
    @Override
    public String getName() {
        return "dimensiontitle";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/dimensiontitle <namespace:path>";
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
        ResourceLocation dimensionId = new ResourceLocation(args[0]);

        WorldServer targetWorld = null;
        for (Integer dim : DimensionManager.getStaticDimensionIDs()) {
            WorldServer ws = DimensionManager.getWorld(dim);
            if (ws != null && TitleRenderManager.getDimensionResourceLocation(ws).equals(dimensionId)) {
                targetWorld = ws;
                break;
            }
        }

        if (targetWorld == null) {
            throw new CommandException("commands.locatebiome.invalid", dimensionId);
        }

        final ResourceLocation dimensionBaseKey = TitleRenderManager.getDimensionResourceLocation(targetWorld);
        Minecraft.getMinecraft().addScheduledTask(() -> {
            if (TravelersTitles.titleManager == null) {
                return;
            }

            String dimensionNameKey = TitleRenderManager.makeTranslationKey(TravelersTitles.MOD_ID, dimensionBaseKey);

            if (TravelersTitles.titleManager.blacklistedDimensions.contains(dimensionBaseKey.toString())) {
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString("That dimension is blacklisted, so its title won't normally show!"));
            }

            ITextComponent dimensionTitle = TitleRenderManager.createDimensionTitleComponent(dimensionBaseKey);

            String dimensionColorKey = dimensionNameKey + ".color";
            String dimensionColorStr = I18n.hasKey(dimensionColorKey)
                ? I18n.format(dimensionColorKey)
                : TravelersTitles.titleManager.dimensionTitleRenderer.titleDefaultTextColor;

            TravelersTitles.titleManager.dimensionTitleRenderer.setColor(dimensionColorStr);
            TravelersTitles.titleManager.dimensionTitleRenderer.displayTitle(dimensionTitle, null);
        });
    }
}
