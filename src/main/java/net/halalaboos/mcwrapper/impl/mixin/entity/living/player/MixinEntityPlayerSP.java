package net.halalaboos.mcwrapper.impl.mixin.entity.living.player;

import net.halalaboos.mcwrapper.api.entity.living.player.ClientPlayer;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.util.Rotation;
import net.halalaboos.mcwrapper.api.util.Vector3d;
import net.minecraft.util.EnumHand;
import net.minecraft.util.MovementInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.client.entity.EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends MixinAbstractClientPlayer implements ClientPlayer {

	@Shadow
	public abstract void swingArm(EnumHand hand);

	@Shadow
	public abstract void closeScreen();

	@Shadow
	public MovementInput movementInput;

	@Override
	public void swingItem(Hand hand) {
		swingArm(EnumHand.values()[hand.ordinal()]);
	}

	@Override
	public void closeWindow() {
		closeScreen();
	}

	@Override
	public boolean isFlying() {
		return capabilities.isFlying;
	}

	@Override
	public float getForwardMovement() {
		return movementInput.moveForward;
	}
}
