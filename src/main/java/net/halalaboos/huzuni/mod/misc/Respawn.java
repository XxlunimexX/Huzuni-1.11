package net.halalaboos.huzuni.mod.misc;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.mcwrapper.api.event.network.PacketReadEvent;
import net.halalaboos.mcwrapper.api.network.packet.server.HealthUpdatePacket;

import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;

/**
 * Respawns the player once their health has reached below 0.
 * */
public class Respawn extends BasicMod {
	
	public Respawn() {
		super("Respawn", "Automagically respawns once you're sent to the respawn screen");
		this.setCategory(Category.MISC);
		setAuthor("brudin");
		subscribe(PacketReadEvent.class, event -> {
			if (event.getPacket() instanceof HealthUpdatePacket) {
				HealthUpdatePacket packet = (HealthUpdatePacket)event.getPacket();
				if (packet.getHearts() > 0.0F)
					return;
				getMinecraft().getNetworkHandler().sendRespawn();
			}
		});
	}

}
