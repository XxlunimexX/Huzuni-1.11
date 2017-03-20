package net.halalaboos.huzuni.mod.misc;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.projectile.FishHook;
import net.halalaboos.mcwrapper.api.event.network.PacketReadEvent;
import net.halalaboos.mcwrapper.api.network.packet.server.EntityVelocityPacket;

import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;
import static net.halalaboos.mcwrapper.api.MCWrapper.getWorld;

/**
 * Attempts to recast rods when fishing when a bob from a fish was found.
 * */
public class Autofish extends BasicMod {

	public Autofish() {
		super("Auto fish", "Automagically recasts and pulls fish");
		setAuthor("brudin");
		this.setCategory(Category.MISC);

		subscribe(PacketReadEvent.class, event -> {
			//Check if the packet read is a velocity packet
			if (event.getPacket() instanceof EntityVelocityPacket) {
				EntityVelocityPacket packetEntityVelocity = (EntityVelocityPacket)event.getPacket();
				//Get the entity this packet targets
				Entity packetEntity = getWorld().getEntity(packetEntityVelocity.getId());
				//Check if the entity is a FishHook
				if (packetEntity != null && packetEntity instanceof FishHook) {
					FishHook fish = (FishHook)packetEntity;
					//Check if the owner is us
					if (fish.getOwner() == getPlayer()) {
						double velX = fish.getVelocity().getX();
						double velY = fish.getVelocity().getY();
						double velZ = fish.getVelocity().getZ();
						//Check if the velocity is the 'bobbing' amount
						if (velX == 0 && velY < -0.02 && velZ == 0) {
							//Reel in...
							getMinecraft().getNetworkHandler().sendUseSwing();
							//Recast the rod!
							getMinecraft().getNetworkHandler().sendUseSwing();
						}
					}
				}
			}
		});
	}
}
