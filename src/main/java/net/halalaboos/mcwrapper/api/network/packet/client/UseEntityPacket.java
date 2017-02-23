package net.halalaboos.mcwrapper.api.network.packet.client;

import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.network.packet.Packet;

public interface UseEntityPacket extends Packet {

	Entity getEntity();

	Hand getHand();

	UseAction getAction();

	enum UseAction {
		INTERACT, ATTACK, INTERACT_AT
	}
}
