package net.halalaboos.mcwrapper.impl;

import net.halalaboos.mcwrapper.api.opengl.GLState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

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
	public void enablePolygonOffset() {
		GlStateManager.enablePolygonOffset();
	}

	@Override
	public void disablePolygonOffset() {
		GlStateManager.disablePolygonOffset();
	}

	@Override
	public void bindTexture(int texId) {
		GlStateManager.bindTexture(texId);
	}

	@Override
	public void disableBlend() {
		GlStateManager.disableBlend();
	}

	private final Tessellator tessellator = Tessellator.getInstance();

	@Override
	public void drawTexture(float x, float y, float width, float height, float u, float v, float t, float s) {
		VertexBuffer renderer = tessellator.getBuffer();
		renderer.begin(GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
		renderer.pos(x + width, y, 0F).tex(t, v).endVertex();
		renderer.pos(x, y, 0F).tex(u, v).endVertex();
		renderer.pos(x, y + height, 0F).tex(u, s).endVertex();
		renderer.pos(x, y + height, 0F).tex(u, s).endVertex();
		renderer.pos(x + width, y + height, 0F).tex(t, s).endVertex();
		renderer.pos(x + width, y, 0F).tex(t, v).endVertex();
		tessellator.draw();
	}
}
