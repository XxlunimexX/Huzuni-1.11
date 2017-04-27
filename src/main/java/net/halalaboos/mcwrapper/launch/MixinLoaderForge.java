package net.halalaboos.mcwrapper.launch;

import java.io.File;
import java.util.List;

/**
 * Our {@link MixinLoader} class doesn't seem to work properly through a development environment, so we will use this
 * as our tweak class instead when we're in that context.
 *
 * Compiled versions of the client will use {@link MixinLoader} without any trouble.
 */
public class MixinLoaderForge extends MixinLoader {

	@Override
	public void acceptOptions(List<String> list, File gameDir, File assetsDir, String profile) {}

	@Override
	public String[] getLaunchArguments() {
		return new String[0];
	}
}
