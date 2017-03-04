package net.halalaboos.huzuni.mod.misc;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.projectile.FishHook;
import net.halalaboos.mcwrapper.api.event.PacketReadEvent;
import net.halalaboos.mcwrapper.api.network.packet.server.EntityVelocityPacket;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

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
			if (event.getPacket() instanceof EntityVelocityPacket) {
				EntityVelocityPacket packetEntityVelocity = (EntityVelocityPacket)event.getPacket();
				Entity packetEntity = getWorld().getEntity(packetEntityVelocity.getId());
				if (packetEntity != null && packetEntity instanceof FishHook) {
					FishHook fish = (FishHook)packetEntity;
					if (fish.getOwner() == getPlayer()) {
						double velX = fish.getVelocity().getX();
						double velY = fish.getVelocity().getY();
						double velZ = fish.getVelocity().getZ();
						if (velX == 0 && velY < -0.02 && velZ == 0) {
							recastRod();
						}
					}
				}
			}
		});
	}


	/**
     * Recasts the rod.
     * */
	private void recastRod() {
		CPacketAnimation packetAnimation = new CPacketAnimation(EnumHand.MAIN_HAND);
		CPacketPlayerTryUseItem packetTryUse = new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND);

		mc.getConnection().sendPacket(packetAnimation);
		mc.getConnection().sendPacket(packetTryUse);

		mc.getConnection().sendPacket(packetAnimation);
		mc.getConnection().sendPacket(packetTryUse);
	}


}
