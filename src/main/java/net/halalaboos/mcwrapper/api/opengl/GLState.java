package net.halalaboos.mcwrapper.api.opengl;

import net.halalaboos.mcwrapper.api.MCWrapper;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

/**
 * Wrapper for Minecraft's OpenGL state manager thing.
 *
 * Avoid using this or {@link MCWrapper#getGLStateManager()}, and instead refer to {@link OpenGL#GL} for usage.
 */
public interface GLState {

	default void rotate(float angle, float x, float y, float z) {
		glRotatef(angle, x, y, z);
	}

	default void translate(double x, double y, double z) {
		glTranslated(x, y, z);
	}

	/**
	 * Creates a new matrix.
	 */
	default void pushMatrix() {
		glPushMatrix();
	}

	/**
	 * Removes (or 'pops') the matrix.
	 */
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
	 * Scales the current matrix by the specified amount.
	 *
	 * @param x The amount to scale the x-axis.
	 * @param y The amount to scale the y-axis.
	 * @param z The amount to scale the z-axis.
	 */
	default void scale(double x, double y, double z) {
		GL11.glScaled(x, y, z);
	}

	void bindTexture(int texId);

	void disableBlend();

	/**
	 * For now, we will be using Minecraft's tessellator for texture rendering until I get around figuring out
	 * why Feather's isn't working.  I recommend you don't rely on this method too much.
	 */
	void drawTexture(float x, float y, float width, float height, float u, float v, float t, float s);
}
