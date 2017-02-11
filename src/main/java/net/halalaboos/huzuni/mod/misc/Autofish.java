package net.halalaboos.huzuni.mod.misc;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.PacketEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.util.EnumHand;

/**
 * Attempts to recast rods when fishing when a bob from a fish was found.
 * */
public class Autofish extends BasicMod {
	
	private int idFish;
	
	public Autofish() {
		super("Auto fish", "Automagically recasts and pulls fish");
		this.setCategory(Category.MISC);
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
		if (event.type == PacketEvent.Type.READ) {
			if (event.getPacket() instanceof SPacketSpawnObject) {
				SPacketSpawnObject packet = (SPacketSpawnObject)event.getPacket();
				if (packet.getEntityID() == 90 && packet.getData() == mc.player.getEntityId()) {
					idFish = packet.getEntityID();
				}
			} else if (event.getPacket() instanceof SPacketEntityVelocity) {
				SPacketEntityVelocity packetEntityVelocity = (SPacketEntityVelocity)event.getPacket();
				Entity packetEntity = mc.world.getEntityByID(packetEntityVelocity.getEntityID());
				if (packetEntity != null && packetEntity instanceof EntityFishHook) {
					EntityFishHook fish = (EntityFishHook)packetEntity;
					if (fish.motionX == 0 && fish.motionY < -0.02 && fish.motionZ == 0) {
						recastRod();
						idFish = -420;
					}
				}
			}
		}
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
