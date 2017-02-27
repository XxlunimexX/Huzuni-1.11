package net.halalaboos.huzuni.mod.misc;

import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.PacketEvent;
import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.settings.Toggleable;
import net.halalaboos.huzuni.api.util.Timer;
import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.block.BlockTypes;
import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.ExperienceOrb;
import net.halalaboos.mcwrapper.api.entity.ItemPickup;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketCollectItem;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

import static net.halalaboos.mcwrapper.api.MCWrapper.getController;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;
import static net.halalaboos.mcwrapper.api.MCWrapper.getWorld;

/**
 * Randomly sends messages in chat under certain circumstances to annoy other players.
 *
 * TODO: Randomized messages from a json file (before release)
 */
public class ChatAnnoy extends BasicMod {

	//Toggles
	private final Toggleable pickup = new Toggleable("Pickups", "Randomly post in chat when you pick up an item.");
	private final Toggleable dig = new Toggleable("Mining", "Randomly post in chat when you break a block.");

	//TODO
	private final Toggleable time = new Toggleable("Time", "Randomly post the time in chat.");

	//Timer used to delay the sent messages
	private final net.halalaboos.huzuni.api.util.Timer timer = new Timer();

	//Random to also delay the messages and make them not send every time something happens
	private final Random random = new Random();

	//The last sent block name
	private String lastBlockName;

	//The last sent item name
	private String lastItemName;

	public ChatAnnoy() {
		super("Chat annoy", "Annoy others in chat!");
		setAuthor("brudin");
		setCategory(Category.MISC);
		addChildren(pickup, dig, time);
		dig.setEnabled(true);
		pickup.setEnabled(true);
	}

	@Override
	protected void onEnable() {
		huzuni.eventManager.addListener(this);
	}

	@Override
	protected void onDisable() {
		huzuni.eventManager.removeListener(this);
	}

	/**
	 * Performed when packets are read/sent, used to call the actual annoy modes.
	 */
	@EventMethod
	public void onPacket(PacketEvent event) {
		if (event.type == PacketEvent.Type.SENT) {
			if (timer.hasReach(1500) && random.nextBoolean()) {
				if (event.getPacket() instanceof CPacketPlayerDigging) {
					CPacketPlayerDigging packet = (CPacketPlayerDigging) event.getPacket();
					alertDigging(packet);
				}
			}
		} else {
			if (timer.hasReach(1500) && random.nextBoolean()) {
				if (event.getPacket() instanceof SPacketCollectItem) {
					SPacketCollectItem packet = (SPacketCollectItem) event.getPacket();
					alertPickup(packet);
				}
			}
		}
	}

	/**
	 * Randomly sends a chat message when the Player mines a Block.
	 * @param digging The digging packet
	 */
	private void alertDigging(CPacketPlayerDigging digging) {
		//Check if the player has (pretty much) broken a block and also an additional random check
		if (getController().getBlockDamage() >= 0.9F && random.nextBoolean()) {
			BlockPos pos = digging.getPosition();
			//The block the Player broke
			Block block = getWorld().getBlock(new Vector3i(pos.getX(), pos.getY(), pos.getZ()));
			//The name of the Block
			String name = block.name();
			//Check if the Block isn't air and the last block we sent to chat isn't the same as this one
			if (block != BlockTypes.AIR && !name.equals(lastBlockName)) {
				//Sends the message
				getPlayer().sendMessage(String.format(getDigMessage(), name.toLowerCase()));
				//Set the last block name to the current block name
				lastBlockName = block.name();
			}
			//Reset the timer.
			timer.reset();
		}
	}

	/**
	 * Randomly sends a chat message when the Player picks up an Item.
	 * @param collectItem The pickup packet
	 */
	private void alertPickup(SPacketCollectItem collectItem) {
		int collector = collectItem.getEntityID();
		int collected = collectItem.getCollectedItemEntityID();

		//Check if it was you who picked up the item
		if (getPlayer().getId() == collector) {
			//The picked up object
			Entity entity = getWorld().getEntity(collected);
			//Check if the item is an item and not an experience orb or something
			if (entity instanceof ItemPickup && !(entity instanceof ExperienceOrb)) {
				//The item entity
				ItemPickup pickup = ((ItemPickup) entity);
				//The actual item stack that will be obtained
				ItemStack itemStack = pickup.getItem();

				//Check if the name isn't the last sent item name
				if (!itemStack.getName().equals(lastItemName)) {
					//The item's name
					String name = itemStack.getName();
					//Send the alert in chat
					getPlayer().sendMessage(String.format(getPickupMessage(), name.toUpperCase()));
					//Set the last item to this one
					lastItemName = itemStack.getName();
				}
			}
		}
		//Reset the timer
		timer.reset();
	}

	/**
	 * The 'pickup' message.
	 */
	private String getPickupMessage() {
		return "i hav picked up %s!";
	}

	/**
	 * The 'dig' message.
	 */
	private String getDigMessage() {
		return "i did it i mined %s :OOO";
	}
}