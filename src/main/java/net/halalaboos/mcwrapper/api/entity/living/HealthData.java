package net.halalaboos.mcwrapper.api.entity.living;

public class HealthData {

	private float currentHealth;
	private float maxHealth;

	public HealthData(float currentHealth, float maxHealth) {
		this.currentHealth = currentHealth;
		this.maxHealth = maxHealth;
	}

	public float getCurrentHealth() {
		return currentHealth;
	}

	public float getMaxHealth() {
		return maxHealth;
	}
}
