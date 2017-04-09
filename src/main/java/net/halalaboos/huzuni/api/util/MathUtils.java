package net.halalaboos.huzuni.api.util;

/**
 * Math utilities
 * */
public final class MathUtils {

	private MathUtils() {
		
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
