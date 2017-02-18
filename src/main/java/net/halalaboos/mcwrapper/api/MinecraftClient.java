package net.halalaboos.mcwrapper.api;

import net.halalaboos.mcwrapper.api.entity.living.player.ClientPlayer;
import net.halalaboos.mcwrapper.api.util.Resolution;
import net.halalaboos.mcwrapper.api.world.World;

public interface MinecraftClient {

	int getRightClickDelayTimer();

	void setRightClickDelayTimer(int rightClickDelayTimer);

	float getTimerSpeed();

	void setTimerSpeed(float timerSpeed);

	float getDelta();

	ClientPlayer getPlayer();

	World getWorld();

	Resolution getScreenResolution();

}
