package net.halalaboos.mcwrapper.api.util.math;

/**
 * Represents a three-dimensional point.
 */
public class Vector3d {

	/** The x-coordinate of the point */
	private final double x;

	/** The y-coordinate of the point */
	private final double y;

	/** The z-coordinate of the point */
	private final double z;

	public static final Vector3d ZERO = new Vector3d(0, 0, 0);

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
		return new Vector3d(this.x + x, this.y, this.z);
	}

	/**
	 * Helps simplify the process of adding a value to only one of the points of the Vector, rather
	 * than having to use {@link #add(double)}.
	 *
	 * @param y The amount to add to the {@link #y}
	 * @return The {@link Vector3d} with the given {@code y} added to the current {@link #y}
	 */
	public Vector3d addY(double y) {
		return new Vector3d(this.x, this.y + y, this.z);
	}

	/**
	 * Helps simplify the process of adding a value to only one of the points of the Vector, rather
	 * than having to use {@link #add(double)}.
	 *
	 * @param z The amount to add to the {@link #z}
	 * @return The {@link Vector3d} with the given {@code z} added to the current {@link #z}
	 */
	public Vector3d addZ(double z) {
		return new Vector3d(this.x, this.y, this.z + z);
	}

	public Vector3d add(double amount) {
		return new Vector3d(this.x + amount, this.y + amount, this.z + amount);
	}

	public Vector3d add(Vector3d vec) {
		return add(vec.x, vec.y, vec.z);
	}

	public Vector3d add(double x, double y, double z) {
		return new Vector3d(this.x + x, this.y + y, this.z + z);
	}

	public Vector3d sub(double amount) {
		return new Vector3d(this.x - amount, this.y - amount, this.z - amount);
	}

	public Vector3d sub(Vector3d vec) {
		return new Vector3d(this.x - vec.x, this.y - vec.y, this.z - vec.z);
	}

	public Vector3d scale(double amount) {
		return new Vector3d(this.x * amount, this.y * amount, this.z * amount);
	}

	public Vector3d div(double amount) {
		return new Vector3d(this.x / amount, this.y / amount, this.z / amount);
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
		return new Vector3d(x, this.y ,this.z);
	}

	public Vector3d setY(double y) {
		return new Vector3d(this.x, y, this.z);
	}

	public Vector3d setZ(double z) {
		return new Vector3d(this.x, this.y, z);
	}

	public Vector3d set(double x, double y, double z) {
		return new Vector3d(x, y, z);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Vector3d)) return false;
		Vector3d other = (Vector3d)o;
		return other.x == this.x && other.y == this.y && other.z == this.z;
	}

	public Vector3i toInt() {
		return new Vector3i(this);
	}
}
