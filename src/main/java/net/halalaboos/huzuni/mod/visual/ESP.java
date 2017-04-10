package net.halalaboos.huzuni.mod.visual;

import net.halalaboos.huzuni.RenderManager.Renderer;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Mode;
import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.huzuni.api.util.MinecraftUtils;
import net.halalaboos.huzuni.api.util.gl.Box;
import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.halalaboos.mcwrapper.api.util.math.AABB;
import org.lwjgl.input.Keyboard;
import pw.knx.feather.tessellate.GrowingTess;

import static net.halalaboos.mcwrapper.api.MCWrapper.getGLStateManager;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;
import static net.halalaboos.mcwrapper.api.MCWrapper.getWorld;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.glEnableClientState;

/**
 * Renders meshes around entities which have been selected.
 * */
public class ESP extends BasicMod implements Renderer {

	private final Box[] box = new Box[0xFFFFF];

	private final Toggleable players = new Toggleable("Players", "Traces to players"),
			mobs = new Toggleable("Mobs", "Traces to mobs"),
			animals = new Toggleable("Animals", "Traces to animals"),
			invisibles = new Toggleable("Invisible", "Trace to invisible entities"),
			lines = new Toggleable("Lines", "Traces lines to each entity"),
			properties = new Toggleable("Properties", "Ignores players without properties"),
			checkAge = new Toggleable("Check age", "Check the age of the entity before rendering");

	private final Value opacity = new Value("Opacity", "%", 0F, 50F, 100F, 1F, "Opacity of the icon");

	private final Mode<String> mode = new Mode<String>("Mode", "Style the entities will be rendered with", "None", "Hitboxes", "Rectangle", "Lines");

	private final GrowingTess tessellator = new GrowingTess(4);

	public ESP() {
		super("ESP", "Render boxes/lines to and around entities within the world", Keyboard.KEY_B);
		setAuthor("brudin");
		this.setCategory(Category.VISUAL);
		this.addChildren(players, mobs, animals, invisibles, lines, properties, checkAge, mode, opacity);
		this.settings.setDisplayable(false);
		players.setEnabled(true);
		lines.setEnabled(true);
		mode.setSelectedItem(1);
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
		for (Entity e : getWorld().getEntities()) {
			if (e instanceof Living) {
				Living entity = (Living)e;
				if (entity == getPlayer() || entity.isDead() || !MinecraftUtils.checkType(entity,
						invisibles.isEnabled(), mobs.isEnabled(), animals.isEnabled(), players.isEnabled()) ||
						(properties.isEnabled() && !MinecraftUtils.checkProperties(entity)) ||
						(checkAge.isEnabled() && !MinecraftUtils.checkAge(entity)))
					continue;
				Vector3d renderPos = entity.getRenderPosition();
				float distance = (float) getPlayer().getDistanceTo(entity);
				int entityId = entity.getEntityListId();
				if (entityId < 0) entityId = 420;
				boolean friend = huzuni.friendManager.isFriend(entity.name());
				if (lines.isEnabled()) {
					drawLine(((float) renderPos.getX()), ((float) renderPos.getY()), ((float) renderPos.getZ()), entity, distance, friend);
				}
				setColor(entity, distance, friend, false, opacity.getValue() / 100F);
				if (mode.getSelected() == 1) {
					getGLStateManager().pushMatrix();
					getGLStateManager().translate(renderPos.getX(), renderPos.getY(), renderPos.getZ());
					getGLStateManager().rotate(-entity.getRotation().yaw, 0F, 1F, 0F);
					generateVbo(entity, entityId);
					box[entityId].render();
					getGLStateManager().popMatrix();
				} else if (mode.getSelected() == 2) {
					getGLStateManager().pushMatrix();
					getGLStateManager().translate(renderPos.getX(), renderPos.getY(), renderPos.getZ());
					getGLStateManager().rotate(-getPlayer().getRotation().yaw, 0F, 1F, 0F);
					float width = (float) (entity.getBoundingBox().max.getX() - entity.getBoundingBox().min.getX()),
							height = (float) (entity.getBoundingBox().max.getY() - entity.getBoundingBox().min.getY());

					tessellator.color(0, 1, 0F, 1F)
							.vertex(-width, 0, 0F).vertex(-width, height, 0F)
							.vertex(width, height, 0F).vertex(width, 0, 0F);

					glEnableClientState(GL_VERTEX_ARRAY);
					glEnableClientState(GL_COLOR_ARRAY);
					tessellator.bind().pass(GL_LINE_LOOP).reset();
					glDisableClientState(GL_VERTEX_ARRAY);
					glDisableClientState(GL_COLOR_ARRAY);
					getGLStateManager().popMatrix();
				} else if (mode.getSelected() == 3) {
					float x = (float)renderPos.getX();
					float y = (float)renderPos.getY();
					float z = (float)renderPos.getZ();
					tessellator.color(1F, distance / 64F, 0F, 1F)
							.vertex(x, y, z).vertex(x, y + entity.getHeight(), z);

					glEnableClientState(GL_VERTEX_ARRAY);
					glEnableClientState(GL_COLOR_ARRAY);
					tessellator.bind().pass(GL_LINES).reset();
					glDisableClientState(GL_VERTEX_ARRAY);
					glDisableClientState(GL_COLOR_ARRAY);
				}
			}
		}
	}

