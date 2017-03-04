package net.halalaboos.huzuni.mod.misc;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Value;
import net.halalaboos.mcwrapper.api.event.PacketReadEvent;
import net.halalaboos.mcwrapper.api.network.packet.server.HealthUpdatePacket;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Automatically disconnects once the health of the player reaches a threshold.
 * */
public class Autoquit extends BasicMod {
	
	public final Value health = new Value("Health", "", 0.5F, 6F, 20F, 0.5F, "Maximum health");
	
	public Autoquit() {
		super("Auto quit", "Automagically disconnects once the player health reaches below a threshold.");
		this.setCategory(Category.MISC);
		setAuthor("brudin");
		this.addChildren(health);
		subscribe(PacketReadEvent.class, event -> {
			if(event.getPacket() instanceof HealthUpdatePacket) {
				HealthUpdatePacket packetUpdateHealth = (HealthUpdatePacket)event.getPacket();
				if(packetUpdateHealth.getHearts() <= health.getValue()) {
					getPlayer().setLocation(getPlayer().getLocation().addY(Double.NaN));
					setEnabled(false);
				}
			}
		});
	}
}
