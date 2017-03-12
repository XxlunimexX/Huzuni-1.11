package net.halalaboos.huzuni.mod.combat;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.mcwrapper.api.event.network.PacketSendEvent;
import net.halalaboos.mcwrapper.api.network.packet.client.UseEntityPacket;
import net.halalaboos.mcwrapper.api.world.Fluid;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Attempts to force criticals by jumping.
 * */
public class Criticals extends BasicMod {
	
	public Criticals() {
		super("Criticals", "Automagically critical with each hit");
		this.setCategory(Category.COMBAT);
		setAuthor("brudin");
		subscribe(PacketSendEvent.class, event -> {
			if (event.getPacket() instanceof UseEntityPacket) {
				UseEntityPacket packetUseEntity = (UseEntityPacket)event.getPacket();
				if (packetUseEntity.getUseAction() == UseEntityPacket.UseAction.ATTACK) {
					if (shouldCritical()) {
						doCrit();
					}
				}
			}
		});
	}
	
	private void doCrit() {
		boolean preGround = getPlayer().isOnGround();
		getPlayer().setOnGround(false);
		getPlayer().jump();
		getPlayer().setOnGround(preGround);
	}
	
	private boolean shouldCritical() {
		return !getPlayer().isInFluid(Fluid.WATER) && getPlayer().isOnGround() && !getPlayer().isClimbing();
	}
	
}
