package net.halalaboos.mcwrapper.impl.mixin.client;

import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.client.Controller;
import net.halalaboos.mcwrapper.api.entity.living.player.GameType;
import net.halalaboos.mcwrapper.api.event.player.BlockDigEvent;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.halalaboos.mcwrapper.impl.Convert;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public abstract class MixinPlayerControllerMP implements Controller {

	@Shadow private float curBlockDamageMP;
	@Shadow private int blockHitDelay;
	@Shadow private boolean isHittingBlock;
	@Shadow public abstract float getBlockReachDistance();
	@Shadow public abstract void resetBlockRemoving();
	@Shadow public abstract void updateController();
	@Shadow public abstract net.minecraft.world.GameType getCurrentGameType();
	@Shadow public abstract boolean onPlayerDestroyBlock(BlockPos pos);

	@Inject(method = "onPlayerDamageBlock", at = @At(value = "FIELD", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;curBlockDamageMP:F", ordinal = 0))
	public void addBlockDigEvent(BlockPos pos, EnumFacing facing, CallbackInfoReturnable<Boolean> ci) {
		BlockDigEvent digEvent = new BlockDigEvent(Convert.from(pos), this.curBlockDamageMP, facing.ordinal());
		MCWrapper.getEventManager().publish(digEvent);
		this.curBlockDamageMP = digEvent.progress;
		this.curBlockDamageMP *= digEvent.multiplier;
	}

	@Override
	public float getBlockDamage() {
		return curBlockDamageMP;
	}

	@Override
	public void setBlockDamage(float damage) {
		this.curBlockDamageMP = damage;
	}

	@Override
	public int getHitDelay() {
		return blockHitDelay;
	}

	@Override
	public void setHitDelay(int hitDelay) {
		this.blockHitDelay = hitDelay;
	}

	@Override
	public boolean isHittingBlock() {
		return isHittingBlock;
	}

	@Override
	public float getBlockReach() {
		return getBlockReachDistance();
	}

	@Override
	public void resetDigging() {
		resetBlockRemoving();
	}

	@Override
	public void update() {
		updateController();
	}

	@Override
	public GameType getGameType() {
		return GameType.values()[getCurrentGameType().ordinal()];
	}

	@Override
	public boolean onBlockDestroy(Vector3i blockPosition) {
		return onPlayerDestroyBlock(Convert.to(blockPosition));
	}
}
