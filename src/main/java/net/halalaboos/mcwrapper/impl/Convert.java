package net.halalaboos.mcwrapper.impl;

import net.halalaboos.mcwrapper.api.util.math.AABB;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class Convert {

	/**
	 * Converts the Minecraft Bounding Box class to the MCWrapper one ({@link AABB}).
	 */
	public static AABB from(AxisAlignedBB bb) {
		return new AABB(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
	}

	public static Vector3i from(BlockPos pos) {
		return new Vector3i(pos.getX(), pos.getY(), pos.getZ());
	}

	public static BlockPos to(Vector3i pos) {
		return new BlockPos(pos.x, pos.y, pos.z);
	}
}
