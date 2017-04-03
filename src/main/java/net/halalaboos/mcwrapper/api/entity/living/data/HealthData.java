package net.halalaboos.mcwrapper.api.entity.living.data;

/**
 * Represents various health-related information for an Entity.
 *
 * Since there are many different values (some have yet to be added here), this class is used to prevent those values
 * from cluttering up the Entity class(es).
 */
public class HealthData {

	/**
	 * The current health value.
	 */
	private float currentHealth;

	/**
	 * The maximum health value.
	 */
	private float maxHealth;

	/**
	 * The absorption amount.
	 * This will only be greater than zero if the target has the Absorption effect.
	 */
	private float absorptionAmount;

	public HealthData(float currentHealth, float maxHealth, float absorptionAmount) {
		this.currentHealth = currentHealth;
		this.maxHealth = maxHealth;
		this.absorptionAmount = absorptionAmount;
	}

	public float getCurrentHealth() {
		return currentHealth;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public float getAbsorptionAmount() {
		return absorptionAmount;
	}

	public float getHealthPercentage() {
		return currentHealth / maxHealth;
	}
}
