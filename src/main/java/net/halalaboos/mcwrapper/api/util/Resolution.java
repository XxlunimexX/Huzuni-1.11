package net.halalaboos.mcwrapper.api.util;

public class Resolution {

	/**
	 * The un-scaled screen width and height.
	 */
	public final int width, height;

	/**
	 * The scaled screen width and height.
	 */
	public int scaledWidth, scaledHeight;

	public Resolution(int width, int height, int scaleSetting) {
		this.width = width;
		this.height = height;
		scale(scaleSetting);
	}

	/**
	 * Sets the scaled width and height based on the given scale setting.
	 */
	public void scale(int scaleSetting) {
		int scaleFactor = 1;
		if (scaleSetting == 0) {
			scaleSetting = 1000;
		}
		while (scaleFactor < scaleSetting && width / (scaleFactor + 1) >= 320
				&& height / (scaleFactor + 1) >= 240) {
			scaleFactor++;
		}
		if (scaleFactor % 2 != 0 && scaleFactor != 1) {
			scaleFactor--;
		}
		this.scaledWidth = width / scaleFactor;
		this.scaledHeight = height / scaleFactor;
	}
}
