package net.halalaboos.mcwrapper.impl.mixin.client;

import net.halalaboos.huzuni.mc.Wrapper;
import net.minecraft.client.renderer.chunk.SetVisibility;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.renderer.chunk.VisGraph.class) public class MixinVisGraph {

	@Inject(method = "setOpaqueCube", at = @At("HEAD"), cancellable = true)
	public void setOpaqueCube(BlockPos pos, CallbackInfo ci) {
		if (Wrapper.shouldIgnoreCulling()) {
			ci.cancel();
		}
	}

	@Inject(method = "computeVisibility", at = @At("HEAD"), cancellable = true)
	public void computeVisibility(CallbackInfoReturnable<SetVisibility> ci) {
		if (Wrapper.shouldIgnoreCulling()) {
			SetVisibility setVisibility = new SetVisibility();
			setVisibility.setAllVisible(true);
			ci.setReturnValue(setVisibility);
		}
	}

}
