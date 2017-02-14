package net.halalaboos.mcwrapper.api.util;

public class Vector3i {

	public final int x;
	public final int y;
	public final int z;

	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3i add(int amount) {
		return new Vector3i(this.x + amount, this.y + amount, this.z + amount);
	}

	public Vector3i sub(int amount) {
		return new Vector3i(this.x - amount, this.y - amount, this.z - amount);
	}

	public Vector3d toDouble() {
		return new Vector3d(this.x, this.y, this.z);
	}
}
