package net.halalaboos.huzuni.mc.mixin;

import net.halalaboos.huzuni.mod.visual.Xray;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.renderer.BlockModelRenderer.class) public abstract class MixinBlockModelRenderer {

	private static final String METHOD_NAME = "Lnet/minecraft/client/renderer/BlockModelRenderer;renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/client/renderer/VertexBuffer;ZJ)Z";

	@Inject(method = METHOD_NAME, at = @At("HEAD"), cancellable = true)
	public void renderModel(IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn, BlockPos posIn,
							   VertexBuffer buffer, boolean checkSides, long rand, CallbackInfoReturnable<Boolean> ci) {
		if (Xray.INSTANCE.isEnabled()) {
			Block block = stateIn.getBlock();
			ci.setReturnValue(!Xray.INSTANCE.shouldIgnore(block) &&
					renderModelSmooth(worldIn, modelIn, stateIn, posIn, buffer, checkSides, rand));
		}
	}

	@Shadow
	public abstract boolean renderModelSmooth(IBlockAccess worldIn, IBakedModel modelIn, IBlockState stateIn,
											  BlockPos posIn, VertexBuffer buffer, boolean checkSides, long rand);
}
