package net.halalaboos.mcwrapper.api.util.math;

public class AABB {

	public final Vector3d min;
	public final Vector3d max;

	public AABB(Vector3d min, Vector3d max) {
		this.min = min;
		this.max = max;
	}

	public AABB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		this(new Vector3d(minX, minY, minZ), new Vector3d(maxX, maxY, maxZ));
	}

	public AABB copy() {
		return new AABB(this.min, this.max);
	}

	public AABB offset(Vector3d offset) {
		return new AABB(this.min.add(offset), this.max.add(offset));
	}

	public AABB offset(double x, double y, double z) {
		return offset(new Vector3d(x, y, z));
	}

	public AABB addVector(Vector3d vec) {
		double x = vec.getX(), y = vec.getY(), z = vec.getZ();
		double minX = min.getX(), minY = min.getY(), minZ = min.getZ();
		double maxX = max.getX(), maxY = max.getY(), maxZ = max.getZ();

		if (x < 0) minX += x; else if (x > 0) maxX += x;
		if (y < 0) minY += y; else if (y > 0) maxY += y;
		if (z < 0) minZ += z; else if (z > 0) maxZ += z;

		return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public AABB grow(double x, double y, double z) {
		return new AABB(this.min.sub(new Vector3d(x, y, z)), this.max.add(new Vector3d(x, y, z)));
	}

	public AABB grow(double amount) {
		return this.grow(amount, amount, amount);
	}

	public String toString() {
		return "box[" + this.min.getX() + ", " + this.min.getY() + ", " + this.min.getZ() + " -> " + this.max.getX() + ", " + this.max.getY() + ", " + this.max.getZ() + "]";
	}
}
