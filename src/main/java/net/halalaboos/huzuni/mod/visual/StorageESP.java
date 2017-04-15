package net.halalaboos.huzuni.mod.visual;

import net.halalaboos.huzuni.RenderManager.Renderer;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.impl.ItemSelector;
import net.halalaboos.huzuni.api.node.impl.ItemSelector.ItemData;
import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.huzuni.api.util.gl.Box;
import net.halalaboos.huzuni.api.util.gl.GLUtils;
import net.halalaboos.mcwrapper.api.block.BlockTypes;
import net.halalaboos.mcwrapper.api.block.tileentity.*;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.util.math.AABB;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.*;

/**
 * Renders boxes around the loaded {@link TileEntity tile entities} in the world.  This is useful for finding
 * hidden chests, since the boxes are not affected by things such as depth.
 *
 * @author Halalaboos
 */
public class StorageESP extends BasicMod implements Renderer {

	private final Box normal, left, right;
	
	private final Toggleable boxes = new Toggleable("Boxes", "Render boxes around the blocks"),
			lines = new Toggleable("Lines", "Render lines towards blocks"), 
			fade = new Toggleable("Fade", "Make nearby boxes fade out"), 
			border = new Toggleable("Border", "Apply borders around each block");

	private final ItemSelector<Class<?>> itemSelector = new ItemSelector<>("Enabled blocks", "OOGA BOOGA");
	
	public StorageESP() {
		super("Storage ESP", "Render boxes/lines to and around storage blocks within the world", Keyboard.KEY_Y);
		setCategory(Category.VISUAL);
		setAuthor("Halalaboos");
		normal = new Box(new AABB(0, 0, 0, 1, 1, 1));
		left = new Box(new AABB(0, 0, 0, 2, 1, 1));
		right = new Box(new AABB(0, 0, 0, 1, 1, 2));
		addChildren(boxes, border, lines, fade, itemSelector);
		boxes.setEnabled(true);
		border.setEnabled(true);
		lines.setEnabled(false);
		itemSelector.addItem(ItemStack.from(BlockTypes.CHEST), Chest.class).setEnabled(true);
		itemSelector.addItem(ItemStack.from(BlockTypes.ENDER_CHEST), EnderChest.class);
		itemSelector.addItem(ItemStack.from(BlockTypes.HOPPER), Hopper.class);
		itemSelector.addItem(ItemStack.from(BlockTypes.DISPENSER), Dropper.class, Dispenser.class);
		itemSelector.addItem(ItemStack.from(BlockTypes.FURNACE), Furnace.class);
		itemSelector.addItem(ItemStack.from(BlockTypes.ENCHANTING_TABLE), EnchantingTable.class);
		itemSelector.addItem(ItemStack.from(BlockTypes.BREWING_STAND), BrewingStand.class);
	}

	@Override
	protected void onEnable() {
		huzuni.renderManager.addWorldRenderer(this);
	}

	@Override
	protected void onDisable() {
		huzuni.renderManager.removeWorldRenderer(this);
	}
	
