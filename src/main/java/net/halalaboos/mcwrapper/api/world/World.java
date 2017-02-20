package net.halalaboos.mcwrapper.api.world;

import net.halalaboos.mcwrapper.api.entity.Entity;
import net.halalaboos.mcwrapper.api.entity.living.player.Player;
import net.halalaboos.mcwrapper.api.util.Vector3i;

import java.util.Collection;

public interface World {

	void setToAir(Vector3i pos);

	Collection<Player> getPlayers();

	Collection<Entity> getEntities();
}
