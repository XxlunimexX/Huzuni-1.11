package net.halalaboos.huzuni.mod.visual;

import net.halalaboos.huzuni.RenderManager.Renderer;
import net.halalaboos.huzuni.WaypointManager.Waypoint;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.mod.Mod;
import net.halalaboos.huzuni.api.mod.command.impl.BasicCommand;
import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.huzuni.api.util.gl.Texture;
import net.halalaboos.mcwrapper.api.event.network.PacketReadEvent;
import net.halalaboos.mcwrapper.api.network.packet.server.HealthUpdatePacket;
import net.halalaboos.mcwrapper.api.util.enums.TextColor;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import java.awt.*;

import static net.halalaboos.mcwrapper.api.MCWrapper.*;
import static org.lwjgl.opengl.GL11.glLineWidth;

/**
 * Renders the waypoints added by the player.
 * */
public class Waypoints extends Mod implements Renderer {
	
	private final Texture location = new Texture("location.png");
	
	public final Toggleable distance = new Toggleable("Distance", "Show distance from waypoint"),
			customFont = new Toggleable("Custom font", "Render with custom font renderer"),
			renderIcon = new Toggleable("Icon", "Render with an icon above waypoint"),
			lines = new Toggleable("Lines", "Render a line to the waypoint"),
			deathPoints = new Toggleable("Death points", "Add a waypoint when you die"),
			scale = new Toggleable("Scale", "Scale the nameplates as you are further from the player"),
			background = new Toggleable("Background", "Render backgrounds behind waypoint names");
	
	public final Value opacity = new Value("Opacity", "%", 0F, 100F, 100F, 1F, "Opacity of the icon");
	
	public final Value scaleValue = new Value("Scale Amount", "%", 1F, 1.2F, 3F, "Amount the name plates will be scaled");
			
