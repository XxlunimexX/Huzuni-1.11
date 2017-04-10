package net.halalaboos.huzuni.mod.mining.templates;

import net.halalaboos.huzuni.api.node.attribute.Nameable;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import java.util.List;

/**
 * Used to automate the placement of blocks. Stores a template of block placements.
 * */
public interface Template extends Nameable {

    /**
     * @return The maximum amount of points this template needs to be able to generate it's shape/structure.
     * */
	int getMaxPoints();

    /**
     * @return The name of this point.
     * */
	String getPointName(int point);

    /**
     * @return True if this position does not need to be offset by the face value (i.e. the block placement packet will use the actual block position rather than what is expected from the client.)
     * */
	boolean insideBlock(Vector3i position);
	
//	boolean isValidPlacement(EnumFacing face, BlockPos position, int point);

    /**
     * Generates and appends block positions to the given output.
     * */
	void generate(List<Vector3i> outputPositions, Face face, Vector3i... positions);
	
}
