package net.halalaboos.mcwrapper.impl;

import net.halalaboos.mcwrapper.api.MinecraftAdapter;
import net.halalaboos.mcwrapper.api.MinecraftClient;
import net.halalaboos.mcwrapper.api.registry.BlockRegistry;
import net.halalaboos.mcwrapper.api.registry.ItemRegistry;
import net.halalaboos.mcwrapper.api.world.World;
import net.halalaboos.mcwrapper.impl.registry.OnePointElevenBlockRegistry;
import net.halalaboos.mcwrapper.impl.registry.OnePointElevenItemRegistry;
import net.minecraft.client.Minecraft;

public class OnePointElevenAdapter implements MinecraftAdapter {

	private MinecraftClient mc;
	private World world;

	private ItemRegistry itemRegistry = new OnePointElevenItemRegistry();
	private BlockRegistry blockRegistry = new OnePointElevenBlockRegistry();

	public OnePointElevenAdapter(Minecraft mc) {
		this.mc = ((MinecraftClient) mc);
		System.out.println("MCWrapper Loaded with OnePointElevenAdapter!");
	}

	@Override
	public void setWorld(World world) {
		this.world = world;
	}

	@Override
	public MinecraftClient getMinecraft() {
		return mc;
	}

	@Override
	public ItemRegistry getItemRegistry() {
		return this.itemRegistry;
	}

	@Override
	public BlockRegistry getBlockRegistry() {
		return this.blockRegistry;
	}

	@Override
	public String getMinecraftVersion() {
		return "1.11.2";
	}
}
