package net.halalaboos.huzuni.api.util;

import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.minecraft.util.math.BlockPos;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Math utilities
 * */
public final class MathUtils {

	private MathUtils() {
		
	}
	
	public static double getDistance(BlockPos position) {
        return getDistance(position.getX(), position.getY(), position.getZ());
	}

	public static double getDistance(double x, double y, double z) {
		return getPlayer().getDistanceTo(new Vector3d(x, y, z));
	}
	
	public static double interpolate(double prev, double cur, double delta) {
		return prev + ((cur - prev) * delta);
	}

    /**
     * Clamps the given input between the maximum and -maximum
     */
	public static float clamp(float input, float max) {
        return clamp(input, max, -max);
    }

    /**
     * Clamps the input between the maximum and minimum values.
     * */
    public static float clamp(float input, float max, float min) {
        if (input > max)
            input = max;
        if (input < min)
            input = min;
        return input;
    }
}
