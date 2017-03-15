package net.halalaboos.huzuni.mc;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.mod.movement.Freecam;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.world.World;

/**
 * PlayerControllerMP.func_147493_a replaced with our hook
 * */
public class HuzuniEntityPlayer extends EntityPlayerSP {
	
	private static final Huzuni huzuni = Huzuni.INSTANCE;

	public HuzuniEntityPlayer(Minecraft mcIn, World worldIn, NetHandlerPlayClient netHandler, StatisticsManager statFile) {
		super(mcIn, worldIn, netHandler, statFile);
	}

	@Override
	public boolean isEntityInsideOpaqueBlock() {
		return !Freecam.INSTANCE.isEnabled() && super.isEntityInsideOpaqueBlock();
	}

	@Override
	protected boolean pushOutOfBlocks(double par1, double par3, double par5) {
		return !Freecam.INSTANCE.isEnabled() && super.pushOutOfBlocks(par1, par3, par5);
	}
}
