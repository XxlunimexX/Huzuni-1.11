package net.halalaboos.huzuni.mod.visual;

import net.halalaboos.huzuni.RenderManager.Renderer;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Toggleable;
import net.halalaboos.huzuni.api.node.Value;
import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.entity.projectile.Arrow;
import net.halalaboos.mcwrapper.api.item.Item;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.item.types.Bow;
import net.halalaboos.mcwrapper.api.item.types.SplashPotion;
import net.halalaboos.mcwrapper.api.item.types.Throwable;
import net.halalaboos.mcwrapper.api.util.math.MathUtils;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.*;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;
import pw.knx.feather.tessellate.GrowingTess;

import java.util.List;

import static net.halalaboos.mcwrapper.api.MCWrapper.*;
import static net.halalaboos.mcwrapper.api.opengl.OpenGL.GL;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.glEnableClientState;

/**
 * Renders the trajectory of any throwable item held by the player along with the projectiles within the air.
 *
 * TODO: Port to MCWrapper
 * */
public class Projectiles extends BasicMod implements Renderer {

	private final Cylinder cylinder = new Cylinder();

	public final Toggleable lines = new Toggleable("Lines", "Render lines showing the projectile of "),
			landing = new Toggleable("Landing", "Render a landing position of each projectile"),
			arrows = new Toggleable("Arrows", "Show other arrows/items trajectories from other players");

	public final Value landingSize = new Value("Landing size", "", 0.5F, 0.5F, 2F, "Adjust the size of the landing pad");

	public Projectiles() {
		super("Projectiles", "Render a trajectory showing the path of a projectile");
		setAuthor("Halalaboos");
		setCategory(Category.VISUAL);
		addChildren(lines, landing, arrows, landingSize);
		lines.setEnabled(true);
		landing.setEnabled(true);
		arrows.setEnabled(true);
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
		ItemStack item = getApplicableItem();
		if (item != null) {
			int mode = 0;
			float velocity;

			if (item.getItemType() instanceof Bow)
				mode = 1;
			else if (item.getItemType() instanceof SplashPotion)
				mode = 2;

			float yaw = getPlayer().getYaw();
			float pitch = getPlayer().getPitch();
			Vector3d cam = getMinecraft().getCamera();
			double posX = cam.getX() - (double) (MathUtils.cos(yaw / 180.0F * (float) Math.PI) * 0.16F),
					posY = (cam.getY() + (double) getPlayer().getEyeHeight()) - 0.10000000149011612D,
					posZ = cam.getZ() - (double) (MathUtils.sin(yaw / 180.0F * (float) Math.PI) * 0.16F),
					motionX = (double) (-MathUtils.sin(yaw / 180.0F * (float) Math.PI) * MathUtils.cos(pitch / 180.0F * (float) Math.PI)) * (mode == 1 ? 1.0 : 0.4),
					motionY = (double) (-MathUtils.sin(pitch / 180.0F * (float) Math.PI)) * (mode == 1 ? 1.0 : 0.4),
					motionZ = (double) (MathUtils.cos(yaw / 180.0F * (float) Math.PI) * MathUtils.cos(pitch / 180.0F * (float) Math.PI)) * (mode == 1 ? 1.0 : 0.4);
			if (getPlayer().getItemUseTicks() <= 0 && mode == 1)
				velocity = 1F;
			else
				velocity = Bow.getVelocity(72000 - getPlayer().getItemUseTicks());
			renderProjectile(mode, velocity, posX, posY, posZ, motionX, motionY, motionZ, null);
		}
		if (arrows.isEnabled()) {
			for (net.halalaboos.mcwrapper.api.entity.Entity o : getWorld().getEntities()) {
				if (o instanceof Arrow) {
					Arrow entity = (Arrow) o;
					if (entity.isDead() || entity.isInGround())
						continue;
					Vector3d vel = entity.getVelocity();
					Vector3d pos = entity.getLocation();
					renderProjectile(1, -1, pos.getX(), pos.getY(), pos.getZ(), vel.getX(), vel.getY(), vel.getZ(), entity.getSource() != null ? entity.getSource().name() : null);
				}
			}
		}
	}

