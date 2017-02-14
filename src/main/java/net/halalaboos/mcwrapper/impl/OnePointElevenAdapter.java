package net.halalaboos.mcwrapper.impl;

import net.halalaboos.mcwrapper.api.MinecraftAdapter;
import net.halalaboos.mcwrapper.api.entity.living.player.ClientPlayer;
import net.halalaboos.mcwrapper.api.util.Resolution;
import net.halalaboos.mcwrapper.api.world.World;
import net.minecraft.client.Minecraft;

public class OnePointElevenAdapter implements MinecraftAdapter {

	private Minecraft mc;
	private World world;

	public OnePointElevenAdapter(Minecraft mc) {
		this.mc = mc;
	}

	@Override
	public ClientPlayer getPlayer() {
		return ((ClientPlayer) mc.player);
	}

	@Override
	public World getWorld() {
		return this.world;
	}

	@Override
	public void setWorld(World world) {
		this.world = world;
	}

	@Override
	public Resolution getScreenResolution() {
		return new Resolution(mc.displayWidth, mc.displayHeight, mc.gameSettings.guiScale);
	}

	@Override
	public String getMinecraftVersion() {
		return "1.11.2";
	}
}
