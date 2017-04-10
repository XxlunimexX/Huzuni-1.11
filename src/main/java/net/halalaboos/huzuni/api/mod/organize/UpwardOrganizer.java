package net.halalaboos.huzuni.api.mod.organize;

import net.halalaboos.huzuni.api.mod.Mod;

/**
 * Organizes mods into order of largest length name to the smallest length name.
 * */
public class UpwardOrganizer extends Organizer {

	public UpwardOrganizer(boolean useDisplay) {
		super("Upward", useDisplay);

	}

	@Override
	public int compare(Mod o1, Mod o2) {
		return isUseDisplay() ? o2.getDisplayNameForRender().length() - o1.getDisplayNameForRender().length() : o2.getName().length() - o1.getName().length();
	}

}
