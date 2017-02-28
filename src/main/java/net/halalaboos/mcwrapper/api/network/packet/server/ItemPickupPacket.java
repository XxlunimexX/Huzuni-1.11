package net.halalaboos.mcwrapper.api.network.packet.server;

import net.halalaboos.mcwrapper.api.network.packet.Packet;

public interface ItemPickupPacket extends Packet {

	/**
	 * @return The Entity ID of the collected item
	 */
	int getCollected();

	/**
	 * @return The Entity ID of the Entity that picked up the item
	 */
	int getCollector();

	/**
	 * @return The size of the item stack that was picked up
	 */
	int getQuantity();

}
