package net.halalaboos.mcwrapper.api.world;

import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.block.tileentity.TileEntity;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.util.math.AABB;
import net.halalaboos.mcwrapper.api.util.math.Result;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import java.util.Collection;
import java.util.Optional;

/**
 * Represents a World in the game.
 *
 * All of the methods in this package that refer to vanilla code will have the {@link MCWrapper#getPlayer()} set as
 * the target Entity parameter if applicable (e.g. getRelativeHardness, sendBlockBreakProgress, etc.).  Since this
 * is a wrapper for client-side purposes, there isn't much reason to target other entities for such methods.
 */
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

	/**
	 * Returns a list of Entities in the specified box, excluding the Player.
	 */
	Collection<Entity> getEntitiesInBox(AABB aabb);

	Collection<TileEntity> getTileEntities();

	Entity getEntity(int entityId);

	Block getBlock(Vector3i pos);
	Block getBlock(int x, int y, int z);

	/**
	 * Checks if the list of colliding bounding boxes from an offset of the Player's bounding box is empty.
	 *
	 * @param offset The offset of the bounding box
	 * @return Whether or not there is a bounding box
	 */
	boolean isOffsetBBEmpty(Vector3d offset);

	boolean blockExists(Vector3i pos);

	void sendBreakProgress(Vector3i pos, int progress);

	float getRelativeHardness(Vector3i pos);

	Player spawnCopiedPlayer(int entityID, Player target);

	void removeEntity(int entityID);

	int getBlockMeta(Vector3i pos);

	Optional<Result> getResult(Vector3d playerVector, Vector3d pos);
}
