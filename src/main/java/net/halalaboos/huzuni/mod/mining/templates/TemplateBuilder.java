package net.halalaboos.huzuni.mod.mining.templates;

import net.halalaboos.huzuni.Huzuni;
import net.halalaboos.huzuni.gui.Notification.NotificationType;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;

/**
 * Processes and handles template building.
 * */
public class TemplateBuilder {
	
	private final Huzuni huzuni = Huzuni.INSTANCE;
	
	private final List<Vector3i> positions = new ArrayList<>(), previewPositions = new ArrayList<>();
	
	private Template template;
	
	private Vector3i[] selectedPositions = null;
	
	private int positionIndex = 0;

    /**
     * Attempts to add the block position with the face as a selection for the template which is being built.
     * @return Tre if the template positions have been fully generated.
     * */
	public boolean addSelection(Vector3i position, Face face) {
		if (selectedPositions != null) {
			selectedPositions[positionIndex] = template.insideBlock(position) ? position : position.offset(face);
			huzuni.addNotification(NotificationType.INFO, "Template Builder", 5000, template.getPointName(positionIndex) + " created!");
			positionIndex++;
		} else {
			positionIndex = 0;
			selectedPositions = new Vector3i[template.getMaxPoints()];
			
			selectedPositions[positionIndex] = template.insideBlock(position) ? position : position.offset(face);
			huzuni.addNotification(NotificationType.INFO, "Template Builder", 5000, template.getPointName(positionIndex) + " created!");
			positionIndex++;
		}
		
		if (selectedPositions != null && positionIndex >= template.getMaxPoints()) {
            positions.clear();
			template.generate(positions, getPlayer().getFace(), selectedPositions);
			selectedPositions = null;
			positionIndex = 0;
			huzuni.addNotification(NotificationType.INFO, "Template Builder", 5000, "Building template!");
			return true;
		}
		return false;
	}

	/**
     * Attempts to generate a list of block positions which can be used for previewing the template. Uses the block position and face for the next position within the template.
     * @return The full list of blocks this template may create, if the player uses the block position and face value.
     * */
	public List<Vector3i> getPreview(Vector3i position, Face face) {
		Vector3i[] tempPositions;
		if (selectedPositions != null) {
			tempPositions = Arrays.copyOf(selectedPositions, selectedPositions.length);
			tempPositions[positionIndex] = template.insideBlock(position) ? position : position.offset(face);
		} else 
			tempPositions = new Vector3i[] { template.insideBlock(position) ? position : position.offset(face) };
		previewPositions.clear();
		template.generate(previewPositions, getPlayer().getFace(), tempPositions);
		return previewPositions;
	}

	/**
     * @return True if the template is one position away from being viewable.
     * */
	public boolean canPreview() {
		return positionIndex + 1 >= template.getMaxPoints();
	}
	
	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
		resetPositions();
	}

	public boolean hasPositions() {
		return !positions.isEmpty();
	}

	public List<Vector3i> getPositions() {
		return positions;
	}

	/**
     * Resets the position information.
     * */
	public void resetPositions() {
		positions.clear();
        previewPositions.clear();
		selectedPositions = null;
		positionIndex = 0;
	}
	
}
