package net.halalaboos.mcwrapper.api.network.packet.server;

import net.halalaboos.mcwrapper.api.network.packet.Packet;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;

public interface EntityVelocityPacket extends Packet {

	void setVelocity(Vector3d velocity);

	Vector3d getVelocity();

	int getId();

}
