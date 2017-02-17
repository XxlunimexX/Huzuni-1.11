package net.halalaboos.mcwrapper.api.entity.living.player;

import net.halalaboos.mcwrapper.api.util.Rotation;
import net.halalaboos.mcwrapper.api.util.Vector3d;

public interface ClientPlayer extends Player {

	void swingItem(Hand hand);

	void closeWindow();

	boolean isFlying();

	float getForwardMovement();

}
