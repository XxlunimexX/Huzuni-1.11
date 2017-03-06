package net.halalaboos.mcwrapper.api.potion;

import net.halalaboos.mcwrapper.api.MCWrapper;

public interface PotionEffect {

	Potion getEffect();

	int getDuration();

	int getAmplifier();

	void setInfinite(boolean infinite);

	/**
	 * Whether or not the Potion shows particles
	 */
	boolean showParticles();

	void setParticles(boolean particles);

	static Builder getBuilder() {
		return MCWrapper.getAdapter().getBuilder(Builder.class);
	}

	static PotionEffect from(Potion effect, int duration, int amplifier, boolean infinite, boolean particles) {
		return getBuilder().setEffect(effect).setDuration(duration).setAmplifier(amplifier).setInfinite(infinite).setParticles(particles).build();
	}

	interface Builder extends net.halalaboos.mcwrapper.api.util.Builder<PotionEffect> {

		Builder setEffect(Potion potion);

		Builder setDuration(int duration);

		Builder setAmplifier(int amplifier);

		Builder setInfinite(boolean infinite);

		Builder setParticles(boolean particles);

	}
}
