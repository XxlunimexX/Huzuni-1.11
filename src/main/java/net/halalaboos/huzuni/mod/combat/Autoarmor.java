package net.halalaboos.huzuni.mod.combat;

import com.google.gson.JsonObject;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.attribute.Nameable;
import net.halalaboos.huzuni.api.node.impl.ItemList;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.huzuni.api.task.ClickTask;
import net.halalaboos.huzuni.api.util.Timer;
import net.halalaboos.huzuni.gui.Notification.NotificationType;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.inventory.Slot;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.item.enchant.Enchantment;
import net.halalaboos.mcwrapper.api.item.enchant.EnchantmentTypes;
import net.halalaboos.mcwrapper.api.item.types.Armor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.halalaboos.mcwrapper.api.MCWrapper.getAdapter;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Automatically equips the preferred armor.
 * */
public class Autoarmor extends BasicMod {
	
	public final ItemList<EnchantmentItem> enchantmentOrder = new ItemList<EnchantmentItem>("Enchantment Priority", "Put each armor enchantment into an order of importance (first being most important).") {

		@Override
		public void load(JsonObject json) throws IOException {
			this.clearChildren();
			super.load(json);
		}
		@Override
		protected void saveItem(JsonObject object, EnchantmentItem enchantmentItem) {
			object.addProperty("enchantmentId", getAdapter().getEnchantmentRegistry().getId(enchantmentItem.getEnchantment()));
		}

		@Override
		protected EnchantmentItem loadItem(JsonObject object) {
			return new EnchantmentItem(getAdapter().getEnchantmentRegistry().getEnchant(object.get("enchantmentId").getAsInt()));
		}
	
	};
	
	public final Value delay = new Value("Attempt Delay", " ms", 0F, 200F, 2000F, 10F, "Delay in MS between each attempt.");
	
	private final Timer timer = new Timer();

	private final ClickTask clickTask = new ClickTask(this);

	private int[] bestSlot = new int[] {
			-1, -1, -1, -1
	};
	
	public Autoarmor() {
		super("Auto armor", "Automagically equip the best armor within your inventory");
		this.setCategory(Category.COMBAT);
		setAuthor("Halalaboos");
		this.addChildren(delay, enchantmentOrder);
		enchantmentOrder.setOrdered(true);
		enchantmentOrder.add(new EnchantmentItem(EnchantmentTypes.PROTECTION));
		enchantmentOrder.add(new EnchantmentItem(EnchantmentTypes.PROJECTILE_PROTECTION));
		enchantmentOrder.add(new EnchantmentItem(EnchantmentTypes.FIRE_PROTECTION));
		enchantmentOrder.add(new EnchantmentItem(EnchantmentTypes.BLAST_PROTECTION));
		enchantmentOrder.add(new EnchantmentItem(EnchantmentTypes.THORNS));
		huzuni.clickManager.registerTaskHolder(this);
		subscribe(PreMotionUpdateEvent.class, this::onUpdate);
	}

	@Override
	public void onDisable() {
		huzuni.clickManager.withdrawTask(clickTask);
	}

	private void onUpdate(PreMotionUpdateEvent event) {
		if (mc.isScreenOpen())
    		return;
        List<Integer> armors = getArmor();
        for (int i : armors) {
            Slot slot = getPlayer().getInventoryContainer().getSlotAt(i);
            if (slot.getItem().isPresent()) {
				Armor armor = (Armor) slot.getItem().get().getItemType();
				if (bestSlot[armor.getType()] != -1) {
					Slot oldSlot = getPlayer().getInventoryContainer().getSlotAt(bestSlot[armor.getType()]);
					if (oldSlot.getItem().isPresent()) {
						if (compare(armor, (Armor) oldSlot.getItem().get().getItemType(), slot.getItem().get(), oldSlot.getItem().get())) {
							bestSlot[armor.getType()] = i;
						}
					}
				} else {
					Slot wearingSlot = getWearingArmor(armor.getType());
					if (wearingSlot.getItem().isPresent()) {
						if (!(wearingSlot.getItem().get().getItemType() instanceof Armor)) {
							bestSlot[8 - wearingSlot.getSlotNumber()] = i;
						} else {
							Armor wearingArmor = (Armor) wearingSlot.getItem().get().getItemType();
							if (compare(armor, wearingArmor, slot.getItem().get(), wearingSlot.getItem().get())) {
								bestSlot[8 - wearingSlot.getSlotNumber()] = i;
							}
						}
					} else {
						bestSlot[8 - wearingSlot.getSlotNumber()] = i;
					}
				}
			}
        }
        for (int i = 0; i < bestSlot.length; i++) {
        	if (bestSlot[i] != -1) {
        		replace(bestSlot[i], 8 - i);
        	}
        	bestSlot[i] = -1;
        }
        if (clickTask.hasClicks())
        	huzuni.clickManager.requestTask(this, clickTask);
		else
			huzuni.clickManager.withdrawTask(clickTask);
	}

