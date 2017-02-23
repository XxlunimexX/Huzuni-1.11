package net.halalaboos.mcwrapper.api.network.packet.client;

import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.network.packet.Packet;
import net.halalaboos.mcwrapper.api.world.World;

public interface UseEntityPacket extends Packet {

	Entity getEntity(World world);

	int getId();

	Hand getUsedHand();

	UseAction getUseAction();

	enum UseAction {
		INTERACT, ATTACK, INTERACT_AT
	}
}
