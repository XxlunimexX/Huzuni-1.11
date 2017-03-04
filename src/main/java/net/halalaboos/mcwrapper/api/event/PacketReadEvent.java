package net.halalaboos.mcwrapper.api.event;

import net.halalaboos.mcwrapper.api.network.packet.Packet;
import net.halalaboos.tukio.Event;

/**
 * Performed every time a {@link Packet} is read by the client.
 *
 * <p>The {@link Packet packets} read by this Event are all server-side, no client-side packets will be read
 * with this Event.</p>
 */
public class PacketReadEvent extends Event {

	/**
	 * The Packet that has been read.
	 */
	private final Packet packet;

	public PacketReadEvent(Packet packet) {
		this.packet = packet;
	}

	/**
	 * {@link #packet}
	 */
	public Packet getPacket() {
		return packet;
	}
}
