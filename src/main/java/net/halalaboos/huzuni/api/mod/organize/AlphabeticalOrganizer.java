package net.halalaboos.huzuni.api.mod.organize;

import net.halalaboos.huzuni.api.mod.Mod;

/**
 * Organizes mods into alphabetical order.
 * */
public class AlphabeticalOrganizer extends Organizer {

	public AlphabeticalOrganizer(boolean useDisplay) {
		super("Alphabetical", useDisplay);

	}

	@Override
	public int compare(Mod o1, Mod o2) {
		return isUseDisplay() ? o1.getDisplayNameForRender().compareTo(o2.getDisplayNameForRender()) : o1.getName().compareTo(o2.getName());
	}
}
