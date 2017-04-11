package net.halalaboos.mcwrapper.impl.mixin.client;


import net.halalaboos.huzuni.mod.visual.Xray;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.renderer.BlockRendererDispatcher.class)
public abstract class MixinBlockModelRenderer {

	@Inject(method = "renderBlock(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/VertexBuffer;)Z", at = @At("HEAD"), cancellable = true)
	public void renderBlock(IBlockState state, BlockPos pos, IBlockAccess blockAccess, VertexBuffer worldRendererIn, CallbackInfoReturnable<Boolean> ci) {
		if (Xray.INSTANCE.isEnabled()) {
			Block block = state.getBlock();
			if (Xray.INSTANCE.shouldIgnore((net.halalaboos.mcwrapper.api.block.Block)block)) {
				ci.setReturnValue(false);
			}
		}
	}
}
