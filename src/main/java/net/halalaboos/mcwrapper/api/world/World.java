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
	 * Returns a list of all of the loaded {@link Player Players}.
	 */
	Collection<Player> getPlayers();

	/**
	 * Returns a list of all of the loaded {@link Entity Entities}.
	 * <p>This will not return {@link TileEntity Tile Entities}.  Instead, use {@link #getTileEntities()}</p>
	 */
	Collection<Entity> getEntities();

	/**
	 * Returns a list of Entities in the specified box, excluding the Player.
	 */
	Collection<Entity> getEntitiesInBox(AABB aabb);

	/**
	 * Returns a list of all of the loaded {@link TileEntity Tile Entities}.
	 */
	Collection<TileEntity> getTileEntities();

	/**
	 * Returns an {@link Entity} with the provided EntityID, if it exists.
	 *
	 * @param entityId The ID of the {@link Entity}.
	 * @return The Entity with the ID, if applicable
	 */
	Entity getEntity(int entityId);

	/**
	 * Returns the {@link Block} at the specified position.
	 *
	 * @param pos The position to look for a {@link Block}.
	 * @return The Block at the position
	 */
	Block getBlock(Vector3i pos);

	/**
	 * Returns the {@link Block} at the specified position.  It's the same as {@link #getBlock(Vector3i)}, just
	 * for those who would rather call the coordinates directly.
	 *
	 * @param x The x-position
	 * @param y The y-position
	 * @param z The z-position
	 * @return The Block at the position
	 */
	Block getBlock(int x, int y, int z);

	/**
	 * Checks if the list of colliding bounding boxes from an offset of the Player's bounding box is empty.
	 *
	 * @param offset The offset of the bounding box
	 * @return Whether or not there is a bounding box
	 */
	boolean isOffsetBBEmpty(Vector3d offset);

	/**
	 * Checks if the specified {@link Vector3i position} contains a block.
	 *
	 * @param pos The position to check (x, y, z)
	 * @return If a block exists
	 */
	boolean blockExists(Vector3i pos);

	/**
	 * Sends block breaking progress at the provided position.
	 *
	 * @param pos The position to send the progress
	 * @param progress The break progress
	 */
	void sendBreakProgress(Vector3i pos, int progress);

	/**
	 * Returns the relative hardness to the {@link MCWrapper#getPlayer() player} at the provided position.
	 *
	 * @param pos The position to get the relative hardness.
	 * @return The relative block hardness.
	 */
	float getRelativeHardness(Vector3i pos);

	/**
	 * Spawns a copy (fake) of the provided {@link Player}.
	 *
	 * @param entityID What the copied player's EntityID should be.
	 * @param target The target player to copy.
	 * @return The copied player.
	 */
	Player spawnCopiedPlayer(int entityID, Player target);

	/**
	 * Removes an {@link Entity} from the world, given the EntityID.
	 *
	 * @param entityID The EntityID
	 */
	void removeEntity(int entityID);

	/**
	 * Returns the metadata of the {@link Block} at the provided position.
	 *
	 * @param pos The position of the Block.
	 * @return The Block's metadata
	 */
	int getBlockMeta(Vector3i pos);

	/**
	 * Raytraces the Blocks between the two provided {@link Vector3d vectors} and returns the {@link Result},
	 * if applicable.
	 *
	 * @return The raytrace result.
	 */
	Optional<Result> getResult(Vector3d start, Vector3d end);

	Optional<Result> getResult(Vector3d start, Vector3d end, boolean stopOnLiquid, boolean ignoreBlockWithoutBB, boolean lastBlock);
}
