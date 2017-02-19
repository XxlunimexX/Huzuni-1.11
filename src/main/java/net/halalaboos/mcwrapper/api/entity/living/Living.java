package net.halalaboos.mcwrapper.api.entity.living;

import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.data.HealthData;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.item.ItemStack;

public interface Living extends Entity {

	HealthData getHealthData();

	void doJump();

	boolean getOnLadder();

	ItemStack getHeldItem(Hand hand);
}
