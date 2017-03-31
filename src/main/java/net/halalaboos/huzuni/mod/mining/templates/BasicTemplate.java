package net.halalaboos.huzuni.mod.mining.templates;

import com.google.gson.*;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * Basic template which can be used to build static shapes from an origin.
 * */
public class BasicTemplate implements Template {

	private final String name;

	private final int[] vertices;

	public BasicTemplate(String name, int[] vertices) {
		this.name = name;
		this.vertices = vertices;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return "Build a " + name;
	}

	@Override
	public int getMaxPoints() {
		return 1;
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
		int xOffset = face.rotateY().getDirectionVector().getX();
		int zOffset = face.rotateY().getDirectionVector().getZ();
		Vector3i origin = positions[0];
		for (int i = 0; i < vertices.length; i += 2) {
			outputPositions.add(origin.add(xOffset * vertices[i], vertices[i + 1], zOffset * vertices[i]));
		}
	}

	@Override
	public String toString() {
		return name;
	}

	/**
     * Loads a basic template from file.
     * */
	public static BasicTemplate readTemplate(File file) throws Exception {
		Gson gson = new GsonBuilder().create();
		JsonObject object = gson.fromJson(new FileReader(file), JsonObject.class);
		String name = object.get("name").getAsString();
		JsonArray jsonArray = object.get("vertices").getAsJsonArray();
		int[] vertices = new int[jsonArray.size()];
		int index = 0;
		for (JsonElement element : jsonArray) {
			vertices[index] = element.getAsInt();
			index++;
		}
		return new BasicTemplate(name, vertices);
	}
}