package net.halalaboos.huzuni.mod.commands;

import net.halalaboos.huzuni.api.mod.BasicCommand;
import net.halalaboos.huzuni.api.mod.Mod;
import net.halalaboos.mcwrapper.api.util.TextColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Authors extends BasicCommand {

	public Authors() {
		super(new String[] { "authors" }, "Lists the authors' contributions to the mod.");
	}

	@Override
	protected void runCommand(String input, String[] args) throws Exception {
		List<Mod> mods = huzuni.modManager.getMods();
		Map<String, String> authorMap = new HashMap<>();
		for (Mod mod : mods) {
			if (authorMap.containsKey(mod.getAuthor())) {
				authorMap.put(mod.getAuthor(), authorMap.get(mod.getAuthor()) + mod.getName() + ", ");
			} else {
				authorMap.put(mod.getAuthor(), mod.getAuthor() + ": " + mod.getName() + ", ");
			}
		}
		huzuni.addChatMessage(TextColor.BOLD + "TOTAL LIST OF AUTHORS: ");
		Set<String> authors = authorMap.keySet();
		for (String author : authors) {
			String modsList = authorMap.get(author);
			int count = modsList.split(", ").length;
			modsList = modsList.substring(0, modsList.length() - 2);
			huzuni.addChatMessage(modsList + " (" + TextColor.GOLD + count + TextColor.RESET + ")");
		}

	}
}
