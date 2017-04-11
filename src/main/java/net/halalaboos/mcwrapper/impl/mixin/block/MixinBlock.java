package net.halalaboos.mcwrapper.impl.mixin.block;

import net.halalaboos.huzuni.mod.visual.Xray;
import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.halalaboos.mcwrapper.api.world.World;
import net.halalaboos.mcwrapper.impl.Convert;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.block.Block.class)
public abstract class MixinBlock implements Block {

	@Shadow public float slipperiness;
	@Shadow private IBlockState defaultBlockState;

	@Shadow
	public abstract String getLocalizedName();

	@Shadow
	@Final
	public static RegistryNamespacedDefaultedByKey<ResourceLocation, net.minecraft.block.Block> REGISTRY;

	@Override
	public float getSlipperiness() {
		return slipperiness;
	}

	@Override
	public void setSlipperiness(float slipperiness) {
		this.slipperiness = slipperiness;
	}

	@Override
	public float blockStrength(Player player, World world, Vector3i pos) {
		return defaultBlockState.getBlockHardness(Minecraft.getMinecraft().world, Convert.to(pos));
	}

	@Override
	public String name() {
		return getLocalizedName();
	}

	@Override
	public int getId() {
		return REGISTRY.getIDForObject((net.minecraft.block.Block) (Object) this);
	}

	@Inject(method = "shouldSideBeRendered", at = @At("HEAD"), cancellable = true)
	public void shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side,
									 CallbackInfoReturnable<Boolean> ci) {
		if (Xray.INSTANCE.isEnabled()) {
			ci.setReturnValue(Xray.INSTANCE.isEnabled((net.halalaboos.mcwrapper.api.block.Block)blockState.getBlock()));
		}
	}

	@Inject(method = "getAmbientOcclusionLightValue", at = @At("HEAD"), cancellable = true)
	public void getAmbientOcclusionLightValue(IBlockState state, CallbackInfoReturnable<Float> ci) {
		if (Xray.INSTANCE.isEnabled()) {
			ci.setReturnValue(10000F);
		}
	}
}
