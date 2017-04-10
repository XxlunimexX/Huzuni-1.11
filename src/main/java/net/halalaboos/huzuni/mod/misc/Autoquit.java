package net.halalaboos.huzuni.mod.misc;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.mcwrapper.api.event.network.PacketReadEvent;
import net.halalaboos.mcwrapper.api.network.packet.server.HealthUpdatePacket;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Automatically disconnects from the current server if the Player's health is lower than the set value.  This is
 * commonly referred to as 'combat logging', and many servers have ways of blocking this.
 *
 * @author b
 */
public class Autoquit extends BasicMod {

	/**
	 * The maximum health to stay on the server at.
	 */
	public final Value health = new Value("Health", "", 0.5F, 6F, 20F, 0.5F, "Maximum health");
	
	public Autoquit() {
		super("Auto quit", "Automagically disconnects once the player health reaches below a threshold.");
		this.setCategory(Category.MISC);
		setAuthor("brudin");
		this.addChildren(health);
		subscribe(PacketReadEvent.class, event -> {
			//If we receive a health update packet, get to work
			if(event.getPacket() instanceof HealthUpdatePacket) {
				HealthUpdatePacket packetUpdateHealth = (HealthUpdatePacket)event.getPacket();
				//If our health is less than the maximum health value
				if(packetUpdateHealth.getHearts() <= health.getValue()) {
					//Set our Y-position to NaN, which will automatically kick us.
					//TODO - Perhaps this should be an option?  Such as just a normal log out and a NaN mode?
					getPlayer().setLocation(getPlayer().getLocation().addY(Double.NaN));
					//Disable the mod once we do this, that way we don't just kick ourselves again when we reconnect.
					setEnabled(false);
				}
			}
		});
	}
}