	public Waypoints() {
		super("Waypoints", "Add/remove points of interest to and from the game");
		setAuthor("Halalaboos");
		setCategory(Category.VISUAL);
		addChildren(distance, customFont, renderIcon, lines, background, deathPoints, scale, scaleValue, opacity);
		distance.setEnabled(true);
		renderIcon.setEnabled(true);
		scale.setEnabled(true);
		this.setEnabled(true);
		this.settings.setDisplayable(false);
		huzuni.commandManager.addCommand(new BasicCommand(new String[] { "waypoint", "wp" }, "Add/remove waypoints.") {

			@Override
			public void giveHelp() {
				huzuni.addChatMessage(".waypoint add \"name\" <x> <y> <z>");
				huzuni.addChatMessage(".waypoint add \"name\"");
				huzuni.addChatMessage(".waypoint remove \"name\"");
			}
			
			@Override
			protected void runCommand(String input, String[] args) throws Exception {
				throw new NullPointerException();
			}
		
		}.addSubcommand(new BasicCommand(new String[] { "add", "a" }, "Add waypoints.") {

			@Override
			protected void runCommand(String input, String[] args) {
				String name = args[0];
				if (args.length > 1) {
					int x = (int) Double.parseDouble(args[1]);
					int y = (int) Double.parseDouble(args[2]);
					int z = (int) Double.parseDouble(args[3]);
					if (huzuni.waypointManager.addWaypoint(new Waypoint(name, new Vector3i(x, y, z)))) {
						huzuni.addChatMessage(String.format("Waypoint '" + TextColor.RED + "%s" + TextColor.GRAY + "' added at %d, %d, %d", name, x, y, z));
						huzuni.waypointManager.save();
					} else {
						huzuni.addChatMessage(String.format("Waypoint '" + TextColor.RED + "%s" + TextColor.GRAY + "' already exists!", name));
					}
				} else {
					Vector3i position = getPlayer().getLocation().toInt();
					if (huzuni.waypointManager.addWaypoint(new Waypoint(name, position))) {
						huzuni.addChatMessage(String.format("Waypoint '" + TextColor.RED + "%s" + TextColor.GRAY + "' added at %d, %d, %d", name, position.getX(), position.getY(), position.getZ()));
						huzuni.waypointManager.save();
					} else {
						huzuni.addChatMessage(String.format("Waypoint '" + TextColor.RED + "%s" + TextColor.GRAY + "' already exists!", name));
					}
				}
			}
			
		}).addSubcommand(new BasicCommand(new String[] { "remove", "r", "delete", "del", "d" }, "Remove waypoints.") {

			@Override
			protected void runCommand(String input, String[] args) {
				String name = args[0];
				if (huzuni.waypointManager.removeWaypoint(name)) {
					huzuni.addChatMessage(String.format("Waypoint '" + TextColor.RED + "%s" + TextColor.GRAY + "' removed!", name));
				} else {
					huzuni.addChatMessage(String.format("Waypoint '" + TextColor.RED + "%s" + TextColor.GRAY + "' does not exist!", name));
				}
			}
			
		}).addSubcommand(new BasicCommand(new String[] { "clear", "c" }, "Clear waypoints.") {

			@Override
			protected void runCommand(String input, String[] args) {
				int amount = huzuni.waypointManager.clearLocalWaypoints();
				huzuni.addChatMessage(String.format("%d waypoints cleared!", amount));
			}
			
		}));
		subscribe(PacketReadEvent.class, event -> {
			if (event.getPacket() instanceof HealthUpdatePacket) {
				HealthUpdatePacket packet = (HealthUpdatePacket) event.getPacket();
				if (packet.getHearts() <= 0.0F && getPlayer().getLocation() != null) {
					huzuni.waypointManager.addDeathPoint(getPlayer().getLocation().toInt());
					huzuni.waypointManager.save();
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
	}

	@Override
	public void render(float partialTicks) {
		for (int i = 0; i < huzuni.waypointManager.getWaypoints().size(); i++) {
			Waypoint waypoint = huzuni.waypointManager.getWaypoints().get(i);
			if (waypoint.isOnServer()) {
				Vector3d renderPos = waypoint.getPosition().toDouble().sub(mc.getCamera());
				Color color = waypoint.getColor();
				double scale = (waypoint.getPosition().toDouble().distanceTo(mc.getCamera()) / 8D) / (1.5F + (2F - scaleValue.getValue()));
				if (scale < 1D || !this.scale.isEnabled())
					scale = 1D;
				if (lines.isEnabled())
					huzuni.renderManager.addLine((float)renderPos.getX(), (float)renderPos.getY(), (float)renderPos.getZ(), (float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, opacity.getValue() / 100F);
				getGLStateManager().pushMatrix();
				GLUtils.prepareBillboarding((float)renderPos.getX(), (float)renderPos.getY(), (float)renderPos.getZ(), true);
				getGLStateManager().scale(scale, scale, scale);
				location.bindTexture();
				GLUtils.glColor(color, opacity.getValue() / 100F);
				String renderName = waypoint.getName() + (distance.isEnabled() ? " (" + ((int) waypoint.getDistance()) + ")" : "");
				if (customFont.isEnabled()) {
					int width = huzuni.guiFontRenderer.getStringWidth(renderName);
					if (renderIcon.isEnabled())
						location.render(-16F, -16F - (huzuni.guiFontRenderer.getStringHeight(renderName)) * 2F, 32, 32);
					if (background.isEnabled()) {
						getGLStateManager().disableTexture2D();
						GLUtils.glColor(0F, 0F, 0F, opacity.getValue() / 100F);
						GLUtils.drawBorderRect(-width / 2 - 2, -2, width / 2 + 2, 10, 2F);
						GLUtils.glColor(1F, 1F, 1F, opacity.getValue() / 100F);
						getGLStateManager().enableTexture2D();
					}
					huzuni.guiFontRenderer.drawStringWithShadow(renderName, -width / 2, -2, 0xFFFFFF);
				} else {
					int width = getTextRenderer().getWidth(renderName);
					if (renderIcon.isEnabled())
						location.render(-16F, -16F - (getTextRenderer().getHeight()) * 2F, 32, 32);
					
					if (background.isEnabled()) {
						getGLStateManager().disableTexture2D();
						GLUtils.glColor(0F, 0F, 0F, opacity.getValue() / 100F);
						GLUtils.drawBorderRect(-width / 2 - 2, -2, width / 2 + 2, 10, 2F);
						GLUtils.glColor(1F, 1F, 1F, opacity.getValue() / 100F);
						getGLStateManager().enableTexture2D();
					}
					getTextRenderer().render(renderName, -width / 2, 0, 0xFFFFFF, true);
			        getGLStateManager().disableAlpha();
				}
				getGLStateManager().disableTexture2D();
				getGLStateManager().popMatrix();
			}
		}
		glLineWidth(huzuni.settings.lineSize.getValue());
	}
	
}
