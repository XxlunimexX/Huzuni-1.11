package net.halalaboos.mcwrapper.api.util.math;

import net.halalaboos.mcwrapper.api.util.enums.Face;

public class Vector3i {

	private final int x;
	private final int y;
	private final int z;

	public static final Vector3i ZERO = new Vector3i(0, 0, 0);

	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3i(double x, double y, double z) {
		this.x = MathUtils.floor(x);
		this.y = MathUtils.floor(y);
		this.z = MathUtils.floor(z);
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
		return new Vector3i(this.x + x, this.y, this.z);
	}

	/**
	 * Helps simplify the process of adding a value to only one of the points of the Vector, rather
	 * than having to use {@link #add(int)}.
	 *
	 * @param y The amount to add to the {@link #y}
	 * @return The {@link Vector3i} with the given {@code y} added to the current {@link #y}
	 */
	public Vector3i addY(int y) {
		return new Vector3i(this.x, this.y + y, this.z);
	}

	/**
	 * Helps simplify the process of adding a value to only one of the points of the Vector, rather
	 * than having to use {@link #add(int)}.
	 *
	 * @param z The amount to add to the {@link #z}
	 * @return The {@link Vector3i} with the given {@code z} added to the current {@link #z}
	 */
	public Vector3i addZ(int z) {
		return new Vector3i(this.x, this.y, this.z + z);
	}

	public Vector3i add(int amount) {
		return new Vector3i(this.x + amount, this.y + amount, this.z + amount);
	}

	public Vector3i add(int x, int y, int z) {
		return new Vector3i(this.x + x, this.y + y, this.z + z);
	}

	public Vector3i add(Vector3i vec) {
		return add(vec.x, vec.y, vec.z);
	}

	public Vector3i sub(int amount) {
		return new Vector3i(this.x - amount, this.y - amount, this.z - amount);
	}

	public Vector3i sub(Vector3i vec) {
		return new Vector3i(this.x - vec.x, this.y - vec.y, this.z - vec.z);
	}

	public Vector3i up() {
		return offset(Face.UP);
	}

	public Vector3i offset(Face face) {
		return copy().add(face.getOffsetX(), face.getOffsetY(), face.getOffsetZ());
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
		return new Vector3i(x, this.y, this.z);
	}

	public Vector3i setY(int y) {
		return new Vector3i(this.x, y, this.z);
	}

	public Vector3i setZ(int z) {
		return new Vector3i(this.x, this.y, z);
	}

	public Vector3d toDouble() {
		return new Vector3d(this.x, this.y, this.z);
	}

	@Override
	public String toString() {
		return x + ", " + y + ", " + z;
	}
}
