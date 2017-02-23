package net.halalaboos.mcwrapper.api.network.packet.server;

import net.halalaboos.mcwrapper.api.network.packet.Packet;

public interface HealthUpdatePacket extends Packet {

	float getHearts();

	float getHunger();

	float getSaturation();
}
