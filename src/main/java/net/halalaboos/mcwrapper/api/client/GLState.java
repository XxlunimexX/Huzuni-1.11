package net.halalaboos.mcwrapper.api.client;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

//todo - finish documentation
public interface GLState {

	default void rotate(float angle, float x, float y, float z) {
		glRotatef(angle, x, y, z);
	}

	default void translate(double x, double y, double z) {
		glTranslated(x, y, z);
	}

	default void pushMatrix() {
		glPushMatrix();
	}

	default void popMatrix() {
		glPopMatrix();
	}

	/**
	 * Sets the line width for rendering.
	 *
	 * @param width The line width
	 */
	default void lineWidth(float width) {
		glLineWidth(width);
	}

	/**
	 * Sets the color for rendering.
	 *
	 * @param red The red value (0 -> 1)
	 * @param green The green value (0 -> 1)
	 * @param blue The blue value (0 -> 1)
	 * @param alpha The alpha value, or opacity (0 -> 1)
	 */
	void color(float red, float green, float blue, float alpha);

	void disableTexture2D();

	void enableTexture2D();

	void enableDepth();

	void disableDepth();

	void enableAlpha();

	void disableAlpha();

	void depthMask(boolean depthMask);

	void blendFunc(int src, int dst);

	void enableBlend();

	void enablePolygonOffset();

	void disablePolygonOffset();

	/**
	 * Sets the scale for rendering.
	 *
	 * @param x The x-scale
	 * @param y The y-scale
	 * @param z The z-scale
	 */
	default void scale(double x, double y, double z) {
		GL11.glScaled(x, y, z);
	}

	void bindTexture(int texId);

	void disableBlend();

}
