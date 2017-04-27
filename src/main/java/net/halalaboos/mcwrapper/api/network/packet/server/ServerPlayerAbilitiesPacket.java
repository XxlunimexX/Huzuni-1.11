package net.halalaboos.mcwrapper.api.network.packet.server;

import net.halalaboos.mcwrapper.api.network.packet.Packet;

public interface ServerPlayerAbilitiesPacket extends Packet {

	/**
	 * Whether or not the Player is currently flying.
	 *
	 * @return If the Player is flying
	 */
	boolean isFlying();

	/**
	 * Whether or not the Player is allowed to fly upon double-jumping.
	 *
	 * @return If the Player is allowed to fly
	 */
	boolean isFlyingAllowed();

}
