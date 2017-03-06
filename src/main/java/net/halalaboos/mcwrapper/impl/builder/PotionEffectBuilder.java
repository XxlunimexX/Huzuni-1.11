package net.halalaboos.mcwrapper.impl.builder;

import net.halalaboos.mcwrapper.api.potion.Potion;
import net.halalaboos.mcwrapper.api.potion.PotionEffect;

public class PotionEffectBuilder implements PotionEffect.Builder {

	private Potion effect;
	private int duration;
	private int amplifier;
	private boolean infinite = false;
	private boolean particles = false;

	@Override
	public PotionEffect build() {
		net.minecraft.potion.PotionEffect out = new net.minecraft.potion.PotionEffect((net.minecraft.potion.Potion)effect, duration, amplifier, true, particles);
		out.setPotionDurationMax(infinite);
		return (PotionEffect) out;
	}

	@Override
	public PotionEffect.Builder setEffect(Potion potion) {
		this.effect = potion;
		return this;
	}

	@Override
	public PotionEffect.Builder setDuration(int duration) {
		this.duration = duration;
		return this;
	}

	@Override
	public PotionEffect.Builder setAmplifier(int amplifier) {
		this.amplifier = amplifier;
		return this;
	}

	@Override
	public PotionEffect.Builder setInfinite(boolean infinite) {
		this.infinite = infinite;
		return this;
	}

	@Override
	public PotionEffect.Builder setParticles(boolean particles) {
		this.particles = particles;
		return this;
	}
}
