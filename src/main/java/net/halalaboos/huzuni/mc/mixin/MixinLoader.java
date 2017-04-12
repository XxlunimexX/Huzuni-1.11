package net.halalaboos.huzuni.mc.mixin;

import net.halalaboos.mcwrapper.api.MCWrapper;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MixinLoader implements ITweaker {

	private final List<String> args = new ArrayList<>();

	@Override
	public void acceptOptions(List<String> list, File gameDir, File assetsDir, String profile) {
		args.addAll(list);
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
		Mixins.addConfiguration("mixins.mcwrapper.json");
		/*
		  Check for Forge's GuiIngame class.  If it is found, then we will tell the Mixin library to use the searge
		  obfuscation context, since Forge jars are obfuscated differently.
		 */
		try {
			Class.forName("net.minecraftforge.client.GuiIngameForge");
			MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
		} catch (ClassNotFoundException e) {
			System.out.println("Forge not found!");
			MCWrapper.IS_USING_FORGE = false;
			e.printStackTrace();
		}
	}

	@Override
	public String getLaunchTarget() {
		return "net.minecraft.client.main.Main";
	}

	@Override
	public String[] getLaunchArguments() {
		return args.toArray(new String[args.size()]);
	}
}
