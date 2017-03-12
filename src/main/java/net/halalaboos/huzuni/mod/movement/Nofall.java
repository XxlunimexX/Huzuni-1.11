package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.mcwrapper.api.event.network.PacketSendEvent;
import net.halalaboos.mcwrapper.api.network.packet.client.PlayerPacket;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

public class Nofall extends BasicMod {
	
	public Nofall() {
		super("Nofall", "Prevents fall damage from occurring", Keyboard.KEY_N);
		setAuthor("brudin");
		this.setCategory(Category.MOVEMENT);
		subscribe(PacketSendEvent.class, event -> {
			//If Fly is enabled, don't do anything, since that has it's own Nofall.
			if (!Flight.INSTANCE.isEnabled()) {
				//Check if it's the Player update packet
				if (event.getPacket() instanceof PlayerPacket) {
					PlayerPacket packet = (PlayerPacket) event.getPacket();
					//Tell the server we're on ground if our fall distance is greater than 2
					if (getPlayer().getFallDistance() > 2) packet.setOnGround(true);
					//Prevents the 'crunch' noise from playing when hitting the ground.
					if (packet.getOnGround()) getPlayer().setFallDistance(0);
				}
			}
		});
	}
}
