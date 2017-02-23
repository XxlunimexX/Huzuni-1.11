package net.halalaboos.mcwrapper.api.entity;

import net.halalaboos.mcwrapper.api.entity.living.player.Player;

public interface FishHook extends Entity {

	Player getOwner();


	//TODO

	enum FishState {
		/**
		 * If the fish hook is in the air/flying
		 */
		AIR,

		/**
		 * If the fish hook is hooked in an Entity
		 */
		HOOKED,

		/**
		 * If the fish hook is bobbing
		 */
		BOBBING
	}
}
