package net.halalaboos.huzuni.mc.mixin;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.launch.MixinTweaker;
import org.spongepowered.asm.mixin.Mixins;

/**
 * Our {@link MixinLoader} class doesn't seem to work properly through a development environment, so we will use this
 * as our tweak class instead when we're in that context.
 *
 * Compiled versions of the client will use {@link MixinLoader} without any trouble.
 */
public class MixinLoaderDebug extends MixinTweaker {

	public MixinLoaderDebug() {
		MixinBootstrap.init();
		Mixins.addConfiguration("mixins.mcwrapper.json");
	}
}
