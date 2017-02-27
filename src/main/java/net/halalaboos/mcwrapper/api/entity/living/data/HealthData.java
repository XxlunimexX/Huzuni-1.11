package net.halalaboos.mcwrapper.api.entity.living.data;

public class HealthData {

	private float currentHealth;
	private float maxHealth;
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
}
