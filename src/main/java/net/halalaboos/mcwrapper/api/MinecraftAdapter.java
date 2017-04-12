package net.halalaboos.mcwrapper.api;

import net.halalaboos.mcwrapper.api.opengl.GLState;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.registry.*;
import net.halalaboos.mcwrapper.api.util.Builder;

import java.util.function.Supplier;

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
	 * @return The {@link MinecraftClient} instance.
	 */
	MinecraftClient getMinecraft();

	/**
	 * The version-specific {@link ItemRegistry} implementation.
	 */
	ItemRegistry getItemRegistry();

	/**
	 * The version-specific {@link BlockRegistry} implementation.
	 */
	BlockRegistry getBlockRegistry();

	/**
	 * The version-specific {@link PotionRegistry} implementation.
	 */
	PotionRegistry getPotionRegistry();

	EnchantmentRegistry getEnchantmentRegistry();

	EntityRegistry getEntityRegistry();

	/**
	 * The version-specific {@link GLState} implementation.
	 */
	GLState getGLStateManager();

	/**
	 * The adapter will need its own implementation of the various {@link Builder} classes; this will return the
	 * (registered) implementation of the 'base' builder class.
	 *
	 * <p>For example, if you look at {@link ItemStack#getBuilder()}, you will see that it points to this method, but
	 * doesn't specifically call the version-specific implementation of the builder - that is where this method comes
	 * in.</p>
	 *
	 * <p>This means that you wouldn't use <code>OnePointElevenItemStackBuilder</code> as a parameter here - you would
	 * use <code>ItemStack.Builder</code>.</p>
	 *
	 * <p>It will look through the registered Builder classes and return an instance of the implementation if it
	 * exists.</p>
	 *
	 * @param builder The base {@link Builder}
	 * @return The registered implementation of the specified {@link Builder}.
	 */
	<T extends Builder> T getBuilder(Class<? extends Builder> builder);

	/**
	 * All version-specific {@link Builder} implementations will need to be registered in the adapter, which will be
	 * done using this method.
	 *
	 * <p>For example, in the case of {@link ItemStack.Builder}, you will need to make your own implementation of that
	 * builder and then register it here like so:</p>
	 *
	 * <p><code>this.registerBuilder(ItemStack.Builder.class, ItemStackBuilder::new);</code></p>
	 *
	 * <p>In this case, this method will then add the <code>builder</code> and <code>instance</code> to a
	 * map containing the builders and instances.</p>
	 *
	 * @param builder The base builder
	 * @param instance The version-specific implementation
	 */
	<T> void registerBuilder(Class<T> builder, Supplier<? extends T> instance);
}
