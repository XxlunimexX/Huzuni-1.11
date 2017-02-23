package net.halalaboos.mcwrapper.api.entity;

public interface FishHook extends Entity {

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
