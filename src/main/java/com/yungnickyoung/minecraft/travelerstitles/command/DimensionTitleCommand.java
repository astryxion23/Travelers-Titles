package com.yungnickyoung.minecraft.travelerstitles.command;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import com.yungnickyoung.minecraft.travelerstitles.render.TitleRenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
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
        return "/dimensiontitle <stable-id|dimension-name|dimension-id>";
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

        String dimensionQuery = buildDimensionQuery(args);

        WorldServer targetWorld = null;

        Integer numericDimensionId = tryParseDimensionId(dimensionQuery);
        if (numericDimensionId != null) {
            targetWorld = DimensionManager.getWorld(numericDimensionId);
        }

        for (Integer dim : DimensionManager.getStaticDimensionIDs()) {
            if (targetWorld != null) {
                break;
            }

            WorldServer ws = DimensionManager.getWorld(dim);
            if (ws != null && matchesDimensionQuery(ws, dimensionQuery)) {
                targetWorld = ws;
                break;
            }
        }

        if (targetWorld == null) {
            throw new CommandException("commands.locatebiome.invalid", dimensionQuery);
        }

        final WorldServer selectedWorld = targetWorld;
        Minecraft.getMinecraft().addScheduledTask(() -> {
            if (TravelersTitles.titleManager == null) {
                return;
            }

            String stableDimensionNameKey = TitleRenderManager.getStableDimensionTranslationKey(selectedWorld);

            if (TitleRenderManager.isDimensionBlacklisted(selectedWorld)) {
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString("That dimension is blacklisted, so its title won't normally show!"));
            }

            ITextComponent dimensionTitle = TitleRenderManager.createDimensionTitleComponent(selectedWorld);

            String dimensionColorKey = stableDimensionNameKey + ".color";
            String dimensionColorStr = I18n.hasKey(dimensionColorKey)
                ? I18n.format(dimensionColorKey)
                : TravelersTitles.titleManager.dimensionTitleRenderer.titleDefaultTextColor;

            TravelersTitles.titleManager.dimensionTitleRenderer.setColor(dimensionColorStr);
            TravelersTitles.titleManager.dimensionTitleRenderer.displayTitle(dimensionTitle, null);
        });
    }

    private static String buildDimensionQuery(final String[] args) {
        final StringBuilder builder = new StringBuilder();
        for (String arg : args) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(arg);
        }
        return builder.toString().trim();
    }

    private static Integer tryParseDimensionId(final String dimensionQuery) {
        try {
            return Integer.parseInt(dimensionQuery);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private static boolean matchesDimensionQuery(final WorldServer world, final String dimensionQuery) {
        final String normalizedQuery = TitleRenderManager.normalizeTranslationKeyPart(dimensionQuery);
        final String rawDimensionName = world.provider.getDimensionType().getName();

        return TitleRenderManager.getStableDimensionId(world).equals(normalizedQuery)
            || TitleRenderManager.getStableDimensionTranslationKey(world).equalsIgnoreCase(dimensionQuery)
            || rawDimensionName.equalsIgnoreCase(dimensionQuery)
            || TitleRenderManager.normalizeTranslationKeyPart(rawDimensionName).equals(normalizedQuery);
    }
}
