package net.halalaboos.huzuni.api.settings.organize;

import net.halalaboos.huzuni.api.mod.Mod;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Organizer which sorts the list of mods.
 * */
public abstract class Organizer implements Comparator<Mod> {
	
	private final String name;
	
	public Organizer(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void organize(List<Mod> mods) {
		Collections.sort(mods, this);
	}
	
	@Override
	public String toString() {
		return name;
	}
}
