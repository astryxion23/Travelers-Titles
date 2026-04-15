package net.minecraftforge.fml.relauncher;

/**
 * Dev classpath shim: some published Forge 1.12.2 userdev jars ship a {@code Side} enum that
 * includes an extra {@code BUKKIT} constant, while {@link net.minecraftforge.fml.common.network.NetworkRegistry}
 * only seeds {@code CLIENT}/{@code SERVER} maps. {@code NetworkRegistry.newChannel} iterates
 * {@code Side.values()} and NPEs on the missing map entry. The public Forge sources only define
 * {@code CLIENT} and {@code SERVER}; this copy matches that contract so FML networking initializes.
 */
public enum Side {
    CLIENT,
    SERVER;

    public boolean isServer() {
        return !this.isClient();
    }

    public boolean isClient() {
        return this == CLIENT;
    }
}
