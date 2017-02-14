package net.halalaboos.mcwrapper.api.entity.living;

import net.halalaboos.mcwrapper.api.entity.Entity;

public interface Living extends Entity {

	double getHealth();

	double getMaxHealth();

	void jump();
}
