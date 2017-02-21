package net.halalaboos.mcwrapper.impl.mixin.entity.living.player;

import com.mojang.authlib.GameProfile;
import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.impl.mixin.entity.living.MixinEntityLiving;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.util.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.entity.player.EntityPlayer.class)
public abstract class MixinEntityPlayer extends MixinEntityLiving implements Player {

	@Shadow public PlayerCapabilities capabilities;
	@Shadow public InventoryPlayer inventory;
	@Shadow protected FoodStats foodStats;
	@Shadow public abstract float getCooledAttackStrength(float adjustTicks);

	@Shadow
	public abstract GameProfile getGameProfile();

	@Override
	public boolean isNPC() {
		return MCWrapper.getPlayer().getInfo(this) == null;
	}

	@Override
	public float getFood() {
		return foodStats.getFoodLevel();
	}

	@Override
	public float getSaturation() {
		return foodStats.getSaturationLevel();
	}

	@Override
	public ItemStack getStack(int slot) {
		return (ItemStack)(Object)inventory.getStackInSlot(slot);
	}

	@Override
	public float getAttackStrength() {
		return getCooledAttackStrength(0F);
	}

	@Override
	public GameProfile getProfile() {
		return getGameProfile();
	}
}
