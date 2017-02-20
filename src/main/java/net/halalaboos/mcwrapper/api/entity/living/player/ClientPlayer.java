package net.halalaboos.mcwrapper.api.entity.living.player;

import net.halalaboos.mcwrapper.api.network.PlayerInfo;

public interface ClientPlayer extends Player {

	void swingItem(Hand hand);

	void closeWindow();

	boolean isFlying();

	float getForwardMovement();

	void setSneak(boolean sneak);

	String getBrand();

	void sendMessage(String message);

	PlayerInfo getInfo(Player player);

}
