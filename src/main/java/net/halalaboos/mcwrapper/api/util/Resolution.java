package net.halalaboos.mcwrapper.api.util;

import net.halalaboos.mcwrapper.api.MCWrapper;

public class Resolution {

	/**
	 * The un-scaled screen width and height.
	 */
	public final int width, height;

	/**
	 * The scaled screen width and height.
	 */
	public int scaledWidth, scaledHeight;

	public int factor;

	public Resolution(int width, int height, int scaleSetting) {
		this.width = width;
		this.height = height;
		scale(scaleSetting);
	}

	/**
	 * Sets the scaled width and height based on the given scale setting.
	 */
	public void scale(int scaleSetting) {
		this.scaledWidth = width;
		this.scaledHeight = height;
		this.factor = 1;
		boolean unicode = MCWrapper.getMinecraft().useUnicode();
		if (scaleSetting == 0) {
			scaleSetting = 1000;
		}
		while (this.factor < scaleSetting && this.scaledWidth / (this.factor + 1) >= 320 &&
				this.scaledHeight / (this.factor + 1) >= 240) {
			++this.factor;
		}
		if (unicode && this.factor % 2 != 0 && this.factor != 1) {
			--this.factor;
		}
		this.scaledWidth = this.scaledWidth / factor;
		this.scaledHeight = this.scaledHeight / factor;
	}
}
