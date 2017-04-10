package net.halalaboos.mcwrapper.impl.mixin.block.types;

import net.halalaboos.mcwrapper.api.client.ClientPlayer;
import net.halalaboos.mcwrapper.impl.mixin.block.MixinBlock;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.halalaboos.mcwrapper.impl.Convert.player;

@Mixin(BlockWeb.class)
public abstract class MixinBlockWeb extends MixinBlock {

	@Inject(method = "onEntityCollidedWithBlock", at = @At("HEAD"), cancellable = true)
	public void adjustPlayerSpeed(World worldIn, BlockPos pos, IBlockState state, Entity entityIn, CallbackInfo ci) {
		if (entityIn == player()) {
			if (!((ClientPlayer) entityIn).isSlowedByBlocks()) {
				ci.cancel();
			}
		}
	}
}
