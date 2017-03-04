package net.halalaboos.mcwrapper.impl;

import net.halalaboos.mcwrapper.api.client.GLState;
import net.minecraft.client.renderer.GlStateManager;

public class GLStateImpl implements GLState {

	private static GLStateImpl INSTANCE = new GLStateImpl();

	public static GLStateImpl getInstance() {
		return INSTANCE;
	}

	@Override
	public void color(float red, float green, float blue, float alpha) {
		GlStateManager.color(red, green, blue, alpha);
	}
}
