package net.halalaboos.huzuni.api.mod.organize;

import net.halalaboos.huzuni.api.mod.Mod;

/**
 * Organizes mods into order of category.
 * */
public class CategoryOrganizer extends Organizer {

	public CategoryOrganizer() {
		super("Category", false);

	}

	@Override
	public int compare(Mod o1, Mod o2) {
		return o1.getCategory().ordinal() - o2.getCategory().ordinal();
	}

}