	private GrowingTess projectileTess = new GrowingTess(4);

	private void renderProjectile(int mode, float velocity, double x, double y, double z, double motionX, double motionY, double motionZ, String text) {
		Vector3d cam = getMinecraft().getCamera();
		if (velocity != -1) {
			float theta = MathUtils.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
			motionX /= (double) theta;
			motionY /= (double) theta;
			motionZ /= (double) theta;
			motionX *= (mode == 1 ? (velocity * 2) : 1) * getMult(mode);
			motionY *= (mode == 1 ? (velocity * 2) : 1) * getMult(mode);
			motionZ *= (mode == 1 ? (velocity * 2) : 1) * getMult(mode);
		}
		boolean hasLanded = false, isEntity = false;
		RayTraceResult collision = null;
		float size = mode == 1 ? 0.3F : 0.25F;
		float gravity = getGravity(mode);

		if (text != null) {
			getGLStateManager().pushMatrix();
			GLUtils.prepareBillboarding((float) (x - cam.getX()), (float) (y - cam.getY()), (float) (z - cam.getZ()), true);
			getGLStateManager().enableTexture2D();
			getTextRenderer().render(text, -getTextRenderer().getWidth(text) / 2, 1, 0xFFFFFFF, true);
			getGLStateManager().disableTexture2D();
			getGLStateManager().popMatrix();
		}
		GLUtils.glColor(0F, 1F, 0F, 1F);
		for (; !hasLanded && y > 0;) {
			Vec3d present = new Vec3d(x, y, z);
			Vec3d future = new Vec3d(x + motionX, y + motionY, z + motionZ);
			RayTraceResult possibleCollision = mc.world.rayTraceBlocks(present, future, false, true, false);
			if (possibleCollision != null) {
				hasLanded = true;
				collision = possibleCollision;
			}
			AxisAlignedBB boundingBox = new AxisAlignedBB(x - size, y - size, z - size, x + size, y + size, z + size);

			List<Entity> entities = mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			for (Entity entity : entities) {
				if (entity.canBeCollidedWith() && entity != mc.player) {
					AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox().expand(0.3D, 0.3D, 0.3D);
					RayTraceResult entityCollision = entityBoundingBox.calculateIntercept(present, future);
					if (entityCollision != null) {
						hasLanded = true;
						isEntity = true;
						collision = entityCollision;
					}
				}
			}

			x += motionX;
			y += motionY;
			z += motionZ;
			float motionAdjustment = 0.99F;
			if (isInMaterial(boundingBox, Material.WATER))
				motionAdjustment = 0.8F;

			motionX *= motionAdjustment;
			motionY *= motionAdjustment;
			motionZ *= motionAdjustment;
			motionY -= gravity;
			if (lines.isEnabled()) {
				projectileTess.vertex(((float) (x - cam.getX())), ((float) (y - cam.getY())), ((float) (z - cam.getZ())));
			}
		}
		if (lines.isEnabled()) {
			GL.state(GL_VERTEX_ARRAY, true).state(GL_COLOR_ARRAY, true);
			projectileTess.bind().pass(GL_LINE_STRIP);
			projectileTess.pass(GL_LINE_STRIP).reset();
			GL.state(GL_VERTEX_ARRAY, false).state(GL_COLOR_ARRAY, false);
		}
		if (landing.isEnabled()) {
			getGLStateManager().pushMatrix();
			getGLStateManager().translate(x - cam.getX(), y - cam.getY(), z - cam.getZ());
			if (collision != null) {
				switch (collision.sideHit.getIndex()) {
					case 2:
						getGLStateManager().rotate(90, 1, 0, 0);
						break;
					case 3:
						getGLStateManager().rotate(90, 1, 0, 0);
						break;
					case 4:
						getGLStateManager().rotate(90, 0, 0, 1);
						break;
					case 5:
						getGLStateManager().rotate(90, 0, 0, 1);
						break;
					default:
						break;
				}
				if (isEntity) color(1f, 0f, 0f, 1f); else color(0F, 1F, 0f, 1f);
			}
			renderPoint();
			getGLStateManager().popMatrix();
		}
	}

