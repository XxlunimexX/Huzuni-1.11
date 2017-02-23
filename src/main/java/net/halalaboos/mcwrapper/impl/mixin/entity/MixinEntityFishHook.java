package net.halalaboos.mcwrapper.impl.mixin.entity;

import net.halalaboos.mcwrapper.api.entity.projectile.FishHook;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityFishHook.class)
public abstract class MixinEntityFishHook extends MixinEntity implements FishHook {

	@Shadow public abstract EntityPlayer getAngler();

	@Override
	public Player getOwner() {
		return (Player)getAngler();
	}
}
