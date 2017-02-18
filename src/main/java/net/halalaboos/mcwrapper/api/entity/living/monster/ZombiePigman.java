package net.halalaboos.mcwrapper.api.entity.living.monster;

import net.halalaboos.mcwrapper.api.entity.Entity;

public interface ZombiePigman extends Zombie {

	boolean isAngry();

	boolean isAngryAt(Entity entity);
}