	/**
	 * Finds an item, checking both hands
	 *
	 * todo - maybe have a check if the player has arrows? if no arrows, ignore bow
	 */
	private ItemStack getApplicableItem() {
		if (getPlayer().getHeldItem(getPlayer().getCurrentHand()).isPresent()) {
			ItemStack active = getPlayer().getHeldItem(getPlayer().getCurrentHand()).get();
			if (isThrowable(active.getItemType())) return active;
		}
		if (getPlayer().getHeldItem(Hand.MAIN).isPresent()) {
			ItemStack main = getPlayer().getHeldItem(Hand.MAIN).get();
			if (isThrowable(main.getItemType())) return main;
		}
		if (getPlayer().getHeldItem(Hand.OFF).isPresent()) {
			ItemStack off = getPlayer().getHeldItem(Hand.OFF).get();
			if (isThrowable(off.getItemType())) return off;
		}
		return null;
	}

	private boolean isThrowable(Item item) {
		return item != null && item instanceof Throwable;
	}

	private float getMult(int mode) {
		return mode == 2 ? 0.5F : 1.5F;
	}

	private float getGravity(int mode) {
		return mode >= 1 ? 0.05F : 0.03F;
	}

	private final GrowingTess pointTess = new GrowingTess(4);

	private void renderPoint() {
		pointTess
				.vertex(-landingSize.getValue(), 0, 0)
				.vertex(0, 0, 0)
				.vertex(0, 0, -landingSize.getValue())
				.vertex(0, 0, 0)
				.vertex(landingSize.getValue(), 0, 0)
				.vertex(0, 0, 0).vertex(0, 0, landingSize.getValue())
				.vertex(0, 0, 0);
		GL.state(GL_VERTEX_ARRAY, true).state(GL_COLOR_ARRAY, true);
		pointTess.bind().pass(GL_LINES);
		pointTess.pass(GL_LINES).reset();
		GL.state(GL_VERTEX_ARRAY, false).state(GL_COLOR_ARRAY, false);
		getGLStateManager().rotate(-90, 1, 0, 0);
		cylinder.setDrawStyle(GLU.GLU_LINE);
		cylinder.draw(landingSize.getValue(), landingSize.getValue(), 0.1f, 24, 1);
	}

	private void color(float r, float g, float b, float a) {
		pointTess.color(r, g, b, a);
		projectileTess.color(r, g, b, a);
		GLUtils.glColor(r, g, b, a);
	}

	private boolean isInMaterial(AxisAlignedBB axisalignedBB, Material material) {
		int chunkMinX = MathUtils.floor(axisalignedBB.minX);
		int chunkMaxX = MathUtils.floor(axisalignedBB.maxX + 1.0D);
		int chunkMinY = MathUtils.floor(axisalignedBB.minY);
		int chunkMaxY = MathUtils.floor(axisalignedBB.maxY + 1.0D);
		int chunkMinZ = MathUtils.floor(axisalignedBB.minZ);
		int chunkMaxZ = MathUtils.floor(axisalignedBB.maxZ + 1.0D);

		StructureBoundingBox structureBoundingBox = new StructureBoundingBox(chunkMinX, chunkMinY, chunkMinZ, chunkMaxX, chunkMaxY, chunkMaxZ);
		if (!mc.world.isAreaLoaded(structureBoundingBox)) {
			return false;
		} else {
			boolean isWithin = false;
			for (int x = chunkMinX; x < chunkMaxX; ++x) {
				for (int y = chunkMinY; y < chunkMaxY; ++y) {
					for (int z = chunkMinZ; z < chunkMaxZ; ++z) {
						IBlockState blockState = mc.world.getBlockState(new BlockPos(x, y, z));
						if (blockState.getMaterial() == material) {
							double liquidHeight = (double) ((float) (y + 1) - BlockLiquid.getLiquidHeightPercent(blockState.getValue(BlockLiquid.LEVEL)));
							if ((double) chunkMaxY >= liquidHeight)
								isWithin = true;
						}
					}
				}
			}
			return isWithin;
		}
	}
}
