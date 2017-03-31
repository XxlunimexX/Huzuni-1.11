package net.halalaboos.mcwrapper.api.network.packet.client;

import net.halalaboos.mcwrapper.api.util.enums.DigAction;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

public interface DiggingPacket {

	Vector3i getLocation();

	DigAction getDigAction();

	Face getFace();

}
