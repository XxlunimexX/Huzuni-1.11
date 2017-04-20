package net.halalaboos.huzuni.api.task;

import net.halalaboos.huzuni.api.node.attribute.Nameable;
import net.halalaboos.huzuni.api.util.Timer;
import net.halalaboos.huzuni.mod.movement.Freecam;
import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.item.types.BlockItem;
import net.halalaboos.mcwrapper.api.util.enums.ActionResult;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import static net.halalaboos.mcwrapper.api.MCWrapper.*;

/**
 * Look task which simulates block placement server-sided.
 * */
public class PlaceTask extends LookTask {

	protected final Timer timer = new Timer();

	protected Face face;

	protected Vector3i position;

	private int placeDelay = 100;

	private boolean naturalPlacement = true;

	public PlaceTask(Nameable handler) {
		super(handler);
		addDependency("off_interact");
	}

	public PlaceTask(Nameable handler, Vector3i position, Face face) {
		super(handler, position.getX(), position.getY(), position.getZ());
		addDependency("off_interact");
		this.position = position;
		this.face = face;
	}

	@Override
	public void onPreUpdate() {
		if (timer.hasReach(placeDelay) && hasBlock() && shouldRotate() && !Freecam.INSTANCE.isEnabled()) {
			this.setRotations(position, face);
			if (shouldResetBlock()) {
				reset();
				return;
			}
			super.onPreUpdate();
		}
	}

	@Override
	public void onPostUpdate() {
		if (timer.hasReach(placeDelay) && hasBlock() && shouldRotate() && !Freecam.INSTANCE.isEnabled()) {
			super.onPostUpdate();
			if (isWithinDistance()) {
				getMinecraft().getNetworkHandler().sendSwing(Hand.MAIN);
				if (naturalPlacement) {
					if (getController().rightClickBlock(position, face, new Vector3d((float) face.getDirectionVector().getX() / 2F, (float) face.getDirectionVector().getY() / 2F, (float) face.getDirectionVector().getZ() / 2F), Hand.MAIN) != ActionResult.FAIL) {
						if (shouldResetBlock()) {
							reset();
						}
					}
				} else {
					getMinecraft().getNetworkHandler().sendTryUseItemOnBlock(position, face, Hand.MAIN, (float) face.getDirectionVector().getX() / 2F, (float) face.getDirectionVector().getY() / 2F, (float) face.getDirectionVector().getZ() / 2F);
				}
				timer.reset();
			}
		}
	}

	@Override
	public void onTaskCancelled() {
		reset();
	}

	@Override
	public void setRunning(boolean running) {
		super.setRunning(running);
		if (!running)
			reset();
	}

	/**
	 * @return True if the item held is required to continue block placement.
	 * */
	protected boolean hasRequiredItem(ItemStack item) {
		return item.getItemType() instanceof BlockItem;
	}

	/**
	 * @return True if the player should face the position.
	 * */
	protected boolean shouldRotate() {
		return getPlayer().getHeldItem(Hand.MAIN).isPresent() && hasRequiredItem(getPlayer().getHeldItem(Hand.MAIN).get()) && isWithinDistance();
	}

	protected void reset() {
		setBlock(null, null);
		timer.reset();
	}

	protected Block getBlock() {
		return getWorld().getBlock(position.offset(face));
	}

	public int getPlaceDelay() {
		return placeDelay;
	}

	public void setPlaceDelay(int placeDelay) {
		this.placeDelay = placeDelay;
	}

	public Vector3i getPosition() {
		return position;
	}

	/**
	 * @return True if the position is within the player's reach distance.
	 * */
	public boolean isWithinDistance() {
		return getPlayer().getDistanceTo(position.toDouble()) < getController().getBlockReach();
	}

	/**
	 * @return True if the block at our placement location is not air.
	 * */
	public boolean shouldResetBlock() {
		return getWorld().blockExists(position.offset(face));
	}

	public void cancelPlacing() {
		if (hasBlock()) {
			reset();
		}
	}

	public void setBlock(Vector3i position, Face face) {
		this.position = position;
		this.face = face;
		if (position != null && face != null) {
			this.setRotations(position, face);
		}
	}

	public boolean hasBlock() {
		return position != null && face != null;
	}

	public boolean isNaturalPlacement() {
		return naturalPlacement;
	}

	public void setNaturalPlacement(boolean naturalPlacement) {
		this.naturalPlacement = naturalPlacement;
	}

}
