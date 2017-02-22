package net.halalaboos.mcwrapper.api;

import net.halalaboos.mcwrapper.api.registry.ItemRegistry;
import net.halalaboos.mcwrapper.api.world.World;

/**
 * A MinecraftAdapter is, in simple terms, the 'heart' of any implementation of the MCWrapper.
 */
public interface MinecraftAdapter {

	/**
	 * The Minecraft version the adapter is made for.
	 * <p>Recommended format(s):</p>
	 * <ul>
	 *     <li>For a 'full' release of Minecraft such as 1.10, use "1.10".</li>
	 *     <li>For a 'beta' release of Minecraft such as Beta 1.7.3, use "b1.7.3".</li>
	 *     <li>For an 'alpha' release of Minecraft such as Alpha 1.2.6, use "a1.2.6".</li>
	 * </ul>
	 */
	String getMinecraftVersion();

	/**
	 * Performed when the world is set.  This is mainly just for testing and will most likely be removed at some point.
	 */
	void setWorld(World world);

	/**
	 * @return The {@link MinecraftClient} instance.
	 */
	MinecraftClient getMinecraft();

	ItemRegistry getItemRegistry();
}
