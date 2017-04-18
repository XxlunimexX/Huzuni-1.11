package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.api.account.Account;
import net.halalaboos.huzuni.api.mod.command.impl.BasicCommand;
import net.halalaboos.huzuni.api.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Debug extends BasicCommand {

	private final File accountsFile = new File(Huzuni.INSTANCE.getSaveFolder(), "Accounts.txt");

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
		List<String> accounts = readAccounts();
		for (String s : accounts) {
			Account account = Huzuni.INSTANCE.accountManager.getAccount(s);
			Huzuni.INSTANCE.accountManager.addAccount(account);
		}
	}

	private List<String> readAccounts() {
		if (accountsFile.exists()) {
			try {
				return FileUtils.readFile(accountsFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new ArrayList<>();
	}
}
