package net.halalaboos.mcwrapper.api;

public interface MinecraftClient {

	int getRightClickDelayTimer();

	void setRightClickDelayTimer(int rightClickDelayTimer);

	float getTimerSpeed();

	void setTimerSpeed(float timerSpeed);

	float getDelta();
}
