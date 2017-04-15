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
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;
import static net.halalaboos.mcwrapper.api.opengl.OpenGL.GL;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;

/**
 * Renders a trail behind the player as they walk - useful for keeping track of where they move to prevent from
 * getting lost or something.
 *
 * @author Halalaboos
 */
public class Breadcrumb extends BasicMod implements Renderer {

	/**
	 * Bread icon to render if {@link #bread} is enabled.
	 */
	private final Texture breadIcon = new Texture("bread.png");

	/**
	 * All of the points for the trail to render.
	 */
	private final List<Vector3i> points = new ArrayList<>();

	/**
	 * The last recorded point, used to check the distance between the current position.
	 */
	private Vector3i lastPosition = new Vector3i(0, 0, 0);

	/**
	 * The opacity of the trail.
	 */
	private final Value opacity = new Value("Opacity", "%", 0F, 50F, 100F, 1F, "Opacity of the icon.");

	/**
	 * How much the {@link #breadIcon} should 'bounce' if {@link #bread} is enabled.
	 */
	private final Value bounce = new Value("Bounce" ,0F, 0F, 10F, "Amount the icon will bounce.");

	/**
	 * The distance between each added point for the trail.
	 */
	private final Value distance = new Value("Distance", " blocks", 1F, 15F, 30F, 1F, "Distance between each point.");

	/**
	 * Whether or not the trail should draw a line (some might just want to have {@link #bread} and no lines)
	 */
    private final Toggleable lines = new Toggleable("Lines", "Render lines between each point.");

	/**
	 * Whether or not to render the {@link #breadIcon} on each point.
	 */
	private final Toggleable bread = new Toggleable("Bread", "Render the bread icon at each point.");

	/**
	 * Whether or not to clear all {@link #points} on death.
	 */
	private final Toggleable clearOnDeath = new Toggleable("Clear on death", "When enabled, the points will clear when the player dies.");

	/**
	 * Used for rendering the trail lines.
	 */
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
		//Checks if we die and clears the trail if clearOnDeath is enabled.
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
			//Loop through all of the points
			for (Vector3i position : points) {
				//Calculate the render position for each point by subtracting the position from the camera
				Vector3d renderPos = position.toDouble().sub(mc.getCamera());
				//Resets the color so the bread isn't red or something, makes it transparent based on the opacity value
				GLUtils.glColor(1F, 1F, 1F, opacity.getValue() / 100F);

				getGLStateManager().pushMatrix();
				//Set up billboarding so the bread appears to be 2D
				GLUtils.prepareBillboarding(((float) renderPos.getX()), ((float) renderPos.getY()), ((float) renderPos.getZ()), true);
				//Scale, otherwise it would just be giant bread icons.
				getGLStateManager().scale(0.25F, 0.25F, 0.25F);
				//Bind and render the texture
				breadIcon.bindTexture();
				breadIcon.render(-32F, -32F + (bounce.getValue() == 0F ? 0F : (float) (bounce.getValue() * Math.cos(Math.toRadians(System.currentTimeMillis() % 360)))), 64, 64);
				getGLStateManager().popMatrix();
			}
		}
		//Disable Texture2D in case that hasn't been done already
		getGLStateManager().disableTexture2D();
		if (lines.isEnabled()) {
			//Color the line, currently it's just white.  Rainbow option maybe?  Or color based on distance?
			GLUtils.glColor(1F, 1F, 1F, opacity.getValue() / 100F);
			//Loop through all the points
			for (Vector3i position : points) {
				//Calculate the render position for each point by subtracting the position from the camera
				Vector3d renderPos = position.toDouble().sub(mc.getCamera());
				//Add a vertex to the line tessellator for each point's render position
		    	tess.vertex(((float) renderPos.getX()), ((float) renderPos.getY()), ((float) renderPos.getZ()));
			}

			//Render the line trail
			GL.state(GL_VERTEX_ARRAY, true);
	    	tess.bind().pass(GL_LINE_STRIP).reset();
			GL.state(GL_VERTEX_ARRAY, false);
		}
		//Check if we are past the minimum required distance to add a point
		//Also checks if we have freecam enabled, we don't want to add points if that is enabled.
		if (lastPosition.toDouble().distanceTo(getPlayer().getLocation()) >= distance.getValue() && !Freecam.INSTANCE.isEnabled()) {
			//Add a point based on the player's location.
			lastPosition = new Vector3i(getPlayer().getLocation());
			points.add(lastPosition);
		}
	}

	/**
	 * Adds a command 'clearcrrumbs' which clears the {@link #points}
	 *
	 * We don't use {@link CommandPointer} too often in the project, but this allows us to create a command that'll
	 * execute method below when it is called.  Nifty!
	 */
	@CommandPointer({ "clearcrumbs" })
	private void clearPoints() {
		points.clear();
		huzuni.addNotification(NotificationType.INFO, this, 5000, "Trail cleared.");
	}
	
}
