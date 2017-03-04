package net.halalaboos.mcwrapper.api.client;

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

	void color(float red, float green, float blue, float alpha);

}
