package net.halalaboos.mcwrapper.api.client;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

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

	default void lineWidth(float width) {
		glLineWidth(width);
	}

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

	default void scale(double x, double y, double z) {
		GL11.glScaled(x, y, z);
	}

	void bindTexture(int texId);

	void disableBlend();

}
