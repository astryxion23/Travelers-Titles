package com.yungnickyoung.minecraft.travelerstitles.neoforge.mixin;

import java.util.Map;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PreparableReloadListener.SharedState.class)
public interface SharedStateAccessor {
    @Accessor("state")
    Map<PreparableReloadListener.StateKey<?>, Object> travelerstitles$state();
}
