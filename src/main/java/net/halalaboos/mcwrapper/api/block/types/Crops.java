package net.halalaboos.mcwrapper.api.block.types;

import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

public interface Crops extends Block {

	int getAge(Vector3i pos);

	boolean isGrown(Vector3i pos);

	int getMaxAge();

}
