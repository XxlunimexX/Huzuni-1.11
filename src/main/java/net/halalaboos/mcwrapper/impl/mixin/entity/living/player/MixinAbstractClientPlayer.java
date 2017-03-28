package net.halalaboos.mcwrapper.impl.mixin.entity.living.player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.client.entity.AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer extends MixinEntityPlayer {

	@Shadow public abstract boolean isSpectator();

}
