package com.yungnickyoung.minecraft.travelerstitles.neoforge.mixin;

import com.yungnickyoung.minecraft.travelerstitles.neoforge.TravelersTitlesAnimationSharedStateHelper;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.neoforged.neoforge.client.entity.animation.json.AnimationLoader;
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
            // 26.1: first SharedState#get in reload is AtlasManager.PENDING_STITCH; animation state is the next get.
            ordinal = 1
        )
    )
    private Object travelerstitles_redirectAnimationSharedGet(
        PreparableReloadListener.SharedState state,
        PreparableReloadListener.StateKey<?> key
    ) {
        if (key != AnimationLoader.STATE_KEY) {
            return state.get(key);
        }
        return TravelersTitlesAnimationSharedStateHelper.resolveAnimationPendingForGet(state, key);
    }
}
