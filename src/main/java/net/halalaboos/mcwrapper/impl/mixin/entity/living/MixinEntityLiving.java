package net.halalaboos.mcwrapper.impl.mixin.entity.living;

import net.halalaboos.mcwrapper.api.entity.living.data.HealthData;
import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.impl.mixin.entity.MixinEntity;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.entity.EntityLivingBase.class)
public abstract class MixinEntityLiving extends MixinEntity implements Living {

	@Shadow public abstract float getHealth();
	@Shadow public abstract float getMaxHealth();
	@Shadow public abstract boolean isOnLadder();
	@Shadow protected abstract void jump();

	@Shadow
	public abstract net.minecraft.item.ItemStack getHeldItem(EnumHand hand);

	@Override
	public HealthData getHealthData() {
		return new HealthData(getHealth(), getMaxHealth());
	}

	@Override
	public void doJump() {
		jump();
	}

	@Override
	public boolean getOnLadder() {
		return isOnLadder();
	}

	@Override
	public ItemStack getHeldItem(Hand hand) {
		return (ItemStack)(Object)getHeldItem(EnumHand.values()[hand.ordinal()]);
	}
}
