package net.halalaboos.huzuni.mod.visual;

import net.halalaboos.huzuni.RenderManager.Renderer;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.mod.command.impl.annotation.CommandPointer;
import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.api.util.gl.Texture;
import net.halalaboos.huzuni.gui.Notification.NotificationType;
import net.halalaboos.huzuni.mod.movement.Freecam;
import net.halalaboos.mcwrapper.api.event.network.PacketReadEvent;
import net.halalaboos.mcwrapper.api.network.packet.server.HealthUpdatePacket;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import pw.knx.feather.tessellate.GrowingTess;

import java.util.ArrayList;
import java.util.List;

import static net.halalaboos.mcwrapper.api.MCWrapper.getGLStateManager;
import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;
import static net.halalaboos.mcwrapper.api.opengl.OpenGL.GL;
import static org.lwjgl.opengl.GL11.*;

/**
 * Tracks the player's position and renders bread images at each point.
 * */
public class Breadcrumb extends BasicMod implements Renderer {

	private final Texture breadIcon = new Texture("bread.png");

	private final List<Vector3i> points = new ArrayList<>();
	private Vector3i lastPosition = new Vector3i(0, 0, 0);
	
	public final Value opacity = new Value("Opacity", "%", 0F, 50F, 100F, 1F, "Opacity of the icon.");
	public final Value bounce = new Value("Bounce" ,0F, 0F, 10F, "Amount the icon will bounce.");
	public final Value distance = new Value("Distance", " blocks", 1F, 15F, 30F, 1F, "Distance between each point.");

    public final Toggleable lines = new Toggleable("Lines", "Render lines between each point.");
    public final Toggleable bread = new Toggleable("Bread", "Render the bread icon at each point.");
    public final Toggleable clearOnDeath = new Toggleable("Clear on death", "When enabled, the points will clear when the player dies.");

    private GrowingTess tess = new GrowingTess(4);

	public Breadcrumb() {
		super("Breadcrumb", "Retrace your steps as bread is placed in your path");
		setAuthor("Halalaboos");
		this.setCategory(Category.VISUAL);
		addChildren(lines, bread, clearOnDeath, opacity, distance, bounce);
		lines.setEnabled(true);
		bread.setEnabled(true);
		clearOnDeath.setEnabled(true);
		huzuni.commandManager.generateCommands(this);
		subscribe(PacketReadEvent.class, event -> {
			if (clearOnDeath.isEnabled()) {
				if (event.getPacket() instanceof HealthUpdatePacket) {
					HealthUpdatePacket packet = (HealthUpdatePacket)event.getPacket();
					if (packet.getHearts() <= 0F) {
						clearPoints();
					}
				}
			}
		});
	}
	
	@Override
	public void onEnable() {
		huzuni.renderManager.addWorldRenderer(this);
	}
	
	@Override
	public void onDisable() {
		huzuni.renderManager.removeWorldRenderer(this);
		lastPosition = new Vector3i(0, 0, 0);
		clearPoints();
	}
	
	@Override
	public void render(float partialTicks) {
		if (bread.isEnabled()) {
			for (Vector3i position : points) {
				Vector3d renderPos = position.toDouble().sub(getMinecraft().getCamera());
				GLUtils.glColor(1F, 1F, 1F, opacity.getValue() / 100F);
				getGLStateManager().pushMatrix();
				GLUtils.prepareBillboarding(((float) renderPos.getX()), ((float) renderPos.getY()), ((float) renderPos.getZ()), true);
				getGLStateManager().scale(0.25F, 0.25F, 0.25F);
				breadIcon.bindTexture();
				breadIcon.render(-32F, -32F + (bounce.getValue() == 0F ? 0F : (float) (bounce.getValue() * Math.cos(Math.toRadians(System.currentTimeMillis() % 360)))), 64, 64);
				getGLStateManager().popMatrix();
			}
		}
		getGLStateManager().disableTexture2D();
		if (lines.isEnabled()) {
			GLUtils.glColor(1F, 1F, 1F, opacity.getValue() / 100F);
			for (Vector3i position : points) {
				Vector3d renderPos = position.toDouble().sub(getMinecraft().getCamera());
		    	tess.vertex(((float) renderPos.getX()), ((float) renderPos.getY()), ((float) renderPos.getZ()));
			}
			GL.state(GL_VERTEX_ARRAY, true);
	    	tess.bind().pass(GL_LINE_STRIP).reset();
			GL.state(GL_VERTEX_ARRAY, false);
		}
		if (lastPosition.toDouble().distanceTo(getPlayer().getLocation()) >= distance.getValue() && !Freecam.INSTANCE.isEnabled()) {
			lastPosition = new Vector3i(getPlayer().getLocation());
			points.add(lastPosition);
		}
	}
	
	@CommandPointer({ "clearcrumbs" })
	public void clearPoints() {
		points.clear();
		huzuni.addNotification(NotificationType.INFO, this, 5000, "Trail cleared.");
	}
	
	
}
