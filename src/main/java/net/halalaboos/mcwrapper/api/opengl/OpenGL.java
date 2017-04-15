package net.halalaboos.mcwrapper.api.opengl;

import org.lwjgl.opengl.GL11;

import static net.halalaboos.mcwrapper.api.MCWrapper.getGLStateManager;

/**
 * All credits go to Flare devs for this idea - just a simple wrapper for GL-related functions with a builder-styled
 * pattern to make things that would take multiple lines instead take only one.  For example:
 * <p>
 *     <code>
 *         GL11.glPushMatrix();
 *         GL11.glEnable(GL11.GL_BLEND);
 *         GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
 *         ...
 *     </code>
 * </p>
 * Would instead be something like:
 * <p>
 *     <code>
 *         GL.push().blend().blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
 *     </code>
 * </p>
 * Though if you don't like how that looks, you're also able to just do everything in separate lines too!
 *
 * To use this class, just import {@link #GL} statically, or you could just call {@code OpenGL.GL.xxx} if you don't
 * mind things looking a little messy.
 *
 * @author b
 *
 * TODO: Maybe just remove the GLState interface and use this instead.
 * TODO: More documentation for the other methods
 */
public enum OpenGL {
	/**
	 * The instance of this class, use this for invoking the methods.  It's recommended to just import this statically
	 * so you can just type {@code GL.push()} instead of {@code OpenGL.GL.push()}.
	 */
	GL;

	/**
	 * Invokes {@link GL11#glEnableClientState(int)} or {@link GL11#glDisableClientState(int)} depending on
	 * whether or not {@code enable} is true, with the specified {@code state} as the cap.
	 *
 	 * @param state The cap/state to enable/disable
	 * @param enable Whether or not to enable/disable this state
	 */
	public OpenGL state(int state, boolean enable) {
		if (enable) GL11.glEnableClientState(state);
		else GL11.glDisableClientState(state);
		return this;
	}

	public OpenGL translate(double x, double y, double z) {
		GL11.glTranslated(x, y, z);
		return this;
	}

	/**
	 * Sets the line width to the specified {@code width}.
	 *
	 * @param width The line width.
	 */
	public OpenGL width(float width) {
		getGLStateManager().lineWidth(width);
		return this;
	}

	/**
	 * Creates a new matrix.
	 */
	public OpenGL push() {
		getGLStateManager().pushMatrix();
		return this;
	}

	/**
	 * Removes (or 'pops') the matrix.
	 */
	public OpenGL pop() {
		getGLStateManager().popMatrix();
		return this;
	}

	/**
	 * Scales the current matrix by the specified amount.
	 *
	 * @param x The amount to scale the x-axis.
	 * @param y The amount to scale the y-axis.
	 * @param z The amount to scale the z-axis.
	 */
	public OpenGL scale(double x, double y, double z) {
		getGLStateManager().scale(x, y, z);
		return this;
	}

	/**
	 * Enables (or disables) {@link GL11#GL_ALPHA_TEST} depending on the {@code state}.
	 *
	 * @param state Whether or not to enable
	 */
	public OpenGL alpha(boolean state) {
		if (state) getGLStateManager().enableAlpha(); else getGLStateManager().disableAlpha();
		return this;
	}

	/**
	 * Enables (or disables) {@link GL11#GL_BLEND} depending on the {@code state}.
	 *
	 * @param state Whether or not to enable
	 */
	public OpenGL blend(boolean state) {
		if (state) getGLStateManager().enableBlend(); else getGLStateManager().disableBlend();
		return this;
	}

	public OpenGL blendFunc(int src, int dst) {
		getGLStateManager().blendFunc(src, dst);
		return this;
	}

	/**
	 * Sets the current RGBA values for rendering.
	 *
	 * @param r The red value (0..1)
	 * @param g The green value (0..1)
	 * @param b The blue value (0..1)
	 * @param a The alpha value (0..1)
	 */
	public OpenGL color(float r, float g, float b, float a) {
		getGLStateManager().color(r, g, b, a);
		return this;
	}
}
