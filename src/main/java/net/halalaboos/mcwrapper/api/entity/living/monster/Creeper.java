package net.halalaboos.mcwrapper.api.entity.living.monster;

import net.halalaboos.mcwrapper.api.property.Explosive;

public interface Creeper extends Monster, Explosive {

	boolean isPowered();

	State getState();

	enum State {
		FUSED, IDLE
	}
}
