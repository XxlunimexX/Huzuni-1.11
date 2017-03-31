package net.halalaboos.mcwrapper.api.util.math;

/**
 * Math methods from Minecraft with the faster libgdx alternatives when applicable.
 */
public class MathUtils {

	private static final int BIG_ENOUGH_INT = 16 * 1024;
	private static final double BIG_ENOUGH_FLOOR = BIG_ENOUGH_INT;

	private static final double CEIL = 0.9999999;
	private static final double BIG_ENOUGH_CEIL = 16384.999999999996;
	private static final double BIG_ENOUGH_ROUND = BIG_ENOUGH_INT + 0.5f;

	private static final int SIN_BITS = 14; // 16KB. Adjust for accuracy.
	private static final int SIN_MASK = ~(-1 << SIN_BITS);
	private static final int SIN_COUNT = SIN_MASK + 1;

	private static final float PI = 3.1415927f;

	private static final float radFull = PI * 2;
	private static final float degFull = 360;

	private static final float radToIndex = SIN_COUNT / radFull;
	private static final float degToIndex = SIN_COUNT / degFull;
	private static final float degreesToRadians = PI / 180;

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

	public static int ceil(float value) {
		return BIG_ENOUGH_INT - (int)(BIG_ENOUGH_FLOOR - value);
	}

	public static int ceil(double value) {
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

	public static float sin(float radians) {
		return Sin.table[(int)(radians * radToIndex) & SIN_MASK];
	}

	public static float sin(double radians) {
		return Sin.table[(int)(radians * radToIndex) & SIN_MASK];
	}

	public static float cos(float radians) {
		return Sin.table[(int)((radians + PI / 2) * radToIndex) & SIN_MASK];
	}

	public static float cos(double radians) {
		return Sin.table[(int)((radians + PI / 2) * radToIndex) & SIN_MASK];
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

	public static int abs(int input) {
		return input >= 0 ? input : -input;
	}

	private static class Sin {
		static final float[] table = new float[SIN_COUNT];

		static {
			for (int i = 0; i < SIN_COUNT; i++)
				table[i] = (float)Math.sin((i + 0.5f) / SIN_COUNT * radFull);
			for (int i = 0; i < 360; i += 90)
				table[(int)(i * degToIndex) & SIN_MASK] = (float)Math.sin(i * degreesToRadians);
		}
	}
}
