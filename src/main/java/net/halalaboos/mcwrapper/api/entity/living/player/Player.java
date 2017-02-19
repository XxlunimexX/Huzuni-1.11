package net.halalaboos.mcwrapper.api.entity.living.player;

import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.item.ItemStack;

public interface Player extends Living {

	float getAttackStrength();

	boolean isGameType(GameType type);

	ItemStack getStack(int slot);

	boolean isNPC();

}
