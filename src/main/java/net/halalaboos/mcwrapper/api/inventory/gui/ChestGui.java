package net.halalaboos.mcwrapper.api.inventory.gui;

import net.halalaboos.mcwrapper.api.inventory.ChestContainer;
import net.halalaboos.mcwrapper.api.inventory.Inventory;

public interface ChestGui {

	Inventory getLower();

	Inventory getUpper();

	ChestContainer getContainer();
}
