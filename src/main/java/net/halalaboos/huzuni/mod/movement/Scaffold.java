package net.halalaboos.huzuni.mod.movement;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Mode;
import net.halalaboos.huzuni.api.node.Value;
import net.halalaboos.huzuni.api.task.PlaceTask;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.math.MathUtils;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import static net.halalaboos.mcwrapper.api.MCWrapper.getInput;
import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;
import static net.halalaboos.mcwrapper.api.MCWrapper.getWorld;

/**
 * Places blocks underneath the player to allow them to walk across large areas which have no blocks.
 *
 * TODO: Port to MCWrapper
 * */
public class Scaffold extends BasicMod {

	public final Value placeDistance = new Value("Distance ", " blocks", 1F, 3F, 4F, 1F, "Max distance you place blocks");

	public final Mode<String> mode = new Mode<String>("Mode", "Movement mode", "Horizontal", "Vertical");

	private final PlaceTask placeTask = new PlaceTask(this);

	private int count = 0;

	public Scaffold() {
		super("Scaffold", "Automatically places blocks when you move forward or jump.");
		setAuthor("Halalaboos");
		addChildren(placeDistance, mode);
		setCategory(Category.MOVEMENT);
		huzuni.lookManager.registerTaskHolder(this);
		placeTask.setPlaceDelay(0);
		placeTask.setNaturalPlacement(false);
		subscribe(PreMotionUpdateEvent.class, this::onPreUpdate);
	}

	@Override
	public void onDisable() {
		huzuni.lookManager.withdrawTask(placeTask);
	}

	@Override
	public String getDisplayNameForRender() {
		return settings.getDisplayName() + String.format(" (%s)", mode.getSelectedItem());
	}

	private void onPreUpdate(PreMotionUpdateEvent event) {
		if (huzuni.lookManager.hasPriority(this) && count <= 0) {
			switch (mode.getSelected()) {
				case 0:
					if (!getInput().isSneak() && (getInput().getForward() != 0 || getInput().getStrafe() != 0)) {
						Face face = getPlayer().getFace();
						Vector3i position = getFirstBlock(face.getDirectionVector());
						if (position != null) {
							placeTask.setBlock(position, face);
							huzuni.lookManager.requestTask(this, placeTask);
						} else
							huzuni.lookManager.withdrawTask(placeTask);
					} else
						huzuni.lookManager.withdrawTask(placeTask);
					break;
				case 1:
					if (!getPlayer().isOnGround() && getInput().isJump() && getPlayer().getVelocity().getY() > 0) {
						Vector3i position = new Vector3i(getPlayer().getX(), MathUtils.floor(getPlayer().getY() - 2), getPlayer().getZ());
						Vector3i above = new Vector3i(getPlayer().getX(), MathUtils.floor(getPlayer().getY() - 1), getPlayer().getZ());
						if (getWorld().blockExists(position) && !getWorld().blockExists(above)) {
							placeTask.setBlock(position, Face.UP);
							huzuni.lookManager.requestTask(this, placeTask);
						} else
							huzuni.lookManager.withdrawTask(placeTask);
					} else
						huzuni.lookManager.withdrawTask(placeTask);
					break;
				default:
					break;
			}
		} else {
			if (count > 0)
				count--;
		}
	}

	private Vector3i getFirstBlock(Vector3i dir) {
		for (int i = 0; i <= ((int) placeDistance.getValue()); i++) {
			Vector3i pos = new Vector3i(getPlayer().getX() + dir.getX() * i, getPlayer().getY() - 1, getPlayer().getZ() + dir.getZ() * i);
			Vector3i before = new Vector3i(getPlayer().getX() + dir.getX() * (i - 1), getPlayer().getY() - 1, getPlayer().getZ() + dir.getZ() * (i - 1));
			if (getWorld().blockExists(pos) && !getWorld().blockExists(before)) {
				return before;
			}
		}
		return null;
	}
}
