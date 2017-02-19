package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.BasicCommand;
import net.halalaboos.mcwrapper.api.MCWrapper;
import net.halalaboos.mcwrapper.api.MinecraftAdapter;
import net.halalaboos.mcwrapper.api.MinecraftClient;
import net.halalaboos.mcwrapper.api.entity.living.player.ClientPlayer;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.item.ItemStack;

public class Debug extends BasicCommand {

	public Debug() {
		super("debug", "Basic debug command for some wrapper things, ignore me!");
	}

	@Override
	protected void runCommand(String input, String[] args) throws Exception {
		MinecraftAdapter adapter = MCWrapper.getAdapter();
		MinecraftClient minecraft = adapter.getMinecraft();
		ClientPlayer player = minecraft.getPlayer();
		ItemStack itemStack = player.getHeldItem(Hand.MAIN);
		String name = itemStack.getName();
		int id = itemStack.getItemType().getId();
		int size = itemStack.getSize();
		float health = player.getHealthData().getCurrentHealth() / 2;
		huzuni.addChatMessage(String.format("Hello, %s!", player.getEntityName()));
		huzuni.addChatMessage(String.format("You have %s hearts!", health));
		huzuni.addChatMessage(String.format("You've got %s corndogs!", player.getFood()));
		huzuni.addChatMessage(String.format("You've got %s corndog saturation!", player.getSaturation()));
		huzuni.addChatMessage(String.format("Held item: %s (x%s), id %s", name, size, id));
	}
}
