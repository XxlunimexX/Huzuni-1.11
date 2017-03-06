package net.halalaboos.mcwrapper.api.potion;

import net.halalaboos.mcwrapper.api.attribute.Nameable;

public interface Potion extends Nameable {

	Type getType();

	boolean hasIcon();

	int id();

	enum Type {
		BAD, BENEFICIAL, NONE
	}
}
