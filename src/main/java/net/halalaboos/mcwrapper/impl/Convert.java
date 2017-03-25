package net.halalaboos.mcwrapper.impl;

import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.util.DigAction;
import net.halalaboos.mcwrapper.api.util.math.AABB;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/**
 * Utility for quickly converting MCWrapper data classes to the Minecraft ones, or vice-versa.
 * <p>This is only used for the Mixins.</p>
 */
public class Convert {

	/**
	 * Converts the Minecraft Bounding Box class to the MCWrapper one ({@link AABB}).
	 */
	public static AABB from(AxisAlignedBB bb) {
		return new AABB(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
	}

	public static AxisAlignedBB to(AABB bb) {
		return new AxisAlignedBB(bb.min.getX(), bb.min.getY(), bb.min.getZ(), bb.max.getX(), bb.max.getY(), bb.max.getZ());
	}

	public static Vector3i from(BlockPos pos) {
		return new Vector3i(pos.getX(), pos.getY(), pos.getZ());
	}

	public static BlockPos to(Vector3i pos) {
		return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
	}

	public static PotionEffect to(net.halalaboos.mcwrapper.api.potion.PotionEffect effect) {
		return (PotionEffect)(Object)effect;
	}

	public static Potion to(net.halalaboos.mcwrapper.api.potion.Potion potion) {
		return Potion.getPotionById(potion.id());
	}

	public static EnumHand to(Hand hand) {
		return EnumHand.values()[hand.ordinal()];
	}

	public static CPacketPlayerDigging.Action to(DigAction action) {
		return CPacketPlayerDigging.Action.values()[action.ordinal()];
	}
}
