package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.command.impl.BasicCommand;

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
//		huzuni.addChatMessage(Boolean.toString(huzuni.resourceCreator.create(args[0], args[1])));

//		huzuni.addChatMessage("TEST: " + ItemTypes.from(BlockTypes.DIRT).name());
	}
}
