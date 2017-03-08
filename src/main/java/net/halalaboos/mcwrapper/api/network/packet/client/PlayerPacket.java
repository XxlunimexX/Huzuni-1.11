package net.halalaboos.mcwrapper.api.network.packet.client;

import net.halalaboos.mcwrapper.api.network.packet.Packet;
import net.halalaboos.mcwrapper.api.util.math.Rotation;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;

public interface PlayerPacket extends Packet {

	Vector3d getLocation();

	void setLocation(Vector3d location);

	Rotation getRotation();

	void setRotation(Rotation rotation);

	boolean getOnGround();

	void setOnGround(boolean onGround);


}
