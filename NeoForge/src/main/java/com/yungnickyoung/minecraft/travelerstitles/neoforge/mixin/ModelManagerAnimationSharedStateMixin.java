package com.yungnickyoung.minecraft.travelerstitles.neoforge.mixin;

import com.yungnickyoung.minecraft.travelerstitles.neoforge.TravelersTitlesAnimationSharedStateHelper;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModelManager.class)
public class ModelManagerAnimationSharedStateMixin {
    @Redirect(
        method = "reload",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/packs/resources/PreparableReloadListener$SharedState;get(Lnet/minecraft/server/packs/resources/PreparableReloadListener$StateKey;)Ljava/lang/Object;",
            ordinal = 0
        )
    )
    private Object travelerstitles_redirectFirstSharedGet(
        PreparableReloadListener.SharedState state,
        PreparableReloadListener.StateKey<?> key
    ) {
        return TravelersTitlesAnimationSharedStateHelper.resolveAnimationPendingForGet(state, key);
    }
}
