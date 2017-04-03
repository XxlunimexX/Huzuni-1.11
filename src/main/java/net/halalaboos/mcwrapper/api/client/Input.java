package net.halalaboos.mcwrapper.api.client;

/**
 * Used to read the client-side movement input.
 */
public interface Input {

	/**
	 * Returns the forward movement value.
	 *
	 * <p>Positive values mean the Player is moving forward, negative means backwards, and 0 means no forward
	 * movement.</p>
	 */
	float getForward();

	/**
	 * Returns the strafe value.
	 *
	 * <p>Positive values mean the Player is moving left, negative means backwards, and 0 means no strafing.</p>
	 */
	float getStrafe();

	/**
	 * Returns whether or not the Player is pressing the 'Sneak' keybind.
	 */
	boolean isSneak();

	/**
	 * Returns whether or not the Player is pressing the 'Jump' keybind.
	 */
	boolean isJump();
}
