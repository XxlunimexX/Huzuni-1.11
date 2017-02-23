package net.halalaboos.mcwrapper.api.network.packet.client;

import net.halalaboos.mcwrapper.api.network.packet.Packet;

public interface ChatMessagePacket extends Packet {

	String getText();

}
