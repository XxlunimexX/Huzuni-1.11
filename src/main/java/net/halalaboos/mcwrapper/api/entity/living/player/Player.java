package net.halalaboos.mcwrapper.api.entity.living.player;

import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.item.ItemStack;

public interface Player extends Living {

	float getAttackStrength();

	ItemStack getStack(int slot);

	boolean isNPC();

	void setNPC(boolean npc);

	float getFood();

	float getSaturation();

}
