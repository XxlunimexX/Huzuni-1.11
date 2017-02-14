package net.halalaboos.mcwrapper.api.util;

/**
 * Represents a three-dimensional point.
 */
public class Vector3d {

	public final double x;
	public final double y;
	public final double z;

	public Vector3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3d add(double amount) {
		return new Vector3d(x + amount, y + amount, z + amount);
	}

	public Vector3d sub(double amount) {
		return new Vector3d(x - amount, y - amount, z - amount);
	}

	public Vector3d copy() {
		return new Vector3d(x, y, z);
	}
}
