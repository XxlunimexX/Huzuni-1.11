package net.halalaboos.mcwrapper.launch;

import net.halalaboos.mcwrapper.api.MCWrapper;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.launch.MixinTweaker;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Since we aren't relying on Forge anymore, this means we won't be having our Mixins load through Forge's coremod
 * classes.  Instead, we will be using the {@link ITweaker} class from the Minecraft launchwrapper library, which
 * works just about the same.
 */
public class MixinLoader implements ITweaker {

	/**
	 * We could be using the {@link MixinTweaker} class, but it strips the launch
	 * arguments by default, making it not work outside of a development environment.  These are the launch arguments.
	 */
	private ArrayList<String> args = new ArrayList<>();

	private boolean forge = false;

	@Override
	public void acceptOptions(List<String> list, File gameDir, File assetsDir, String profile) {
		args.addAll(list);
		if (args.contains("--ignoreme")) {
			forge = true;
			args.remove("--ignoreme");
		}
		if (!args.contains("--version") && profile != null) {
			this.args.add("--version");
			this.args.add(profile);
		}
		if (!args.contains("--assetsDir") && assetsDir != null) {
			this.args.add("--assetsDir");
			this.args.add(assetsDir.getPath());
		}
		if (!args.contains("--gameDir") && gameDir != null) {
			this.args.add("--gameDir");
			this.args.add(gameDir.getPath());
		}
	}

	@Override
	public void injectIntoClassLoader(LaunchClassLoader classLoader) {
		MixinBootstrap.init();
		/*
		  Check for Forge's GuiIngame class.  If it is found, then we will tell the Mixin library to use the searge
		  obfuscation context, since Forge jars are obfuscated differently.
		 */
		Mixins.addConfiguration("mixins.mcwrapper.json");

		if (hasForge()) {
			MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
			System.out.println("Forge found!");
		} else {
			System.out.println("Forge not found!");
			MCWrapper.IS_USING_FORGE = false;
		}
		MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
	}

	private boolean hasForge() {
		for (IClassTransformer transformer : Launch.classLoader.getTransformers()) {
			if (transformer.getClass().getName().contains("fml")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The target class we will be launching.  In this case, it is Minecraft's Main class.
	 */
	@Override
	public String getLaunchTarget() {
		return "net.minecraft.client.main.Main";
	}

	/**
	 * We will use our {@link #args} for the launch arguments.
	 */
	@Override
	public String[] getLaunchArguments() {
		return forge ? new String[0] : args.toArray(new String[args.size()]);
	}
}
