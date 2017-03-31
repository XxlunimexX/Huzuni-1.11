package net.halalaboos.huzuni.mod.mining.templates;

import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import java.util.List;

public class WallsTemplate implements Template {

	@Override
	public String getName() {
		return "Walls";
	}

	@Override
	public String getDescription() {
		return "Build walls";
	}

	@Override
	public int getMaxPoints() {
		return 2;
	}

	@Override
	public String getPointName(int point) {
		return "Point " + (point + 1);
	}
	
	@Override
	public boolean insideBlock(Vector3i position) {
		return false;
	}

	@Override
	public void generate(List<Vector3i> outputPositions, Face face, Vector3i... positions) {
		if (positions[0].equals(positions[1]))
			return;
		int incrementX = positions[0].getX() <= positions[1].getX() ? 1 : -1;
		int incrementY = positions[0].getY() <= positions[1].getY() ? 1 : -1;
		int incrementZ = positions[0].getZ() <= positions[1].getZ() ? 1 : -1;
		for (int i = positions[0].getX(); check(i, positions[1].getX(), positions[0].getX() > positions[1].getX()); i += incrementX) {
			for (int j = positions[0].getY(); check(j, positions[1].getY(), positions[0].getY() > positions[1].getY()); j += incrementY) {
				for (int k = positions[0].getZ(); check(k, positions[1].getZ(), positions[0].getZ() > positions[1].getZ()); k += incrementZ) {
					if (i == positions[0].getX() || i == positions[1].getX() || k == positions[0].getZ() || k == positions[1].getZ())
						outputPositions.add(new Vector3i(i, j, k));
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Walls";
	}

    /**
     * Compares the min vs the max and flips the comparison when necessary.
     * */
	private boolean check(int min, int max, boolean flip) {
		return flip ? min >= max : min <= max;
	}
}
