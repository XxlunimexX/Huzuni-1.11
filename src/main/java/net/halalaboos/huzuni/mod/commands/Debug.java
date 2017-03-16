package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.BasicCommand;
import net.halalaboos.huzuni.indev.gui.impl.ImageCreator;
import net.halalaboos.mcwrapper.api.client.ClientPlayer;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.item.ItemTypes;
import net.halalaboos.mcwrapper.api.potion.Potion;
import net.halalaboos.mcwrapper.api.potion.PotionEffect;
import net.minecraft.util.text.translation.I18n;

import static net.halalaboos.huzuni.indev.gui.impl.Pointers.IMAGE_ARROW;
import static net.halalaboos.mcwrapper.api.MCWrapper.getAdapter;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

public class Debug extends BasicCommand {

	public Debug() {
		super("debug", "Basic debug command for some wrapper things, ignore me!");
	}

	@Override
	protected void runCommand(String input, String[] args) throws Exception {
		/*ClientPlayer player = getPlayer();
		ItemStack itemStack = player.getHeldItem(Hand.MAIN);
		String name = itemStack.name();
		int id = itemStack.getItemType().getId();
		int size = itemStack.getSize();
		float health = player.getHealthData().getCurrentHealth() / 2;
		huzuni.addChatMessage(String.format("Hello, %s!", player.name()));
		huzuni.addChatMessage(String.format("You have %s hearts!", health));
		huzuni.addChatMessage(String.format("You've got %s corndogs!", player.getFood()));
		huzuni.addChatMessage(String.format("You've got %s corndog saturation!", player.getSaturation()));
		huzuni.addChatMessage(String.format("Held item: %s (x%s), id %s", name, size, id));
		try {
			ItemStack stack = ItemStack.from(ItemTypes.ACACIA_BOAT, 1);
			huzuni.addChatMessage(stack.name() + " " + stack.getSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String output = "";
		for (PotionEffect effect : player.getEffects()) {
			output += I18n.translateToLocal(effect.getEffect().name()) + "\n";
		}

		Potion potion = getAdapter().getPotionRegistry().getPotion("instant_health");
		huzuni.addChatMessage(potion.name());
		System.out.println(output);*/

		ImageCreator imageCreator = new ImageCreator();
		huzuni.addChatMessage(Boolean.toString(imageCreator.create(args[0]).isPresent()));

	}
}
