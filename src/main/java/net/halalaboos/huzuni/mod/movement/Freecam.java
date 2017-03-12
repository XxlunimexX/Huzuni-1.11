package net.halalaboos.huzuni.mod.movement;

import com.mojang.authlib.GameProfile;
import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.PlayerMoveEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Value;
import net.halalaboos.mcwrapper.api.event.PreMotionUpdateEvent;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import org.lwjgl.input.Keyboard;

/**
 * Allows the player to fly freely from their body and explore the world.
 * */
public class Freecam extends BasicMod {
	
	public static final Freecam INSTANCE = new Freecam();
	
	public final Value speed = new Value("Speed", "", 0.1F, 1F, 10F, "movement speed");

	private boolean oldFlying = false;
    private EntityOtherPlayerMP fakePlayer;
	
	private Freecam() {
		super("Freecam", "Allows an individual to fly FROM THEIR BODY?", Keyboard.KEY_U);
		this.setCategory(Category.MOVEMENT);
		setAuthor("Halalaboos");
		addChildren(speed);
	}
	
	@Override
	public void toggle() {
		super.toggle();
		if (mc.player != null && mc.world != null) {
	        if (isEnabled()) {
	        	oldFlying = Flight.INSTANCE.isEnabled();
	            fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(mc.player.getUniqueID(), mc.player.getName()));
	            fakePlayer.copyLocationAndAnglesFrom(mc.player);
				fakePlayer.inventory = mc.player.inventory;
	            fakePlayer.setPositionAndRotation(mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.rotationYaw, mc.player.rotationPitch);
	            fakePlayer.rotationYawHead = mc.player.rotationYawHead;
				mc.world.addEntityToWorld(-69, fakePlayer);
				Flight.INSTANCE.setEnabled(true);
			 } else {
	        	if (fakePlayer != null && mc.player != null) {
	        		mc.player.setPositionAndRotation(fakePlayer.posX, fakePlayer.posY, fakePlayer.posZ, fakePlayer.rotationYaw, fakePlayer.rotationPitch);
	            	mc.world.removeEntityFromWorld(-69);
					Flight.INSTANCE.setEnabled(oldFlying);
	        	}
	        	 if (mc.player != null)
					 Flight.INSTANCE.setEnabled(oldFlying);
	        }
			mc.renderGlobal.loadRenderers(); //Fixes culling updates
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
	public void onPlayerMove(PlayerMoveEvent event) {
		mc.player.setSprinting(false);
		Flight.INSTANCE.setEnabled(true);
		if (fakePlayer != null)
			fakePlayer.setHealth(mc.player.getHealth());
		event.setMotionX(event.getMotionX() * speed.getValue());
		event.setMotionY(event.getMotionY() * speed.getValue());
		event.setMotionZ(event.getMotionZ() * speed.getValue());
		mc.player.renderArmPitch = 1000;
		mc.player.renderArmYaw = 1000;
	}
}
