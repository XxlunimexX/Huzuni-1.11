package net.halalaboos.mcwrapper.api.event.network;

import net.halalaboos.mcwrapper.api.network.packet.Packet;
import net.halalaboos.tukio.Cancellable;
import net.halalaboos.tukio.Event;

public class PacketSendEvent extends Event implements Cancellable {

	private final Packet packet;

	private boolean cancelled = false;

	public PacketSendEvent(Packet sentPacket) {
		this.packet = sentPacket;
	}

	public Packet getPacket() {
		return packet;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
