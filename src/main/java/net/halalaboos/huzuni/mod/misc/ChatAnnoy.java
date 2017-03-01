package net.halalaboos.huzuni.mod.misc;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.halalaboos.huzuni.api.event.EventManager.EventMethod;
import net.halalaboos.huzuni.api.event.PacketEvent;
import net.halalaboos.huzuni.api.event.UpdateEvent;
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
import net.halalaboos.mcwrapper.api.network.NetworkHandler;
import net.halalaboos.mcwrapper.api.network.PlayerInfo;
import net.halalaboos.mcwrapper.api.network.packet.server.ItemPickupPacket;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static net.halalaboos.mcwrapper.api.MCWrapper.*;

/**
 * Randomly sends messages in chat under certain circumstances to annoy other players.
 *
 * TODO: Remove minecraft code
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

	//The last sent time
	private String lastTimeSent;

	//The messages to send - Category (e.g. dig, time, pickup), Messages
	private final Map<String, List<String>> messageMap = new HashMap<>();

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
		lastTimeSent = getCurrentTime();
		huzuni.eventManager.addListener(this);
		if (messageMap.isEmpty()) {
			try {
				//todo change this lol
				loadMessages(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("huzuni/chatannoy.json")).getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
				if (dig.isEnabled() && event.getPacket() instanceof CPacketPlayerDigging) {
					CPacketPlayerDigging packet = (CPacketPlayerDigging) event.getPacket();
					alertDigging(packet);
				}
			}
		} else {
			if (pickup.isEnabled() && timer.hasReach(250) && random.nextBoolean()) {
				if (event.getPacket() instanceof ItemPickupPacket) {
					ItemPickupPacket packet = (ItemPickupPacket) event.getPacket();
					alertPickup(packet);
				}
			}
		}
	}

	@EventMethod
	public void onTick(UpdateEvent event) {
		if (event.type == UpdateEvent.Type.PRE) {
			if (timer.hasReach(1500) && time.isEnabled() && !getCurrentTime().equals(lastTimeSent)) {
				getPlayer().sendMessage(getMessage("time", null, null));
				lastTimeSent = getCurrentTime();
				timer.reset();
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
				getPlayer().sendMessage(getMessage("dig", block, null));
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
	private void alertPickup(ItemPickupPacket collectItem) {
		int collector = collectItem.getCollector();
		int collected = collectItem.getCollected();

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
				if (!itemStack.name().equals(lastItemName)) {
					//Send the alert in chat
					getPlayer().sendMessage(getMessage("pickup", null, itemStack));
					//Set the last item to this one
					lastItemName = itemStack.name();
				}
			}
		}
		//Reset the timer
		timer.reset();
	}

	private void loadMessages(InputStream file) {
		try {
			Gson gson = new Gson();
			BufferedReader reader = new BufferedReader(new InputStreamReader(file));
			Type type = new TypeToken<List<ChatMessage>>(){}.getType();
			List<ChatMessage> chatMessage = gson.fromJson(reader, type);
			for (ChatMessage msg : chatMessage) {
				messageMap.put(msg.category, msg.messages);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getCurrentTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
	}

	private String getMessage(String category, @Nullable Block block, @Nullable ItemStack itemStack) {
		int size = messageMap.get(category).size();
		String message = messageMap.get(category).get(random.nextInt(size));
		message = StringUtils.replace(message, "$PLAYER", getRandomPlayer());
		message = StringUtils.replace(message, "$TIME", getCurrentTime());
		if (block != null) {
			message = StringUtils.replace(message, "$BLOCK", block.name());
		}
		if (itemStack != null) {
			message = StringUtils.replace(message, "$ITEM", itemStack.name());
		}
		return message;
	}

	private String getRandomPlayer() {
		if (!getMinecraft().getNetworkHandler().isPresent() || !getMinecraft().isRemote()) return "you";
		NetworkHandler netHandler = getMinecraft().getNetworkHandler().get();
		List<PlayerInfo> list = new ArrayList<>(netHandler.getPlayers());
		String out = list.get(random.nextInt(list.size() - 1)).getName(false);
		if (out.equals(getPlayer().name())) return getRandomPlayer();
		return out;
	}

	private class ChatMessage {
		String category;
		List<String> messages;
	}
}