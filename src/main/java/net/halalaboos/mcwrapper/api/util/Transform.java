package net.halalaboos.mcwrapper.api.util;

/**
 * Represents the transform of an object, e.g. the rotation and scale.
 */
public class Transform {

	public final float scale;
	public final float pitch; //x rotation
	public final float yaw;	//y rotation

	public Transform(float scale, float pitch, float yaw) {
		this.scale = scale;
		this.pitch = pitch;
		this.yaw = yaw;
	}

	public Transform(float pitch, float yaw) {
		this(1, pitch, yaw);
	}
}