	private void generateVbo(Living entity, int entityId) {
		if (box[entityId] == null) {
			double wX = entity.getBoundingBox().min.getX() - entity.getBoundingBox().max.getX(),
					wY = entity.getBoundingBox().max.getY() - entity.getBoundingBox().min.getY(),
					wZ = entity.getBoundingBox().max.getZ() - entity.getBoundingBox().min.getZ();
			double minX = -wX/2, minY = 0, minZ = -wZ/2, maxX = wX/2, maxZ = wZ/2;
			box[entityId] = new Box(new AABB(minX, minY, minZ, maxX, wY, maxZ));
			box[entityId].setOpaque(false);
			box[entityId].render();
		}
	}

	private void drawLine(float renderX, float renderY, float renderZ, Living entity, float distance, boolean friend) {
		float opacity = this.opacity.getValue() / 100F;
		if (distance > 64F) distance = 64F;
		if (friend)
			huzuni.renderManager.addLine(renderX, renderY, renderZ, huzuni.friendManager.getColor(), opacity);
		else {
			if (huzuni.settings.team.isEnabled()) {
				if (huzuni.settings.team.isTeam(entity)) {
					int color = huzuni.settings.team.getColor();
					huzuni.renderManager.addLine(renderX, renderY, renderZ, (float) (color >> 16 & 255) / 255F, (float) (color >> 8 & 255) / 255F, (float) (color & 255) / 255F, opacity);
					return;
				} else {
					int teamColor = huzuni.settings.team.getTeamColor(entity);
					if (teamColor != -1) {
						huzuni.renderManager.addLine(renderX, renderY, renderZ, (float) (teamColor >> 16 & 255) / 255F, (float) (teamColor >> 8 & 255) / 255F, (float) (teamColor & 255) / 255F, opacity);
						return;
					}
				}
			}
			huzuni.renderManager.addLine(renderX, renderY, renderZ, 1F, distance / 64F, 0F, opacity);
		}
	}

	private void setColor(Living entity, float distance, boolean friend, boolean lines, float opacity) {
		if (friend)
			GLUtils.glColor(huzuni.friendManager.getColor(), opacity);
		else {
			if (huzuni.settings.team.isEnabled()) {
				if (huzuni.settings.team.isTeam(entity)) {
					GLUtils.glColor(huzuni.settings.team.getColor(), opacity);
					return;
				} else {
					int teamColor = huzuni.settings.team.getTeamColor(entity);
					if (teamColor != -1) {
						GLUtils.glColor(teamColor, opacity);
						return;
					}
				}
			}
			if (lines)
				GLUtils.glColor(1F, distance / 64F, 0F, opacity);
			else {
				if (entity.getHurtResistantTime() > 0)
					GLUtils.glColor(1F, 1F - ((float) entity.getHurtResistantTime() / ((float) entity.getMaxHurtResistantTime() / 2F)), 0F, opacity);
				else
					GLUtils.glColor(0F, 1F, 0F, opacity);
			}
		}
	}

}
