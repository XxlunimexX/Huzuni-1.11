package net.halalaboos.huzuni.mc.mixin;

import net.halalaboos.huzuni.mod.visual.Xray;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.block.Block.class) public class MixinBlock {

	@Inject(method = "shouldSideBeRendered", at = @At("HEAD"), cancellable = true)
	public void shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side,
									 CallbackInfoReturnable<Boolean> ci) {
		if (Xray.INSTANCE.isEnabled()) {
			ci.setReturnValue(Xray.INSTANCE.isEnabled(blockState.getBlock()));
		}
	}

	@Inject(method = "getAmbientOcclusionLightValue", at = @At("HEAD"), cancellable = true)
	public void getAmbientOcclusionLightValue(IBlockState state, CallbackInfoReturnable<Float> ci) {
		if (Xray.INSTANCE.isEnabled()) {
			ci.setReturnValue(10000F);
		}
	}

}
