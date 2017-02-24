package net.halalaboos.mcwrapper.api.world;

import net.halalaboos.mcwrapper.api.block.tileentity.TileEntity;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import java.util.Collection;

public interface World {

	/**
	 * Sets the specified position to air.
	 */
	void setToAir(Vector3i pos);

	/**
	 * Returns a list of all of the loaded Player Entities.
	 */
	Collection<Player> getPlayers();

	/**
	 * Returns a list of all of the loaded Entities.
	 */
	Collection<Entity> getEntities();

	Collection<TileEntity> getTileEntities();

	Entity getEntity(int entityId);

}
