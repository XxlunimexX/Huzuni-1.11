package net.halalaboos.mcwrapper.api.network;

import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.util.enums.DigAction;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface NetworkHandler {

	/**
	 * Returns a list of {@link PlayerInfo} for each connected Player to the current server.
	 *
	 * @return The list of players
	 */
	Collection<PlayerInfo> getPlayers();

	/**
	 * Sends the <code>PERFORM_RESPAWN</code> client status packet.
	 *
	 * <p><strong>NOTE:</strong> This only works if the player is dead.</p>
	 */
	void sendRespawn();

	/**
	 * Sends both the animation packet, and the try use item packet.
	 *
	 * <p>Essentially, it just fakes the right click item swinging effect, for items such as a fishing rod.</p>
	 */
	void sendUseSwing();

	Optional<PlayerInfo> getInfo(UUID uuid);

	void sendDigging(DigAction action, Vector3i pos, int face);

	void sendSwing(Hand hand);

	void sendTryUseItemOnBlock(Vector3i pos, Face face, Hand hand, float faceX, float faceY, float faceZ);
}