    /**
     * Compares two item stacks (and their associated item) in regards to enchantment value.
     */
	private boolean compare(Armor newArmor, Armor oldArmor, ItemStack newStack, ItemStack oldStack) {
		for (int i = 0; i < enchantmentOrder.size(); i++) {
			Enchantment enchantment = enchantmentOrder.get(i).getEnchantment();
			int oldEnchantment, newEnchantment;
        	if (enchantment == EnchantmentTypes.PROTECTION) {
        		oldEnchantment = oldArmor.getDamageReduceAmount() + enchantment.getLevel(oldStack);
                newEnchantment = newArmor.getDamageReduceAmount() + enchantment.getLevel(newStack);
        	} else {
        		oldEnchantment = enchantment.getLevel(oldStack);
                newEnchantment = enchantment.getLevel(newStack);
        	}
        	if (oldEnchantment == newEnchantment) {
        		continue;
        	} else {
        		return oldEnchantment < newEnchantment;
        	}
        }
		return false;
	}

	/**
     * Attempts to replace the old armor slot with the new armor item.
     * */
	private void replace(int newArmor, int oldArmor) {
		Slot oldSlot = getPlayer().getInventoryContainer().getSlotAt(oldArmor);
		if (clickTask.containsClick(newArmor, 0, 0))
			timer.reset();
		else if (timer.hasReach((int) delay.getValue())) {
			if (!oldSlot.getItem().isPresent()) {
				clickTask.add(newArmor, 0, 0);
				clickTask.add(oldArmor, 0, 0);
			} else {
				clickTask.add(newArmor, 0, 0);
				clickTask.add(oldArmor, 0, 0);
				clickTask.add(newArmor, 0, 0);
			}
			huzuni.clickManager.requestTask(this, clickTask);
			huzuni.addNotification(NotificationType.INFO, this, 5000, "Equipping armor!");
		}
	}

    /**
     * Finds all pieces of armor within the inventory.
     */
    private List<Integer> getArmor() {
        List<Integer> list = new ArrayList<Integer>();
        for (int o = 9; o < 45; o++) {
            if (getPlayer().getInventoryContainer().getSlotAt(o).getItem().isPresent()) {
                net.halalaboos.mcwrapper.api.item.ItemStack item = getPlayer().getInventoryContainer().getSlotAt(o).getItem().get();
                if (item != null)
                    if (item.getItemType() instanceof Armor)
                    	list.add(o);
            }
        }
        return list;
    }

    /**
     * @return The slot which is associated with the given armor type.
     * */
	private Slot getWearingArmor(int armorType) {
		return getPlayer().getInventoryContainer().getSlotAt(8 - armorType);
	}

	/**
     * Nameable which holds an enchantment.
     * */
	public static class EnchantmentItem implements Nameable {
		
		private final Enchantment enchantment;
		
		public EnchantmentItem(Enchantment enchantment) {
			this.enchantment = enchantment;
		}

		@Override
		public String getName() {
			return enchantment.name(1);
		}
		
		@Override
		public String getDescription() {
			return getName() + " enchantment.";
		}
		
		public Enchantment getEnchantment() {
			return enchantment;
		}
		
	}
}
