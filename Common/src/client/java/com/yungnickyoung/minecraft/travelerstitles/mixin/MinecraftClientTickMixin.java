package com.yungnickyoung.minecraft.travelerstitles.mixin;

import com.yungnickyoung.minecraft.travelerstitles.TravelersTitlesClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftClientTickMixin {
    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void onClientTick(CallbackInfo ci) {
        TravelersTitlesClient.TITLE_MANAGER.clientTick();
    }
}
