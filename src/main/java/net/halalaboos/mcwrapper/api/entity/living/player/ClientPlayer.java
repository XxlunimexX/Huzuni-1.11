package net.halalaboos.mcwrapper.api.entity.living.player;

import net.halalaboos.mcwrapper.api.util.Rotation;
import net.halalaboos.mcwrapper.api.util.Vector3d;

public interface ClientPlayer extends Player {

	void setRotation(Rotation rotation);

	void swingItem(Hand hand);

	void setLocation(Vector3d pos);

	void closeWindow();

	boolean isFlying();

	float getForwardMovement();

}
