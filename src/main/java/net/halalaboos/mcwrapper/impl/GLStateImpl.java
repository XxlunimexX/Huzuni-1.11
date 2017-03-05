package net.halalaboos.mcwrapper.impl;

import net.halalaboos.mcwrapper.api.client.GLState;
import net.minecraft.client.renderer.GlStateManager;

/**
 * NOTE - THIS IS ACTUALLY NOT REALLY NECESSARY.
 * WILL BE REDONE ASAP
 */
public class GLStateImpl implements GLState {

	@Override
	public void color(float red, float green, float blue, float alpha) {
		GlStateManager.color(red, green, blue, alpha);
	}

	@Override
	public void disableTexture2D() {
		GlStateManager.disableTexture2D();
	}

	@Override
	public void enableTexture2D() {
		GlStateManager.enableTexture2D();
	}

	@Override
	public void enableDepth() {
		GlStateManager.enableDepth();
	}

	@Override
	public void disableDepth() {
		GlStateManager.disableDepth();
	}

	@Override
	public void enableAlpha() {
		GlStateManager.enableAlpha();
	}

	@Override
	public void disableAlpha() {
		GlStateManager.disableAlpha();
	}

	@Override
	public void depthMask(boolean depthMask) {
		GlStateManager.depthMask(depthMask);
	}

	@Override
	public void blendFunc(int src, int dst) {
		GlStateManager.blendFunc(src, dst);
	}

	@Override
	public void enableBlend() {
		GlStateManager.enableBlend();
	}

	@Override
	public void bindTexture(int texId) {
		GlStateManager.bindTexture(texId);
	}

	@Override
	public void disableBlend() {
		GlStateManager.disableBlend();
	}
}
