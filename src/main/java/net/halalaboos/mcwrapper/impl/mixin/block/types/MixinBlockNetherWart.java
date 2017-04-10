package net.halalaboos.mcwrapper.impl.mixin.block.types;

import net.halalaboos.mcwrapper.api.block.types.NetherWart;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.halalaboos.mcwrapper.impl.Convert;
import net.halalaboos.mcwrapper.impl.mixin.block.MixinBlock;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.properties.PropertyInteger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockNetherWart.class)
public abstract class MixinBlockNetherWart extends MixinBlock implements NetherWart {

	@Shadow @Final public static PropertyInteger AGE;

	@Override
	public int getAge(Vector3i pos) {
		return Convert.world().getBlockState(Convert.to(pos)).getValue(AGE);
	}

	@Override
	public int getMaxAge() {
		return 3;
	}
}
