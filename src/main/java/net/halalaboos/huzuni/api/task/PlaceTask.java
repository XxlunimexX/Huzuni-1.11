package net.halalaboos.huzuni.api.task;

import net.halalaboos.huzuni.api.node.Nameable;
import net.halalaboos.huzuni.api.util.Timer;
import net.halalaboos.huzuni.mod.movement.Freecam;
import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.util.enums.ActionResult;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.math.Vector3d;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.minecraft.item.ItemBlock;

import static net.halalaboos.mcwrapper.api.MCWrapper.*;

/**
 * Look task which simulates block placement server-sided.
 * */
public class PlaceTask extends LookTask {

	protected final Timer timer = new Timer();

	protected Face face;

	protected Vector3i position;

	protected int placeDelay = 100;

	protected boolean naturalPlacement = true;

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
			getMinecraft().getNetworkHandler().sendSwing(Hand.MAIN);
			if (naturalPlacement) {
				ActionResult result = getController().rightClickBlock(position, face,
						new Vector3d((float) face.getDirectionVector().getX() / 2F,
								(float) face.getDirectionVector().getY() / 2F,
								(float) face.getDirectionVector().getZ() / 2F), Hand.MAIN);

				if (result != ActionResult.FAIL) {
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
		return item.getItemType() instanceof ItemBlock;
	}

	/**
	 * @return True if the player should face the position.
	 * */
	protected boolean shouldRotate() {
		return getPlayer().getHeldItem(Hand.MAIN) != null && hasRequiredItem(getPlayer().getHeldItem(Hand.MAIN)) && isWithinDistance();
	}

	protected void reset() {
		setBlock(null, null);
		timer.reset();
	}

	protected Block getBlock() {
		return getWorld().getBlock(position);
	}

	public int getPlaceDelay() {
		return placeDelay;
	}

	public void setPlaceDelay(int placeDelay) {
		this.placeDelay = placeDelay;
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
			System.out.println("SETTING! " + position.toString());
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
