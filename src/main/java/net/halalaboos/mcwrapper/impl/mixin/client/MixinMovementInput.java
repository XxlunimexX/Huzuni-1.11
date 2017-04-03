package net.halalaboos.mcwrapper.impl.mixin.client;

import net.halalaboos.mcwrapper.api.client.Input;
import net.minecraft.util.MovementInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MovementInput.class)
public class MixinMovementInput implements Input {

	@Shadow public float moveForward;
	@Shadow public float moveStrafe;
	@Shadow public boolean sneak;
	@Shadow public boolean jump;

	@Override
	public float getForward() {
		return moveForward;
	}

	@Override
	public float getStrafe() {
		return moveStrafe;
	}

	@Override
	public boolean isSneak() {
		return sneak;
	}

	@Override
	public boolean isJump() {
		return jump;
	}
}
