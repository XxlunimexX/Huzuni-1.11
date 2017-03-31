package net.halalaboos.mcwrapper.api.util.enums;

import net.halalaboos.mcwrapper.api.util.math.Vector3i;

/**
 * Dig actions used for the digging packet.
 *
 * @see {@link net.halalaboos.mcwrapper.api.network.NetworkHandler#sendDigging(DigAction, Vector3i, int)}
 */
public enum DigAction {
	/**
	 * Tell the server we are beginning to break a block
	 */
	START,
	/**
	 * Tell the server we stopped digging (mid-way through breaking, aka cancelling)
	 */
	ABORT,
	/**
	 * Tell the server we have finished breaking a block
	 */
	COMPLETE,
	/**
	 * Tell the server we are dropping all of our items
	 */
	DROP_ALL,
	/**
	 * Tell the server we are dropping only our held item
	 */
	DROP_ONE,
	/**
	 * Tell the server we are finished using our main item
	 */
	RELEASE_ITEM,
	/**
	 * Tell the server we are swapping our main and off-hand items
	 */
	SWAP_ITEMS
}
