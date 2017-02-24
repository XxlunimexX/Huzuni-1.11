package net.halalaboos.mcwrapper.api.block.tileentity;

public interface Chest extends TileEntity {

	Chest getAdjacentZNeg();
	Chest getAdjacentXPos();
	Chest getAdjacentXNeg();
	Chest getAdjacentZPos();

	ChestType getType();

	enum ChestType {
		NORMAL, TRAP
	}
}
