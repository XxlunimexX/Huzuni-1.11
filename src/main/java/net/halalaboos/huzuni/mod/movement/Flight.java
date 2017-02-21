package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.PlayerMoveEvent;
import net.halalaboos.huzuni.api.event.UpdateEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.settings.Value;
import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.entity.living.player.ClientPlayer;
import org.lwjgl.input.Keyboard;

public class Flight extends BasicMod {

	public static final Flight INSTANCE = new Flight();

	private final Value speed = new Value("Speed", "", 0.1F, 1F, 10F, "movement speed");

	private Flight() {
		super("Flight", "Allows an individual to fly", Keyboard.KEY_F);
		this.setAuthor("Halalaboos");
		this.setCategory(Category.MOVEMENT);
		addChildren(speed);
	}

	@Override
	public void onEnable() {
		huzuni.eventManager.addListener(this);
	}

	@Override
	public void onDisable() {
		huzuni.eventManager.removeListener(this);
		if (MCWrapper.getPlayer() != null) {
			MCWrapper.getPlayer().setFlying(false);
		}
	}

	@EventMethod
	public void onUpdate(UpdateEvent event) {
		ClientPlayer player = MCWrapper.getPlayer();
		switch (event.type) {
			case PRE:
				player.setFlying(true);
				if (player.getFallDistance() > 3) {
					player.setOnGround(true);
				}
				break;
			case POST:
				if (player.getFallDistance() > 3) {
					player.setOnGround(false);
				}
				break;
		}
	}

	@EventMethod
	public void onPlayerMove(PlayerMoveEvent event) {
		event.setMotionX(event.getMotionX() * speed.getValue());
		event.setMotionY(event.getMotionY() * speed.getValue());
		event.setMotionZ(event.getMotionZ() * speed.getValue());
	}

}
