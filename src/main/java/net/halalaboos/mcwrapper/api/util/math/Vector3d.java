package net.halalaboos.mcwrapper.api.util.math;

/**
 * Represents a three-dimensional point.
 */
public class Vector3d {

	/** The x-coordinate of the point */
	private double x;

	/** The y-coordinate of the point */
	private double y;

	/** The z-coordinate of the point */
	private double z;

	public Vector3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Helps simplify the process of adding a value to only one of the points of the Vector, rather
	 * than having to use {@link #add(double)}.
	 *
	 * @param x The amount to add to the {@link #x}
	 * @return The {@link Vector3d} with the given {@code x} added to the current {@link #x}
	 */
	public Vector3d addX(double x) {
		this.x += x;
		return this;
	}

	/**
	 * Helps simplify the process of adding a value to only one of the points of the Vector, rather
	 * than having to use {@link #add(double)}.
	 *
	 * @param y The amount to add to the {@link #y}
	 * @return The {@link Vector3d} with the given {@code y} added to the current {@link #y}
	 */
	public Vector3d addY(double y) {
		this.y += y;
		return this;
	}

	/**
	 * Helps simplify the process of adding a value to only one of the points of the Vector, rather
	 * than having to use {@link #add(double)}.
	 *
	 * @param z The amount to add to the {@link #z}
	 * @return The {@link Vector3d} with the given {@code z} added to the current {@link #z}
	 */
	public Vector3d addZ(double z) {
		this.z += z;
		return this;
	}

	public Vector3d add(double amount) {
		this.x += amount;
		this.y += amount;
		this.z += amount;
		return this;
	}

	public Vector3d add(Vector3d vec) {
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
		return this;
	}

	public Vector3d sub(double amount) {
		this.x -= amount;
		this.y -= amount;
		this.z -= amount;
		return this;
	}

	public Vector3d sub(Vector3d vec) {
		this.x -= vec.x;
		this.y -= vec.y;
		this.z -= vec.z;
		return this;
	}

	public Vector3d scale(double amount) {
		this.x *= amount;
		this.y *= amount;
		this.z *= amount;
		return this;
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

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public Vector3d setX(double x) {
		this.x = x;
		return this;
	}

	public Vector3d setY(double y) {
		this.y = y;
		return this;
	}

	public Vector3d setZ(double z) {
		this.z = z;
		return this;
	}

	public Vector3i toInt() {
		return new Vector3i(this);
	}
}
