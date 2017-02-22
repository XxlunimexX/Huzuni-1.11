package net.halalaboos.huzuni.mod.misc;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.PacketEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.settings.Value;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.minecraft.network.play.server.SPacketUpdateHealth;

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
	}
	
	@Override
	public void onEnable() {
		huzuni.eventManager.addListener(this);
	}
	
	@Override
	public void onDisable() {
		huzuni.eventManager.removeListener(this);
	}

	@EventMethod
	public void onPacket(PacketEvent event) {
		if(event.type == PacketEvent.Type.READ) {
			if(event.getPacket() instanceof SPacketUpdateHealth) {
				SPacketUpdateHealth packetUpdateHealth = (SPacketUpdateHealth)event.getPacket();
				if(packetUpdateHealth.getHealth() <= health.getValue()) {
					getPlayer().setLocation(Vector3d.RANDOM);
					setEnabled(false);
				}
			}
		}
	}

}
