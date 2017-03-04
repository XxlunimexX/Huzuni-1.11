package net.halalaboos.mcwrapper.api.network.packet.client;

import net.halalaboos.mcwrapper.api.network.packet.Packet;

public interface TabCompletePacket extends Packet {

	String getText();

	void setText(String text);

}
