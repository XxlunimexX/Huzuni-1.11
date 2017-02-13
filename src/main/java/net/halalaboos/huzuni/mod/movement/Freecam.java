package net.halalaboos.huzuni.mod.movement;

import com.mojang.authlib.GameProfile;
import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.PacketEvent;
import net.halalaboos.huzuni.api.event.PlayerMoveEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.settings.Value;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import org.lwjgl.input.Keyboard;

/**
 * Allows the player to fly freely from their body and explore the world.
 * */
public class Freecam extends BasicMod {
	
	public static final Freecam INSTANCE = new Freecam();
	
	public final Value speed = new Value("Speed", "", 0.1F, 1F, 10F, "movement speed");
	
    private EntityOtherPlayerMP fakePlayer;
	
	private Freecam() {
		super("Freecam", "Allows an individual to fly FROM THEIR BODY?", Keyboard.KEY_U);
		this.setCategory(Category.MOVEMENT);
		addChildren(speed);
	}
	
	@Override
	public void toggle() {
		super.toggle();
		if (mc.player != null && mc.world != null) {
	        if (isEnabled()) {
	            fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(mc.player.getUniqueID(), mc.player.getName()));
	            fakePlayer.copyLocationAndAnglesFrom(mc.player);
				fakePlayer.inventory = mc.player.inventory;
	            fakePlayer.setPositionAndRotation(mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.rotationYaw, mc.player.rotationPitch);
	            fakePlayer.rotationYawHead = mc.player.rotationYawHead;
				mc.world.addEntityToWorld(-69, fakePlayer);
				mc.player.capabilities.isFlying = true;
			 } else {
	        	if (fakePlayer != null && mc.player != null) {
	        		mc.player.setPositionAndRotation(fakePlayer.posX, fakePlayer.posY, fakePlayer.posZ, fakePlayer.rotationYaw, fakePlayer.rotationPitch);
	            	mc.world.removeEntityFromWorld(-69);
	            	mc.player.capabilities.isFlying = false;
	        	}
	        	 if (mc.player != null)
	                 mc.player.capabilities.isFlying = false;
	        }
		}
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
		if (event.type == PacketEvent.Type.SENT) {
			mc.player.setSprinting(false);
			mc.player.capabilities.isFlying = true;
			if (fakePlayer != null)
				fakePlayer.setHealth(mc.player.getHealth());
		}
	}

	@EventMethod
	public void onPlayerMove(PlayerMoveEvent event) {
		event.setMotionX(event.getMotionX() * speed.getValue());
		event.setMotionY(event.getMotionY() * speed.getValue());
		event.setMotionZ(event.getMotionZ() * speed.getValue());
		mc.player.renderArmPitch += 200;
		mc.player.renderArmYaw += 180;
	}
}
