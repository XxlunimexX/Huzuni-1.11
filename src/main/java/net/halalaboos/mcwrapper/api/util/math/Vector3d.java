package net.halalaboos.mcwrapper.api.util.math;

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

	public Vector3d add(Vector3d vec) {
		return new Vector3d(this.x + vec.x, this.y + vec.y, this.z + vec.z);
	}

	public Vector3d sub(double amount) {
		return new Vector3d(x - amount, y - amount, z - amount);
	}

	public Vector3d sub(Vector3d vec) {
		return new Vector3d(this.x - vec.x, this.y - vec.y, this.z - vec.z);
	}

	public Vector3d scale(double amount) {
		return new Vector3d(x * amount, y * amount, z * amount);
	}

	public double distanceTo(Vector3d target) {
		double dX = this.x - target.x;
		double dY = this.y - target.y;
		double dZ = this.z - target.z;
		return MathUtils.sqrt(dX * dX + dY * dY + dZ * dZ);
	}

	public Vector3d rotatePitch(float pitch) {
		float cos = MathUtils.cos(pitch);
		float sin = MathUtils.sin(pitch);
		return new Vector3d(this.x, this.y * (double)cos + this.z * (double)sin,
				this.z * (double)cos - this.y * (double)sin);
	}

	public Vector3d rotateYaw(float yaw) {
		float cos = MathUtils.cos(yaw);
		float sin = MathUtils.sin(yaw);
		return new Vector3d(this.x * (double)cos + this.z * (double)sin, this.y,
				this.z * (double)cos - this.x * (double)sin);
	}

	public Vector3d copy() {
		return new Vector3d(x, y, z);
	}
}
