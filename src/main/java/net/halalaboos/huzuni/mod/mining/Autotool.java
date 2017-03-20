package net.halalaboos.huzuni.mod.mining;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Toggleable;
import net.halalaboos.huzuni.api.node.Value;
import net.halalaboos.huzuni.api.task.HotbarTask;
import net.halalaboos.huzuni.api.util.Timer;
import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.event.network.PacketSendEvent;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.network.packet.client.UseEntityPacket;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.getWorld;

/**
 * Automatically switches to the best tool needed for the player's given situation.
 * */
public class Autotool extends BasicMod {
	
	public final Toggleable weapon = new Toggleable("Swap weapon", "Swaps to the best weapon when in combat.");
	public final Value combatTime = new Value("Combat time", " ms", 100F, 1000F, 5000F, 50F, "Time required to pass until no longer considered in combat.");

	private final Timer timer = new Timer();

	private final HotbarTask hotbarTask = new HotbarTask(this) {

		@Override
		protected boolean isValid(ItemStack itemStack) {
			if (itemStack != null) {
				if (digging && hasBlock()) {
					return itemStack.getStrength(position.getX(), position.getY(), position.getZ()) > 0.055555556F;
				} else if (weapon.isEnabled() && !digging && hasEntity()) {
					//TODO
				} else
					return false;
			} else
				return false;
		}
		
		@Override
		protected boolean compare(ItemStack currentItem, ItemStack newItem) {
			if (newItem != null) {
				if (digging) {
					int x = position.getX();
					int y = position.getY();
					int z = position.getZ();
					float currentHardness = currentItem.getStrength(x, y, z);
					float newHardness = newItem.getStrength(x, y, z);
					return currentHardness < newHardness;
				} else {
					return false;
				}
			} else
				return false;
		}
	};

	
	private boolean digging = false;
	private Living entity = null;
	private IBlockState blockState = null;
	private BlockPos position = null;
	
	public Autotool() {
		super("Auto tool", "Switches to the best tool in your hotbar when mining", Keyboard.KEY_J);
		this.setCategory(Category.MINING);
		setAuthor("Halalaboos");
		this.addChildren(weapon, combatTime);
		weapon.setEnabled(true);
		huzuni.hotbarManager.registerTaskHolder(this);
		subscribe(PacketSendEvent.class, this::onPacket);
		subscribe(PreMotionUpdateEvent.class, this::onUpdate);
	}

	@Override
	public void onDisable() {
		huzuni.hotbarManager.withdrawTask(hotbarTask);
	}

	private void onPacket(PacketSendEvent event) {
		if (event.getPacket() instanceof CPacketPlayerDigging) {
			CPacketPlayerDigging packet = (CPacketPlayerDigging) event.getPacket();
			if (!mc.playerController.isInCreativeMode() && packet.getAction() == CPacketPlayerDigging.Action.START_DESTROY_BLOCK) {
				this.digging = true;
				this.entity = null;
				this.position = packet.getPosition();
				this.blockState = mc.world.getBlockState(this.position);
				huzuni.hotbarManager.requestTask(this, hotbarTask);
			}
		} else if (event.getPacket() instanceof UseEntityPacket) {
			UseEntityPacket packet = (UseEntityPacket)event.getPacket();
			if (packet.getUseAction() == UseEntityPacket.UseAction.ATTACK) {
				net.halalaboos.mcwrapper.api.entity.Entity entity = packet.getEntity(getWorld());
				if (entity instanceof Living) {
					this.digging = false;
					this.position = null;
					this.blockState = null;
					this.entity = (Living)entity;
					timer.reset();
					huzuni.hotbarManager.requestTask(this, hotbarTask);
				}
			}
		}
	}

	private void onUpdate(PreMotionUpdateEvent event) {
		if (!mc.gameSettings.keyBindAttack.isKeyDown() && digging) {
			this.blockState = null;
			this.position = null;
			this.digging = false;
			huzuni.hotbarManager.withdrawTask(hotbarTask);
		}
		if (timer.hasReach((int) combatTime.getValue()) && weapon.isEnabled() && !digging) {
			this.entity = null;
			huzuni.hotbarManager.withdrawTask(hotbarTask);
		}
	}

	/**
     * @return True if the auto tool has a block.
     * */
	private boolean hasBlock() {
		return blockState != null && position != null;
	}

	/**
     * @return True if the auto tool has an entity.
     * */
	private boolean hasEntity() {
		return entity != null;
	}

}
