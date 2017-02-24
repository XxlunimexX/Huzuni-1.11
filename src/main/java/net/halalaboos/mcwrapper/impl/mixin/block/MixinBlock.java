package net.halalaboos.mcwrapper.impl.mixin.block;

import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.halalaboos.mcwrapper.api.world.World;
import net.halalaboos.mcwrapper.impl.Convert;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(net.minecraft.block.Block.class)
public abstract class MixinBlock implements Block {

	@Shadow public float slipperiness;
	@Shadow private IBlockState defaultBlockState;

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
}
