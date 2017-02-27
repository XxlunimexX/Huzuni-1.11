package net.halalaboos.mcwrapper.impl.mixin.item;

import net.halalaboos.mcwrapper.api.item.Item;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.item.ItemStack.class)
public abstract class MixinItemStack implements ItemStack {

	@Shadow public abstract int getCount();
	@Shadow public abstract net.minecraft.item.Item getItem();
	@Shadow public abstract String getDisplayName();

	@Override
	public int getSize() {
		return getCount();
	}

	@Override
	public Item getItemType() {
		return ((Item) getItem());
	}

	@Override
	public String getName() {
		return getDisplayName();
	}

	@Override
	public void renderInGui(int x, int y) {
		Minecraft mc = Minecraft.getMinecraft();
		GlStateManager.pushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		try {
			GlStateManager.translate(0.0F, 0.0F, 32.0F);
			mc.getRenderItem().zLevel = 200F;
			mc.getRenderItem().renderItemAndEffectIntoGUI((net.minecraft.item.ItemStack)(Object)this, x, y);
			mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, (net.minecraft.item.ItemStack)(Object)this, x, y, "");
			mc.getRenderItem().zLevel = 0F;
		} catch (Exception e) {
			e.printStackTrace();
		}
		RenderHelper.disableStandardItemLighting();
		GlStateManager.popMatrix();
	}
}
