package net.halalaboos.huzuni.api.mod.organize;

import net.halalaboos.huzuni.api.mod.Mod;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Organizer which sorts the list of mods.
 * */
public abstract class Organizer implements Comparator<Mod> {
	
	private final String name;

	private boolean useDisplay = false;

	public Organizer(String name, boolean useDisplay) {
		this.name = name;
		this.useDisplay = useDisplay;
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

	public boolean isUseDisplay() {
		return useDisplay;
	}

	public void setUseDisplay(boolean useDisplay) {
		this.useDisplay = useDisplay;
	}
}
