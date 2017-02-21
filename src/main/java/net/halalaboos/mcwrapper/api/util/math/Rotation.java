package net.halalaboos.mcwrapper.api.util.math;

/**
 * Represents the transform of an object, e.g. the rotation and scale.
 */
public class Rotation {

	public final float pitch; //x rotation
	public final float yaw;    //y rotation

	public Rotation(float pitch, float yaw) {
		this.pitch = pitch;
		this.yaw = yaw;
	}
}
