package net.halalaboos.mcwrapper.impl.mixin.entity.living.player;

import com.mojang.authlib.GameProfile;
import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.inventory.Container;
import net.halalaboos.mcwrapper.api.inventory.PlayerInventory;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.impl.Convert;
import net.halalaboos.mcwrapper.impl.mixin.entity.living.MixinEntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.util.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(net.minecraft.entity.player.EntityPlayer.class)
public abstract class MixinEntityPlayer extends MixinEntityLiving implements Player {

	@Shadow public PlayerCapabilities capabilities;
	@Shadow public InventoryPlayer inventory;
	@Shadow protected FoodStats foodStats;
	@Shadow public abstract float getCooledAttackStrength(float adjustTicks);
	@Shadow public abstract GameProfile getGameProfile();
	@Shadow public net.minecraft.inventory.Container inventoryContainer;

	@Override
	public boolean isNPC() {
		if (npc) npc = MCWrapper.getPlayer().getInfo(this) == null;
		return npc;
	}

	@Override
	public float getFood() {
		return foodStats.getFoodLevel();
	}

	@Override
	public boolean isHungry() {
		return foodStats.needFood();
	}

	@Override
	public float getSaturation() {
		return foodStats.getSaturationLevel();
	}

	@Override
	public Optional<ItemStack> getStack(int slot) {
		return Convert.getOptional(inventory.getStackInSlot(slot));
	}

	@Override
	public float getAttackStrength() {
		return getCooledAttackStrength(0F);
	}

	@Override
	public GameProfile getProfile() {
		return getGameProfile();
	}

	@Override
	public Container getInventoryContainer() {
		return ((Container) inventoryContainer);
	}

	@Override
	public PlayerInventory getPlayerInventory() {
		return ((PlayerInventory) inventory);
	}

	private boolean npc = true; //This corrects itself, no worries here!

	private boolean pushedByWater = true;

	private boolean fakeClip = false;

	@Redirect(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;isSpectator()Z", ordinal = 0))
	public boolean spec(EntityPlayer entityPlayer) {
		return entityPlayer.isSpectator() || fakeClip;
	}

	@Override
	protected boolean pushOutOfBlocks(double x, double y, double z) {
		return !fakeClip && super.pushOutOfBlocks(x, y, z);
	}

	@Override
	public boolean isEntityInsideOpaqueBlock() {
		return !fakeClip && super.isEntityInsideOpaqueBlock();
	}

	@Override
	public void setNoClip(boolean noClip) {
		this.fakeClip = noClip;
	}

	@Override
	public void setPushedByWater(boolean pushedByWater) {
		this.pushedByWater = pushedByWater;
	}

	@Inject(method = "isPushedByWater", at = @At("HEAD"), cancellable = true)
	public void isPushedByWater(CallbackInfoReturnable<Boolean> ci) {
		if (!pushedByWater) {
			ci.setReturnValue(false);
		}
	}
}
