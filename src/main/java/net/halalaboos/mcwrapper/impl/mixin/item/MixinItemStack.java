package net.halalaboos.mcwrapper.impl.mixin.item;

import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.item.Item;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.halalaboos.mcwrapper.impl.Convert;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(net.minecraft.item.ItemStack.class)
public abstract class MixinItemStack implements ItemStack {

	@Shadow public abstract int getCount();
	@Shadow public abstract net.minecraft.item.Item getItem();
	@Shadow public abstract String getDisplayName();

	@Shadow
	public abstract int getMaxItemUseDuration();

	@Shadow
	public abstract boolean isEmpty();

	@Shadow
	public abstract int getMaxStackSize();

	@Shadow
	public abstract float getStrVsBlock(IBlockState blockIn);

	@Shadow @Nullable public abstract NBTTagCompound getTagCompound();

	@Shadow public abstract void setTagCompound(@Nullable NBTTagCompound nbt);

	@Shadow @Nullable public abstract NBTTagList getEnchantmentTagList();

	private Minecraft mc = Minecraft.getMinecraft();

	@Override
	public int getSize() {
		return getCount();
	}

	@Override
	public float getStrength(Vector3i pos) {
		return getStrVsBlock(mc.world.getBlockState(Convert.to(pos)));
	}

	@Override
	public Item getItemType() {
		return ((Item) getItem());
	}

	@Override
	public String name() {
		return getDisplayName();
	}

	@Override
	public int getMaxUseTicks() {
		return getMaxItemUseDuration();
	}

	@Override
	public void renderInGui(int x, int y) {
		GlStateManager.pushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		try {
			GlStateManager.translate(0.0F, 0.0F, 32.0F);
			mc.getRenderItem().zLevel = 200F;
			mc.getRenderItem().renderItemAndEffectIntoGUI(getMCItemStack(), x, y);
			mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, getMCItemStack(), x, y, "");
			mc.getRenderItem().zLevel = 0F;
		} catch (Exception e) {
			e.printStackTrace();
		}
		RenderHelper.disableStandardItemLighting();
		GlStateManager.popMatrix();
	}

	@Override
	public void render3D(int x, int y) {
		mc.getRenderItem().zLevel = -150F;
		mc.getRenderItem().renderItemAndEffectIntoGUI(getMCItemStack(), x, y);
		mc.getRenderItem().zLevel = 0F;
	}

	@Override
	public int getMaxSize() {
		return getMaxStackSize();
	}

	@Override
	public boolean empty() {
		return isEmpty();
	}

	@Override
	public void addEnchant(String enchantmentName, short level) {
		Enchantment enchantment = get(enchantmentName);
		if (enchantment != null) {
			if (getTagCompound() == null) {
				setTagCompound(new NBTTagCompound());
			}
			if (!getTagCompound().hasKey("ench", 9)) {
				getTagCompound().setTag("ench", new NBTTagList());
			}
			NBTTagList nbttaglist = getTagCompound().getTagList("ench", 10);
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			nbttagcompound.setShort("id", (short)Enchantment.getEnchantmentID(enchantment));
			nbttagcompound.setShort("lvl", level);
			nbttaglist.appendTag(nbttagcompound);
			MCWrapper.getController().update();
		}
	}

	@Override
	public List<String> getEnchants() {
		if (getEnchantmentTagList() == null) return null;
		ArrayList<String> out = new ArrayList<>();
		NBTTagList enchantments = getEnchantmentTagList();
		for (int i = 0; i < enchantments.tagCount(); i++) {
			NBTTagCompound compound = enchantments.getCompoundTagAt(i);
			if (Enchantment.getEnchantmentByID(compound.getByte("id")) != null) {
				String name = Enchantment.getEnchantmentByID(compound.getByte("id")).getTranslatedName(compound.getByte("lvl")).substring(0, 4) + " " + compound.getByte("lvl");
				out.add(name);
			}
		}
		return out;
	}

	private Enchantment get(String name) {
		for (Enchantment enchantment : Enchantment.REGISTRY) {
			String translatedName = I18n.translateToLocal(enchantment.getName());
			if (enchantment.getName().equals(name) || translatedName.equalsIgnoreCase(name)
					|| translatedName.replaceAll(" ", "").equalsIgnoreCase(name)) {
				return enchantment;
			}
		}
		return null;
	}

	private net.minecraft.item.ItemStack getMCItemStack() {
		return (net.minecraft.item.ItemStack)(Object)this;
	}
}
