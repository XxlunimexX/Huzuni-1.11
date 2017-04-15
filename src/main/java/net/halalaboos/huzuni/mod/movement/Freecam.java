package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.event.network.PacketSendEvent;
import net.halalaboos.mcwrapper.api.event.player.MoveEvent;
import net.halalaboos.mcwrapper.api.network.packet.client.PlayerPacket;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;
import static net.halalaboos.mcwrapper.api.MCWrapper.getWorld;

/**
 * Allows the player to fly freely from their body and explore the world.
 */
public class Freecam extends BasicMod {
	
	public static final Freecam INSTANCE = new Freecam();
	
	public final Value speed = new Value("Speed", 0.1F, 1F, 10F, "movement speed");

	private boolean oldFlying = false;
    private Player fakePlayer;

	private Freecam() {
		super("Freecam", "Allows an individual to fly FROM THEIR BODY?", Keyboard.KEY_U);
		this.setCategory(Category.MOVEMENT);
		setAuthor("Halalaboos");
		addChildren(speed);
		subscribe(MoveEvent.class, this::onPlayerMove);
		subscribe(PacketSendEvent.class, this::onPacketSend);
	}

	private void onPlayerMove(MoveEvent event) {
		getPlayer().setSprinting(false);
		Flight.INSTANCE.setEnabled(true);
	}

	private void onPacketSend(PacketSendEvent event) {
		event.setCancelled(event.getPacket() instanceof PlayerPacket);
	}
	
	@Override
	public void toggle() {
		super.toggle();
		if (getPlayer() != null && getWorld() != null) {
	        if (isEnabled()) {
	        	oldFlying = Flight.INSTANCE.isEnabled();
	            fakePlayer = getWorld().spawnCopiedPlayer(-69, getPlayer());
				Flight.INSTANCE.setEnabled(true);
			 } else {
	        	if (fakePlayer != null && getPlayer() != null) {
	        		getPlayer().setLocation(fakePlayer.getLocation());
	        		getPlayer().setRotation(fakePlayer.getRotation());
	        		getWorld().removeEntity(-69);
					Flight.INSTANCE.setEnabled(oldFlying);
	        	}
	        	 if (getPlayer() != null)
					 Flight.INSTANCE.setEnabled(oldFlying);
	        }
			mc.loadRenderers(); //Fixes culling updates
			getPlayer().setNoClip(enabled); //Enable noclip
		}
    }
}
