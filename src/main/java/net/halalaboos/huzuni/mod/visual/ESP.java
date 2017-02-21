package net.halalaboos.huzuni.mod.visual;

import net.halalaboos.huzuni.RenderManager.Renderer;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.settings.Mode;
import net.halalaboos.huzuni.api.settings.Toggleable;
import net.halalaboos.huzuni.api.settings.Value;
import net.halalaboos.huzuni.api.util.MinecraftUtils;
import net.halalaboos.huzuni.api.util.render.Box;
import net.halalaboos.huzuni.api.util.render.GLManager;
import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.halalaboos.mcwrapper.api.util.math.AABB;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.input.Keyboard;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;

/**
 * Renders meshes around entities which have been selected.
 * */
public class ESP extends BasicMod implements Renderer {

	private final Box[] box = new Box[0xFFFFF];

	private final Tessellator tessellator = Tessellator.getInstance();

	public final Toggleable players = new Toggleable("Players", "Traces to players"),
			mobs = new Toggleable("Mobs", "Traces to mobs"),
			animals = new Toggleable("Animals", "Traces to animals"),
			invisibles = new Toggleable("Invisible", "Trace to invisible entities"),
			lines = new Toggleable("Lines", "Traces lines to each entity"),
			properties = new Toggleable("Properties", "Ignores players without properties"),
			checkAge = new Toggleable("Check age", "Check the age of the entity before rendering");

	public final Value opacity = new Value("Opacity", "%", 0F, 50F, 100F, 1F, "Opacity of the icon");

	public final Mode<String> mode = new Mode<String>("Mode", "Style the entities will be rendered with", "None", "Hitboxes", "Rectangle", "Lines");

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
		for (Entity e : MCWrapper.getWorld().getEntities()) {
			if (e instanceof Living) {
				Living entity = (Living)e;
				if (entity == MCWrapper.getPlayer() || entity.isDead() || !MinecraftUtils.checkType(entity,
						invisibles.isEnabled(), mobs.isEnabled(), animals.isEnabled(), players.isEnabled()) ||
						(properties.isEnabled() && !MinecraftUtils.checkProperties(entity)) ||
						(checkAge.isEnabled() && !MinecraftUtils.checkAge(entity)))
					continue;
				Vector3d pos = entity.getInterpolatedPosition();
				double rX = pos.x - mc.getRenderManager().viewerPosX;
				double rY = pos.y - mc.getRenderManager().viewerPosY;
				double rZ = pos.z - mc.getRenderManager().viewerPosZ;
				float distance = (float)MCWrapper.getPlayer().getDistanceTo(entity);
				int entityId = entity.getEntityListId();
				if (entityId < 0) entityId = 420;
				boolean friend = huzuni.friendManager.isFriend(entity.name());
				if (lines.isEnabled()) {
					drawLine(((float) rX), ((float) rY), ((float) rZ), entity, distance, friend);
				}
				setColor(entity, distance, friend, false, opacity.getValue() / 100F);
				if (mode.getSelected() == 1) {
					GlStateManager.pushMatrix();
					GlStateManager.translate(rX, rY, rZ);
					GlStateManager.rotate(-entity.getRotation().yaw, 0F, 1F, 0F);
					generateVbo(entity, entityId);
					box[entityId].render();
					GlStateManager.popMatrix();
				} else if (mode.getSelected() == 2) {
					GlStateManager.pushMatrix();
					GlStateManager.translate(rX, rY, rZ);
					GlStateManager.rotate(-MCWrapper.getPlayer().getRotation().yaw, 0F, 1F, 0F);
					float width = (float) (entity.getBoundingBox().max.x - entity.getBoundingBox().min.x),
							height = (float) (entity.getBoundingBox().max.y - entity.getBoundingBox().min.y);
					VertexBuffer vertexBuffer = tessellator.getBuffer();
					vertexBuffer.begin(GL_LINE_LOOP, DefaultVertexFormats.POSITION);
					vertexBuffer.pos(-width, 0, 0F).endVertex();
					vertexBuffer.pos(-width, height, 0F).endVertex();
					vertexBuffer.pos(width, height, 0F).endVertex();
					vertexBuffer.pos(width, 0, 0F).endVertex();
					tessellator.draw();
					GlStateManager.popMatrix();
				} else if (mode.getSelected() == 3) {
					GLManager.glColor(1F, distance / 64F, 0F, 1F);
					VertexBuffer renderer = tessellator.getBuffer();
					renderer.begin(GL_LINES, DefaultVertexFormats.POSITION);
					renderer.pos(rX, rY, rZ).endVertex();
					renderer.pos(rX, rY + entity.getHeight(), rZ).endVertex();
					tessellator.draw();
				}
			}
		}
	}

	private void generateVbo(Living entity, int entityId) {
		if (box[entityId] == null) {
			double wX = entity.getBoundingBox().min.x - entity.getBoundingBox().max.x,
					wY = entity.getBoundingBox().max.y - entity.getBoundingBox().min.y,
					wZ = entity.getBoundingBox().max.z - entity.getBoundingBox().min.z;
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
			GLManager.glColor(huzuni.friendManager.getColor(), opacity);
		else {
			if (huzuni.settings.team.isEnabled()) {
				if (huzuni.settings.team.isTeam(entity)) {
					GLManager.glColor(huzuni.settings.team.getColor(), opacity);
					return;
				} else {
					int teamColor = huzuni.settings.team.getTeamColor(entity);
					if (teamColor != -1) {
						GLManager.glColor(teamColor, opacity);
						return;
					}
				}
			}
			if (lines)
				GLManager.glColor(1F, distance / 64F, 0F, opacity);
			else {
				if (entity.getHurtResistantTime() > 0)
					GLManager.glColor(1F, 1F - ((float) entity.getHurtResistantTime() / ((float) entity.getMaxHurtResistantTime() / 2F)), 0F, opacity);
				else
					GLManager.glColor(0F, 1F, 0F, opacity);
			}
		}
	}

}
