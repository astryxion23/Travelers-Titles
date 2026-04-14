package com.yungnickyoung.minecraft.travelerstitles.neoforge;

import com.yungnickyoung.minecraft.travelerstitles.neoforge.mixin.SharedStateAccessor;
import java.util.Map;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.neoforged.neoforge.client.entity.animation.json.AnimationLoader;

/**
 * Resolves {@link AnimationLoader.PendingAnimations} when {@link PreparableReloadListener.StateKey} identity
 * differs between the patched game classpath and NeoForge (IdentityHashMap miss on {@code SharedState#get}).
 */
public final class TravelersTitlesAnimationSharedStateHelper {
    private TravelersTitlesAnimationSharedStateHelper() {}

    public static Object resolveAnimationPendingForGet(
        PreparableReloadListener.SharedState state,
        PreparableReloadListener.StateKey<?> requestedKey
    ) {
        Map<PreparableReloadListener.StateKey<?>, Object> map =
            ((SharedStateAccessor) (Object) state).travelerstitles$state();
        Object direct = map.get(requestedKey);
        if (direct instanceof AnimationLoader.PendingAnimations pa) {
            return pa;
        }
        for (Object value : map.values()) {
            if (value instanceof AnimationLoader.PendingAnimations pa && value != AnimationLoader.PendingAnimations.EMPTY) {
                map.put(requestedKey, pa);
                return pa;
            }
        }
        AnimationLoader.PendingAnimations empty = AnimationLoader.PendingAnimations.EMPTY;
        map.put(requestedKey, empty);
        return empty;
    }
}
