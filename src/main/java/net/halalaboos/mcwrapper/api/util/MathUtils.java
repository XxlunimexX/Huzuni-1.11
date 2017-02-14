package net.halalaboos.mcwrapper.api.util;

/**
 * Math methods from Minecraft with the faster libgdx alternatives when applicable.
 */
public class MathUtils {

	public static int floor(double val) {
		return (int)(val + 1024.0D) - 1024;
	}

	public static float wrapDegrees(float value) {
		value = value % 360.0F;
		if (value >= 180.0F) value -= 360.0F;
		if (value < -180.0F) value += 360.0F;
		return value;
	}

	public static float wrapDegrees(double value) {
		return wrapDegrees((float)value);
	}

	static public int ceil(float value) {
		return BIG_ENOUGH_INT - (int)(BIG_ENOUGH_FLOOR - value);
	}

	public static float sqrt(float value) {
		return (float)Math.sqrt((double)value);
	}

	public static float sqrt(double value) {
		return (float)Math.sqrt(value);
	}

	public static double interpolate(double prev, double cur, double delta) {
		return prev + ((cur - prev) * delta);
	}

	static private final int BIG_ENOUGH_INT = 16 * 1024;
	static private final double BIG_ENOUGH_FLOOR = BIG_ENOUGH_INT;
	static private final double CEIL = 0.9999999;
	static private final double BIG_ENOUGH_CEIL = 16384.999999999996;
	static private final double BIG_ENOUGH_ROUND = BIG_ENOUGH_INT + 0.5f;
}
