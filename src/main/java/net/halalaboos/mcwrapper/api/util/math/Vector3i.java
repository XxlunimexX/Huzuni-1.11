package net.halalaboos.mcwrapper.api.util.math;

import net.halalaboos.mcwrapper.api.util.enums.Face;

/**
 * Represents a three-dimensional point, but uses integers rather than doubles like the {@link Vector3d} class does.
 *
 * This is useful to represent points like the location of a {@link net.halalaboos.mcwrapper.api.block.Block} in the
 * {@link net.halalaboos.mcwrapper.api.world.World}.
 */
public class Vector3i {

	/**
	 * The x-coordinate of the vector.
	 */
	private final int x;

	/**
	 * The y-coordinate of the vector.
	 */
	private final int y;

	/**
	 * The z-coordinate of the vector.
	 */
	private final int z;

	/**
	 * A {@link Vector3i} with the {@link #x}, {@link #y}, {@link #z} set to 0.
	 *
	 * Only purpose is to avoid having to write this code repeatedly.
	 */
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

	/**
	 * Returns a new {@link Vector3i} from a {@link Vector3d}, in the case where one would want to cast the points
	 * of a {@link Vector3d} to the rounded values.
	 *
	 * @param vec The input vector
	 */
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

	/**
	 * Adds the given {@code amount} to the {@link #x}, {@link #y}, and {@link #z}.
	 *
	 * @param amount The amount to add
	 * @return The {@link Vector3i} with the {@code amount} added to all of the points.
	 */
	public Vector3i add(int amount) {
		return new Vector3i(this.x + amount, this.y + amount, this.z + amount);
	}

	/**
	 * Adds the given {@code x}, {@code y}, and {@code z} points to the vector.  This works the same as
	 * {@link #add(Vector3i)}, but requires less code.
	 *
	 * @param x The amount to add to the {@link #x}
	 * @param y The amount to add to the {@link #y}
	 * @param z The amount to add to the {@link #z}
	 * @return The {@link Vector3i} with points added.
	 */
	public Vector3i add(int x, int y, int z) {
		return new Vector3i(this.x + x, this.y + y, this.z + z);
	}

	/**
	 * Adds the {@link Vector3i#x}, {@link Vector3i#y}, and {@link Vector3i#z} of the {@code vec} to this vector.
	 *
	 * @param vec The vector to add to this one
	 * @return The {@link Vector3i} with the {@code vec}'s point coordinates added.
	 */
	public Vector3i add(Vector3i vec) {
		return add(vec.x, vec.y, vec.z);
	}

	public Vector3i up() {
		return offset(Face.UP);
	}

	/**
	 * Adds the {@code face}'s {@link Face#getOffsetVector()} to the vector.
	 *
	 * @param face The {@link Face} type.
	 * @return The offset {@link Vector3i}
	 */
	public Vector3i offset(Face face) {
		return add(face.getOffsetVector());
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
