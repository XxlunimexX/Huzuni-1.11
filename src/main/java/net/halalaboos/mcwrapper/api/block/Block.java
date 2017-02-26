package net.halalaboos.mcwrapper.api.block;

import net.halalaboos.mcwrapper.api.attribute.Nameable;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.halalaboos.mcwrapper.api.world.World;

public interface Block extends Nameable {

	float getSlipperiness();
	void setSlipperiness(float slipperiness);

	float blockStrength(Player player, World world, Vector3i pos);

	int getId();
}
