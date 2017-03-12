package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.PlayerMoveEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Value;
import net.halalaboos.mcwrapper.api.event.network.PacketSendEvent;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.network.packet.client.PlayerPacket;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

public class Flight extends BasicMod {

	public static final Flight INSTANCE = new Flight();

	private final Value speed = new Value("Speed", "", 0.1F, 1F, 10F, "movement speed");

	private Flight() {
		super("Flight", "Allows an individual to fly", Keyboard.KEY_F);
		this.setAuthor("Halalaboos");
		this.setCategory(Category.MOVEMENT);
		addChildren(speed);
		subscribe(PacketSendEvent.class, event -> {
			//Check if it's the Player update packet
			if (event.getPacket() instanceof PlayerPacket) {
				PlayerPacket packet = (PlayerPacket)event.getPacket();
				//Tell the server we're on the ground
				packet.setOnGround(true);
			}
		});
		//Enables creative flying
		subscribe(PreMotionUpdateEvent.class, event -> getPlayer().setFlying(true));
	}

	@Override
	public void onEnable() {
		huzuni.eventManager.addListener(this);
	}

	@Override
	public void onDisable() {
		huzuni.eventManager.removeListener(this);
		if (getPlayer() != null) {
			getPlayer().setFlying(false);
		}
	}

	@EventMethod
	public void onPlayerMove(PlayerMoveEvent event) {
		event.setMotionX(event.getMotionX() * speed.getValue());
		event.setMotionY(event.getMotionY() * speed.getValue());
		event.setMotionZ(event.getMotionZ() * speed.getValue());
	}

}
