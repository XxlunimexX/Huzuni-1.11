package net.halalaboos.huzuni;

import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL32;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static net.halalaboos.mcwrapper.api.MCWrapper.getGLStateManager;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;
import static org.lwjgl.opengl.GL11.*;

/**
 * Handles the renderers within the client.
 * */
public final class RenderManager {

	private final List<Renderer> worldRenderers = new ArrayList<>();
	
	private final List<Renderer> overlayRenderers = new ArrayList<>();
	
	private List<float[]> lines = new ArrayList<>();
	
	private final Huzuni huzuni;
	
	public RenderManager(Huzuni huzuni) {
		this.huzuni = huzuni;
	}

	/**
     * Renders the world renderers.
     * */
	public void renderWorld(float partialTicks) {
		for (Renderer renderer : worldRenderers) {
			renderer.render(partialTicks);
		}
	}

	/**
	 * Enables the gl constants required for 3-d rendering within the world.
	 * */
	public void enableGlConstants() {
    	getGLStateManager().pushMatrix();
		getGLStateManager().disableAlpha();
		getGLStateManager().enableBlend();
		getGLStateManager().blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		getGLStateManager().disableTexture2D();
		getGLStateManager().disableDepth();
		getGLStateManager().depthMask(false);
		if (huzuni.settings.lineSmooth.isEnabled())
		    glEnable(GL_LINE_SMOOTH);
		else
			glDisable(GL_LINE_SMOOTH);
		if (huzuni.settings.infiniteLines.isEnabled())
    		glEnable(GL32.GL_DEPTH_CLAMP);
		getGLStateManager().lineWidth(huzuni.settings.lineSize.getValue());
	}

	/**
     * Disables the gl constants required for 3-d rendering within the world.
     * */
	public void disableGlConstants() {
		if (huzuni.settings.lineSmooth.isEnabled())
			glDisable(GL_LINE_SMOOTH);
		if (huzuni.settings.infiniteLines.isEnabled())
        	glDisable(GL32.GL_DEPTH_CLAMP);
		getGLStateManager().enableTexture2D();
		getGLStateManager().enableDepth();
		getGLStateManager().depthMask(true);
		getGLStateManager().enableAlpha();
		getGLStateManager().popMatrix();
	}

	/**
     * Renders the lines which have been added.
     * */
	public void renderLines() {
		Tessellator tessellator = Tessellator.getInstance();
    	VertexBuffer vertexBuffer = tessellator.getBuffer();
    	vertexBuffer.begin(GL_LINES, DefaultVertexFormats.POSITION_COLOR);
		Vector3d start = new Vector3d(0, 0, 1)
				.rotatePitch(-(float) Math.toRadians(getPlayer().getPitch()))
				.rotateYaw(-(float) Math.toRadians(getPlayer().getYaw()));
		for (float[] point : lines) {
	    	vertexBuffer.pos(start.getX(), start.getY() + Minecraft.getMinecraft().player.getEyeHeight(), start.getZ()).color(point[3], point[4], point[5], point[6]).endVertex();
	    	vertexBuffer.pos(point[0], point[1], point[2]).color(point[3], point[4], point[5], point[6]).endVertex();
		}
    	tessellator.draw();
    	lines.clear();
	}

	/**
     * Renders the overlay renderers.
     * */
	public void renderOverlay(float partialTicks) {
		for (Renderer renderer : overlayRenderers) {
			renderer.render(partialTicks);
		}
	}

	/**
     * Adds line information that will be later used in rendering. <br/>
	 * Lines that are rendered between the camera and any given point must be rendered separately from the normal world render function to avoid view-bobbing.
	 * */
	public void addLine(float x, float y, float z, Color color, float alpha) {
		addLine(x, y, z, (float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, alpha);
	}

    /**
     * Adds line information that will be later used in rendering. <br/>
     * Lines that are rendered between the camera and any given point must be rendered separately from the normal world render function to avoid view-bobbing.
     * */
	public void addLine(float x, float y, float z, Color color) {
		addLine(x, y, z, (float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, (float) color.getAlpha() / 255F);
	}

    /**
     * Adds line information that will be later used in rendering. <br/>
     * Lines that are rendered between the camera and any given point must be rendered separately from the normal world render function to avoid view-bobbing.
     * */
	public void addLine(float x, float y, float z, float r, float g, float b, float a) {
		lines.add(new float[] { x, y, z, r, g, b, a });
	}

	public boolean addWorldRenderer(Renderer renderer) {
		return worldRenderers.add(renderer);
	}

	public boolean removeWorldRenderer(Renderer renderer) {
		return worldRenderers.remove(renderer);
	}
	
	public boolean addOverlayRenderer(Renderer renderer) {
		return overlayRenderers.add(renderer);
	}

	public boolean removeOverlayRenderer(Renderer renderer) {
		return overlayRenderers.remove(renderer);
	}

	/**
     * Renderer used within both world and overlay rendering.
     * */
	@FunctionalInterface
	public interface Renderer {
		void render(float partialTicks);
	}
}
