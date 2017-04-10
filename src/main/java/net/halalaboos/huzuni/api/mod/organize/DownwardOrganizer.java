package net.halalaboos.huzuni.api.mod.organize;

import net.halalaboos.huzuni.api.mod.Mod;

/**
 * Organizes mods into order of smallest length name to the largest length name.
 * */
public class DownwardOrganizer extends Organizer {

	public DownwardOrganizer(boolean useDisplay) {
		super("Downward", useDisplay);

	}

	@Override
	public int compare(Mod o1, Mod o2) {
		return isUseDisplay() ? o1.getDisplayNameForRender().length() - o2.getDisplayNameForRender().length() : o1.getName().length() - o2.getName().length();
	}

}
