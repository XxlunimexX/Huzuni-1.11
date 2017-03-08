package net.halalaboos.huzuni.mod.mining;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.UpdateEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Toggleable;
import net.halalaboos.huzuni.api.node.Value;
import net.halalaboos.mcwrapper.api.entity.living.player.GameType;
import net.halalaboos.mcwrapper.api.event.PacketSendEvent;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.getController;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Modifies player mine speed on all blocks.
 * */
public class Speedmine extends BasicMod {

	public final Value speed = new Value("Mine speed", 1F, 1F, 2F, "Mine speed modifier");
	public final Value breakPercent = new Value("Break Percent", 0F, 97F, 100F, 1F, "Block damage percent to break at");
	public final Value hitDelay = new Value("Hit Delay", 0F, 0F, 5F, 1F, "The delay between breaking blocks.");
	private final Toggleable noSlow = new Toggleable("No Slowdown", "Allows you to dig under yourself quicker.");

	private boolean digging = false;
	
	private float curBlockDamage = 0;

	private EnumFacing facing;

	private BlockPos position;

	public Speedmine() {
		super("Speedmine", "Mines blocks at a faster rate", Keyboard.KEY_V);
		this.setCategory(Category.MINING);
		setAuthor("Halalaboos");
		this.addChildren(speed, breakPercent, hitDelay, noSlow);
		subscribe(PacketSendEvent.class, this::onPacket);
	}

	@Override
	protected void onEnable() {
		huzuni.eventManager.addListener(this);
	}

	@Override
	protected void onDisable() {
		huzuni.eventManager.removeListener(this);
	}

	private void onPacket(PacketSendEvent event) {
		if (getController() != null && getController().getGameType() != GameType.CREATIVE) {
			if (event.getPacket() instanceof CPacketPlayerDigging) {
				CPacketPlayerDigging packet = (CPacketPlayerDigging) event.getPacket();
				if (packet.getAction() == CPacketPlayerDigging.Action.START_DESTROY_BLOCK) {
					digging = true;
					this.position = packet.getPosition();
					this.facing = packet.getFacing();
					this.curBlockDamage = 0;
				} else if (packet.getAction() == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK || packet.getAction() == CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
					digging = false;
					this.position = null;
					this.facing = null;
				}
			}
		}
	}

	@EventMethod
	public void onUpdate(UpdateEvent event) {
		if (event.type == UpdateEvent.Type.PRE) {
			if (getController().getHitDelay() > hitDelay.getValue()) {
				getController().setHitDelay(((int) hitDelay.getValue()));
			}
			if (digging) {
				IBlockState blockState = this.mc.world.getBlockState(position);
				float multiplier = noSlow.isEnabled() && getPlayer().getFallDistance() <= 1F
						&& getPlayer().getFallDistance() > 0 ? 5F : 1F;
				curBlockDamage += blockState.getPlayerRelativeBlockHardness(this.mc.player, this.mc.world, this.position) * (speed.getValue()) * multiplier;
				if (curBlockDamage >= breakPercent.getValue() / 100F) {
					getController().onBlockDestroy(new Vector3i(position.getX(), position.getY(), position.getZ()));
					mc.getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.position, this.facing));
					curBlockDamage = 0F;
					digging = false;
				}
			}
		}
	}
}
