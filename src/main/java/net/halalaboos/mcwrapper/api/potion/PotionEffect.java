package net.halalaboos.mcwrapper.api.potion;

public interface PotionEffect {

	Potion getEffect();

	int getDuration();

	int getAmplifier();

	/**
	 * Whether or not the Potion shows particles
	 */
	boolean showParticles();
}
