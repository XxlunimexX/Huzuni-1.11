package net.halalaboos.mcwrapper.api.network.packet.client;

import net.halalaboos.mcwrapper.api.network.packet.Packet;

public interface PlayerAbilitiesPacket extends Packet {

	void setFlying(boolean flying);

	boolean isFlyingAllowed();

	boolean isFlying();
}
