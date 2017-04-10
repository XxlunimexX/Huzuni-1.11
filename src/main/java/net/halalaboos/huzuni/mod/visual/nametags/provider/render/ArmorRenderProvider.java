package net.halalaboos.huzuni.mod.visual.nametags.provider.render;

import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.huzuni.mod.visual.nametags.provider.TagRenderProvider;
import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.item.ItemStack;

import java.util.Optional;

import static net.halalaboos.mcwrapper.api.MCWrapper.getGLStateManager;
import static net.halalaboos.mcwrapper.api.MCWrapper.getTextRenderer;
import static org.lwjgl.opengl.GL11.glPolygonOffset;

public class ArmorRenderProvider implements TagRenderProvider {

	private Toggleable armor;
	private Toggleable enchants;

	public ArmorRenderProvider(Toggleable armor, Toggleable enchants) {
		this.armor = armor;
		this.enchants = enchants;
	}

	/**
	 * Renders the specified Player's items, as well as enchants if the option for either are enabled.
	 */
	@Override
	public void render(Living living, float x, float y) {
		if (!(living instanceof Player)) return;
		Player player = (Player)living;
		int totalItems = 0;
		glPolygonOffset(1.0F, -2000000.0F);
		getGLStateManager().enablePolygonOffset();
		getGLStateManager().enableDepth();
		getGLStateManager().depthMask(true);
		getGLStateManager().pushMatrix();
		for (int i = 0; i < 4; i++)
			totalItems++;
		totalItems++;
		int itemSize = 18, center = (-itemSize / 2), halfTotalSize = ((totalItems * itemSize) / 2 - itemSize) + (itemSize / 2), count = 0;
		if (player.getHeldItem(Hand.MAIN).isPresent()) {
			draw3dItem(player.getHeldItem(Hand.MAIN).get(), (center - halfTotalSize) + itemSize * count + 2, (int) y - 16);
			if (enchants.isEnabled())
				renderEnchantments(player.getHeldItem(Hand.MAIN).get(), (center - halfTotalSize) + itemSize * count + 2, (int) y - 16, 0.5F);
			count++;
		}
		for (int i = 4; i > 0; i--) {
			Optional<ItemStack> optional = player.getPlayerInventory().getArmorStack(i - 1);
			if (optional.isPresent()) {
				draw3dItem(optional.get(), (center - halfTotalSize) + itemSize * count, (int) y - 16);
				if (enchants.isEnabled())
					renderEnchantments(optional.get(), (center - halfTotalSize) + itemSize * count, (int) y - 16, 0.5F);
				count++;
			}
		}
		getGLStateManager().popMatrix();
		getGLStateManager().disablePolygonOffset();
		getGLStateManager().disableDepth();
		getGLStateManager().depthMask(false);
		
	}

	private void draw3dItem(ItemStack itemStack, int x, int y) {
		if (itemStack == null) return;
		itemStack.render3D(x, y);
	}

	/**
	 * Renders the name/level of all of the enchantments on the specified {@link ItemStack}
	 *
	 * @param item The item to obtain the list of enchants from
	 * @param x The x-position to render the text
	 * @param y The y-position to render the text
	 * @param scale The scale of the text
	 */
	private void renderEnchantments(ItemStack item, float x, float y, float scale) {
		float scaleInverse = 1F / scale, increment = 10F / scaleInverse;
		//Check if the item has any enchants
		if (item.getEnchants() != null) {
			//Loop through the enchants
			for (int i = 0; i < item.getEnchants().size(); i++) {
				//Get the name of the enchantment to render
				String name = item.getEnchants().get(i);

				//Setup rendering and draw the text
				getGLStateManager().pushMatrix();
				getGLStateManager().scale(scale, scale, scale);
				getTextRenderer().render(name, x * scaleInverse, ((int) y + (increment * i)) * scaleInverse, 0xFFFFFF);
				getGLStateManager().popMatrix();
			}
		}
	}

	@Override
	public boolean isEnabled(Living entity) {
		return armor.isEnabled();
	}
}
