package net.halalaboos.mcwrapper.impl;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.mcwrapper.api.MinecraftAdapter;
import net.halalaboos.mcwrapper.api.MinecraftClient;
import net.halalaboos.mcwrapper.api.client.GLState;
import net.halalaboos.mcwrapper.api.potion.PotionEffect;
import net.halalaboos.mcwrapper.api.registry.PotionRegistry;
import net.halalaboos.mcwrapper.api.util.Builder;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.registry.BlockRegistry;
import net.halalaboos.mcwrapper.api.registry.ItemRegistry;
import net.halalaboos.mcwrapper.impl.builder.ItemStackBuilder;
import net.halalaboos.mcwrapper.impl.builder.PotionEffectBuilder;
import net.halalaboos.mcwrapper.impl.registry.OnePointElevenBlockRegistry;
import net.halalaboos.mcwrapper.impl.registry.OnePointElevenItemRegistry;
import net.halalaboos.mcwrapper.impl.registry.OnePointElevenPotionRegistry;
import net.minecraft.client.Minecraft;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class OnePointElevenAdapter implements MinecraftAdapter {

	private MinecraftClient mc;

	private ItemRegistry itemRegistry = new OnePointElevenItemRegistry();
	private BlockRegistry blockRegistry = new OnePointElevenBlockRegistry();
	private PotionRegistry potionRegistry = new OnePointElevenPotionRegistry();
	private final Map<Class<?>, Supplier<?>> builderMap = new IdentityHashMap<>();
	private GLStateImpl glState = new GLStateImpl();

	public OnePointElevenAdapter(Minecraft mc) {
		this.mc = ((MinecraftClient) mc);
		registerBuilder(ItemStack.Builder.class, ItemStackBuilder::new);
		registerBuilder(PotionEffect.Builder.class, PotionEffectBuilder::new);
		Huzuni.INSTANCE.start();
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
	public PotionRegistry getPotionRegistry() {
		return this.potionRegistry;
	}

	@Override
	public GLState getGLStateManager() {
		return glState;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Builder> T getBuilder(Class<? extends Builder> builder) {
		Supplier<?> supplier = builderMap.get(builder);
		return (T)supplier.get();
	}

	@Override
	public <T> void registerBuilder(Class<T> builder, Supplier<? extends T> instance) {
		this.builderMap.put(builder, instance);
	}

	@Override
	public String getMinecraftVersion() {
		return "1.11.2";
	}
}
