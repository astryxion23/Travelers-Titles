package com.yungnickyoung.minecraft.travelerstitles.render;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.function.Predicate;

public class TitleRenderer<T> {
    public final LinkedList<T> recentEntries = new LinkedList<>();
    public ITextComponent displayedTitle = null;
    public ITextComponent displayedSubTitle = null;
    public int titleTimer = 0;
    public int cooldownTimer = 0;

    // User-customizable text effects
    public int maxRecentListSize;
    public boolean enabled;
    public int titleFadeInTicks;
    public int titleDisplayTime;
    public int titleFadeOutTicks;
    public int titleTextcolor;
    public String titleDefaultTextColor;
    public boolean showTextShadow;
    public float titleTextSize;
    public float titleXOffset;
    public float titleYOffset;
    public boolean isTextCentered;

    public TitleRenderer(
        int maxRecentListSize,
        boolean enabled,
        int fadeInTicks,
        int displayTicks,
        int fadeOutTicks,
        String textColor,
        boolean showTextShadow,
        double textSize,
        double xOffset,
        double yOffset,
        boolean centerText
    ) {
        this.maxRecentListSize = maxRecentListSize;
        this.enabled = enabled;
        this.titleFadeInTicks = fadeInTicks;
        this.titleDisplayTime = displayTicks;
        this.titleFadeOutTicks = fadeOutTicks;
        this.setColor(textColor);
        this.titleDefaultTextColor = textColor;
        this.showTextShadow = showTextShadow;
        this.titleTextSize = (float) textSize;
        this.titleXOffset = (float) xOffset;
        this.titleYOffset = (float) yOffset;
        this.isTextCentered = centerText;
    }

    @SuppressWarnings("deprecation")
    public void renderText(float partialTicks) {
        if (displayedTitle != null && titleTimer > 0) {
            float age = (float) titleTimer - partialTicks;
            int opacity = 255;
            if (titleTimer > titleFadeOutTicks + titleDisplayTime) {
                float r = (float) (titleFadeInTicks + titleDisplayTime + titleFadeOutTicks) - age;
                opacity = (int) (r * 255.0F / (float) titleFadeInTicks);
            }

            if (titleTimer <= titleFadeOutTicks) {
                opacity = (int) (age * 255.0F / (float) titleFadeOutTicks);
            }

            opacity = MathHelper.clamp(opacity, 0, 255);
            if (opacity > 8) {
                Minecraft mc = Minecraft.getMinecraft();
                ScaledResolution sr = new ScaledResolution(mc);
                GlStateManager.pushMatrix();
                if (this.isTextCentered) {
                    GlStateManager.translate(sr.getScaledWidth() / 2f, sr.getScaledHeight() / 2f, 0.0F);
                }
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

                GlStateManager.pushMatrix();
                GlStateManager.scale(titleTextSize, titleTextSize, titleTextSize);
                int alpha = opacity << 24 & 0xFF000000;
                FontRenderer fontRenderer = mc.fontRenderer;
                int titleWidth = fontRenderer.getStringWidth(displayedTitle.getFormattedText());

                float xOffset = this.isTextCentered
                    ? this.titleXOffset + (float) (-titleWidth / 2)
                    : this.titleXOffset;

                if (showTextShadow) {
                    fontRenderer.drawString(displayedTitle.getFormattedText(), (int) xOffset, (int) titleYOffset, titleTextcolor | alpha, true);
                } else {
                    fontRenderer.drawString(displayedTitle.getFormattedText(), (int) xOffset, (int) titleYOffset, titleTextcolor | alpha, false);
                }
                GlStateManager.popMatrix();

                if (displayedSubTitle != null) {
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(1.3F, 1.3F, 1.3F);
                    int subtitleWidth = fontRenderer.getStringWidth(displayedSubTitle.getFormattedText());
                    if (showTextShadow) {
                        fontRenderer.drawString(displayedSubTitle.getFormattedText(), -subtitleWidth / 2, -35, 0xFFFFFF | alpha, true);
                    } else {
                        fontRenderer.drawString(displayedSubTitle.getFormattedText(), -subtitleWidth / 2, -35, 0xFFFFFF | alpha, false);
                    }
                    GlStateManager.popMatrix();
                }

                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }
    }

    public void tick() {
        if (titleTimer > 0) {
            --titleTimer;
            if (titleTimer <= 0) {
                clearTimer();
            }
        }
        if (cooldownTimer > 0) {
            --cooldownTimer;
        }
    }

    public void displayTitle(ITextComponent titleText, @Nullable ITextComponent subtitleText) {
        displayedTitle = titleText;
        titleTimer = titleFadeInTicks + titleDisplayTime + titleFadeOutTicks;
        if (subtitleText != null) {
            displayedSubTitle = subtitleText;
        }
    }

    public void clearTimer() {
        titleTimer = 0;
    }

    public void setColor(String textColor) {
        try {
            this.titleTextcolor = (int) Long.parseLong(textColor, 16);
        } catch (Exception e) {
            TravelersTitles.LOGGER.error("Text color {} is not a valid RGB color. Defaulting to white...", textColor);
            TravelersTitles.LOGGER.error(e.toString());
            this.titleTextcolor = 0xFFFFFF;
        }
    }

    public void addRecentEntry(T entry) {
        if (this.recentEntries.size() >= this.maxRecentListSize && this.recentEntries.size() > 0) {
            this.recentEntries.removeFirst();
        }
        if (this.maxRecentListSize > 0) {
            recentEntries.addLast(entry);
        }
    }

    public boolean containsEntry(Predicate<T> entryMatchPredicate) {
        return this.recentEntries.stream().anyMatch(entryMatchPredicate);
    }
}
