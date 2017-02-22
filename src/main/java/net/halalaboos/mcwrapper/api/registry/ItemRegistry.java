package net.halalaboos.mcwrapper.api.registry;

import net.halalaboos.mcwrapper.api.item.Item;

import java.util.Collection;

public interface ItemRegistry {

	/**
	 * Returns an {@link Item} with the same name as the one provided.
	 * <p>Proper format of the name is 'item_name'.</p>
	 */
	Item getItem(String name);

	/**
	 * Returns an {@link Item} with the same ID as the one provided.
	 * <p>It is recommended to avoid using this, since Minecraft is slowly moving further and further away
	 * from using IDs.</p>
	 */
	Item getItem(int id);

	/**
	 * Returns all of the loaded/registered items.
	 */
	Collection<Item> getRegisteredItems();
}
