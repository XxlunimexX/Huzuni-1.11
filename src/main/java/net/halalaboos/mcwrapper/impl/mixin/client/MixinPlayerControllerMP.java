package net.halalaboos.mcwrapper.impl.mixin.client;

import net.halalaboos.mcwrapper.api.client.Controller;
import net.halalaboos.mcwrapper.api.entity.living.player.GameType;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerControllerMP.class)
public abstract class MixinPlayerControllerMP implements Controller {

	@Shadow private float curBlockDamageMP;
	@Shadow private int blockHitDelay;
	@Shadow private boolean isHittingBlock;
	@Shadow public abstract float getBlockReachDistance();
	@Shadow public abstract void resetBlockRemoving();
	@Shadow public abstract void updateController();
	@Shadow public abstract net.minecraft.world.GameType getCurrentGameType();

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
}
