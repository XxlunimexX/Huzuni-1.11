package net.halalaboos.mcwrapper.api.network.packet.server;

import net.halalaboos.mcwrapper.api.network.packet.Packet;

public interface SpawnObjectPacket extends Packet {

	int getSpawnedId();

	int getSourceId();
}
