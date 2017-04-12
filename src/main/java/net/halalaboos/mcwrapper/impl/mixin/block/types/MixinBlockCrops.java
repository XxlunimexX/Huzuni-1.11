package net.halalaboos.mcwrapper.impl.mixin.block.types;

import net.halalaboos.mcwrapper.api.block.types.Crops;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.halalaboos.mcwrapper.impl.Convert;
import net.halalaboos.mcwrapper.impl.mixin.block.MixinBlock;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.*;

@Mixin(BlockCrops.class)
@Implements(@Interface(iface = Crops.class, prefix = "api$"))
public abstract class MixinBlockCrops extends MixinBlock implements Crops {
	@Shadow public abstract int getMetaFromState(IBlockState state);

	@Override
	public int getAge(Vector3i pos) {
		return getMetaFromState(Convert.world().getBlockState(Convert.to(pos)));
	}

	@Intrinsic
	public int api$getMaxAge() {
		return getMaxAge();
	}
}
