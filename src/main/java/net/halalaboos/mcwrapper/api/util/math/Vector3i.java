package net.halalaboos.mcwrapper.api.util.math;

public class Vector3i {

	private int x;
	private int y;
	private int z;

	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3i(double x, double y, double z) {
		this.x = (int)x;
		this.y = (int)y;
		this.z = (int)z;
	}

	public Vector3i(Vector3d vec) {
		this(vec.getX(), vec.getY(), vec.getZ());
	}

	/**
	 * Helps simplify the process of adding a value to only one of the points of the Vector, rather
	 * than having to use {@link #add(int)}.
	 *
	 * @param x The amount to add to the {@link #x}
	 * @return The {@link Vector3i} with the given {@code x} added to the current {@link #x}
	 */
	public Vector3i addX(int x) {
		this.x += x;
		return this;
	}

	/**
	 * Helps simplify the process of adding a value to only one of the points of the Vector, rather
	 * than having to use {@link #add(int)}.
	 *
	 * @param y The amount to add to the {@link #y}
	 * @return The {@link Vector3i} with the given {@code y} added to the current {@link #y}
	 */
	public Vector3i addY(int y) {
		this.y += y;
		return this;
	}

	/**
	 * Helps simplify the process of adding a value to only one of the points of the Vector, rather
	 * than having to use {@link #add(int)}.
	 *
	 * @param z The amount to add to the {@link #z}
	 * @return The {@link Vector3i} with the given {@code z} added to the current {@link #z}
	 */
	public Vector3i addZ(int z) {
		this.z += z;
		return this;
	}

	public Vector3i add(int amount) {
		this.x += amount;
		this.y += amount;
		this.z += amount;
		return this;
	}

	public Vector3i add(Vector3i vec) {
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
		return this;
	}

	public Vector3i sub(int amount) {
		this.x -= amount;
		this.y -= amount;
		this.z -= amount;
		return this;
	}

	public Vector3i sub(Vector3i vec) {
		this.x -= vec.x;
		this.y -= vec.y;
		this.z -= vec.z;
		return this;
	}

	public Vector3i copy() {
		return new Vector3i(x, y, z);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public Vector3i setX(int x) {
		this.x = x;
		return this;
	}

	public Vector3i setY(int y) {
		this.y = y;
		return this;
	}

	public Vector3i setZ(int z) {
		this.z = z;
		return this;
	}

	public Vector3d toDouble() {
		return new Vector3d(this.x, this.y, this.z);
	}
}