	private boolean isInstance(ItemData<Class<?>> itemData, Object object) {
		for (Class<?> clazz : itemData.getTypes()) {
			if (clazz.isInstance(object)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return True if the object is an instance of the item data's sub classes and if the item data is enabled.
	 * */
	private boolean isEnabledInstance(Object object) {
		for (ItemData<Class<?>> itemData : itemSelector.getItemDatas()) {
			if (itemData.isEnabled() && isInstance(itemData, object)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void render(float partialTicks) {
		for (TileEntity tileEntity : getWorld().getTileEntities()) {
			Vector3d cam = mc.getCamera();
			Vector3d renderPos = tileEntity.getPosition().toDouble().sub(cam);
			float renderX = (float) (renderPos.getX());
			float renderY = (float) (renderPos.getY());
			float renderZ = (float) (renderPos.getZ());
			float dist = (float) getPlayer().getDistanceTo(tileEntity.getPosition().toDouble()) / 128;
			float alpha = dist > 0.25F ? 0.25F : dist;

			if (!isEnabledInstance(tileEntity))
				continue;
			if (tileEntity instanceof Chest) {
				Chest chest = (Chest)tileEntity;
				if (this.lines.isEnabled()) {
					huzuni.renderManager.addLine(renderX + 0.5F, renderY, renderZ + 0.5F, 1F, chest.getType() == Chest.ChestType.TRAP ? 0F : 1F, 0F, alpha);
				}
				getGLStateManager().pushMatrix();
				getGLStateManager().translate(renderX, renderY, renderZ);
				if (chest.getAdjacentXPos() != null) {
					if (boxes.isEnabled()) {
						colorChest(chest, alpha);
						left.setOpaque(true);
						left.render();
					}
					if (border.isEnabled()) {
						colorChest(chest, alpha);
						left.setOpaque(false);
						left.render();
					}
				} else if (chest.getAdjacentZPos() != null) {
					if (boxes.isEnabled()) {
						colorChest(chest, alpha);
						right.setOpaque(true);
						right.render();
					}
					if (border.isEnabled()) {
						colorChest(chest, alpha);
						right.setOpaque(false);
						right.render();
					}
				} else if (chest.getAdjacentXNeg() == null && chest.getAdjacentZNeg() == null) {
					if (boxes.isEnabled()) {
						colorChest(chest, alpha);
						normal.setOpaque(true);
						normal.render();
					}
					if (border.isEnabled()) {
						colorChest(chest, alpha);
						normal.setOpaque(false);
						normal.render();
					}
				}
				getGLStateManager().popMatrix();
			}
			if (tileEntity instanceof EnderChest || tileEntity instanceof EnchantingTable) renderBox(tileEntity, 1F, 0.1F, 1F);
			if (tileEntity instanceof Furnace) renderBox(tileEntity, 0.25F, 0.25F, 0.25F);
			if (tileEntity instanceof Dropper || tileEntity instanceof Dispenser) renderBox(tileEntity, 0.5F, 0.5F, 0.5F);
			if (tileEntity instanceof Hopper || tileEntity instanceof BrewingStand) renderBox(tileEntity, 0.25F, 0.25F, 0.25F);
		}
	}

	private void colorChest(Chest tileEntity, float alpha) {
		if (tileEntity.getType() == Chest.ChestType.TRAP)
			GLUtils.glColor(1F, 0F, 0F, fade.isEnabled() ? alpha : 0.25F);
		else
			GLUtils.glColor(1F, 1F, 0F, fade.isEnabled() ? alpha : 0.25F);
	}

	/**
     * Renders a box with the given r, g, b values over the given tile entity.
     * */
	private void renderBox(TileEntity tileEntity, float r, float g, float b) {
		float dist = (float) getPlayer().getDistanceTo(tileEntity.getPosition().toDouble()) / 128;
		float alpha = dist > 0.25F ? 0.25F : dist;
		Vector3d renderPos = tileEntity.getPosition().toDouble().sub(mc.getCamera());
		float renderX = (float) renderPos.getX();
		float renderY = (float) renderPos.getY();
		float renderZ = (float) renderPos.getZ();
		if (this.lines.isEnabled()) {
			huzuni.renderManager.addLine(renderX + 0.5F, renderY, renderZ + 0.5F, r, g, b, fade.isEnabled() ? alpha : 0.25F);
		}
		getGLStateManager().pushMatrix();
		getGLStateManager().translate(renderX, renderY, renderZ);
		if (boxes.isEnabled()) {
			GLUtils.glColor(r, g, b, fade.isEnabled() ? alpha : 0.25F);
			normal.setOpaque(true);
			normal.render();
		}
		if (border.isEnabled()) {
			GLUtils.glColor(r, g, b, fade.isEnabled() ? alpha : 0.25F);
			normal.setOpaque(false);
			normal.render();
		}
		getGLStateManager().popMatrix();
	}
}
