package net.halalaboos.huzuni.mod.mining;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.huzuni.api.task.HotbarTask;
import net.halalaboos.huzuni.api.util.Timer;
import net.halalaboos.mcwrapper.api.client.GameKeybind;
import net.halalaboos.mcwrapper.api.entity.living.Living;
import net.halalaboos.mcwrapper.api.entity.living.player.GameType;
import net.halalaboos.mcwrapper.api.event.network.PacketSendEvent;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.network.packet.client.DiggingPacket;
import net.halalaboos.mcwrapper.api.network.packet.client.UseEntityPacket;
import net.halalaboos.mcwrapper.api.util.enums.DigAction;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.*;

/**
 * Automatically switches to the best tool needed for the player's given situation.
 * */
public class Autotool extends BasicMod {
	
	private final Toggleable weapon = new Toggleable("Swap weapon", "Swaps to the best weapon when in combat.");
	private final Value combatTime = new Value("Combat time", " ms", 100F, 1000F, 5000F, 50F, "Time required to pass until no longer considered in combat.");

	private final Timer timer = new Timer();

	private final HotbarTask hotbarTask = new HotbarTask(this) {

		@Override
		protected boolean isValid(ItemStack itemStack) {
			if (itemStack != null) {
				if (digging && hasBlock()) {
					return getPlayer().getRelativeHardness(position, itemStack) > 0.05F;
				} else
					return weapon.isEnabled() && !digging && hasEntity() &&
							getPlayer().calculateDamageWithAttackSpeed(entity, itemStack) > 1F;
			} else
				return false;
		}
		
		@Override
		protected boolean compare(ItemStack currentItem, ItemStack newItem) {
			if (newItem != null) {
				if (digging) {
					float currentHardness = getPlayer().getRelativeHardness(position, currentItem);
					float newHardness = getPlayer().getRelativeHardness(position, newItem);
					return currentHardness < newHardness;
				} else {
					float currentDamage = getPlayer().calculateDamageWithAttackSpeed(entity, currentItem);
					float newDamage = getPlayer().calculateDamageWithAttackSpeed(entity, newItem);
					return currentDamage < newDamage;
				}
			} else
				return false;
		}
	};
	
	private boolean digging = false;
	private Living entity = null;
	private Vector3i position = null;
	
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
		if (event.getPacket() instanceof DiggingPacket) {
			DiggingPacket packet = (DiggingPacket) event.getPacket();
			if (getController().getGameType() != GameType.CREATIVE && packet.getDigAction() == DigAction.START) {
				this.digging = true;
				this.entity = null;
				this.position = packet.getLocation();
				huzuni.hotbarManager.requestTask(this, hotbarTask);
			}
		} else if (event.getPacket() instanceof UseEntityPacket) {
			UseEntityPacket packet = (UseEntityPacket)event.getPacket();
			if (packet.getUseAction() == UseEntityPacket.UseAction.ATTACK) {
				net.halalaboos.mcwrapper.api.entity.Entity entity = packet.getEntity(getWorld());
				if (entity instanceof Living) {
					this.digging = false;
					this.position = null;
					this.entity = (Living)entity;
					timer.reset();
					huzuni.hotbarManager.requestTask(this, hotbarTask);
				}
			}
		}
	}

	private void onUpdate(PreMotionUpdateEvent event) {
		if (!getSettings().isKeyDown(GameKeybind.ATTACK) && digging) {
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
		return position != null;
	}

	/**
     * @return True if the auto tool has an entity.
     * */
	private boolean hasEntity() {
		return entity != null;
	}

}
