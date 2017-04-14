package net.halalaboos.huzuni;

import net.halalaboos.huzuni.gui.screen.HuzuniSettingsMenu;
import net.halalaboos.mcwrapper.api.event.render.HUDRenderEvent;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import org.lwjgl.opengl.GL32;
import pw.knx.feather.tessellate.GrowingTess;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static net.halalaboos.mcwrapper.api.MCWrapper.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Handles the renderers within the client.
 * */
public final class RenderManager {

	private final List<Renderer> worldRenderers = new ArrayList<>();
	
	private final List<Renderer> overlayRenderers = new ArrayList<>();
	
	private final Huzuni huzuni;

	private final GrowingTess lineTess = new GrowingTess(4);
	
	public RenderManager(Huzuni huzuni) {
		this.huzuni = huzuni;

		getEventManager().subscribe(HUDRenderEvent.class, event -> {
			if (!event.isDebugEnabled()) {
				if (!(getMinecraft().getScreen() instanceof HuzuniSettingsMenu)) {
					huzuni.guiManager.widgetManager.render();
					getGLStateManager().disableBlend();
				}
				huzuni.renderManager.renderOverlay(event.getDelta());
			}
		});
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
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		getGLStateManager().lineWidth(huzuni.settings.lineSize.getValue());
		lineTess.bind().pass(GL_LINES).reset();
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);
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
		Vector3d start = new Vector3d(0, 0, 1)
				.rotatePitch(-(float) Math.toRadians(getPlayer().getPitch()))
				.rotateYaw(-(float) Math.toRadians(getPlayer().getYaw()));
		lineTess.color(r, g, b, a)
				.vertex((float)start.getX(), (float)start.getY() + getPlayer().getEyeHeight(), (float)start.getZ())
				.vertex(x, y, z);
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
