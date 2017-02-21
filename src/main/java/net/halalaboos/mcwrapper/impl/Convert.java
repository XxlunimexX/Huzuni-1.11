package net.halalaboos.mcwrapper.impl;

import net.halalaboos.mcwrapper.api.util.math.AABB;
import net.minecraft.util.math.AxisAlignedBB;

public class Convert {

	/**
	 * Converts the Minecraft Bounding Box class to the MCWrapper one ({@link AABB}).
	 */
	public static AABB from(AxisAlignedBB bb) {
		return new AABB(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
	}

}
