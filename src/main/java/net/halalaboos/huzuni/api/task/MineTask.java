package net.halalaboos.huzuni.api.task;

import net.halalaboos.huzuni.api.node.attribute.Nameable;
import net.halalaboos.huzuni.api.util.Timer;
import net.halalaboos.huzuni.mod.movement.Freecam;
import net.halalaboos.mcwrapper.api.entity.living.player.GameType;
import net.halalaboos.mcwrapper.api.entity.living.player.Hand;
import net.halalaboos.mcwrapper.api.util.enums.DigAction;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import static net.halalaboos.mcwrapper.api.MCWrapper.*;

/**
 * Look task which can take block data and simulate server-sided mining.
 * */
public class MineTask extends LookTask {
	
	protected final Timer timer = new Timer();
	
	protected Face face;
	
	protected Vector3i position;
		
	protected float curBlockDamage = 0F;
	
	protected boolean digging = false;
	
	protected int mineDelay = 100;
		
	public MineTask(Nameable handler) {
		super(handler);
		addDependency("main_interact");
	}
	
	@Override
	public void onPreUpdate() {
		if (timer.hasReach(mineDelay) && hasBlock() && !Freecam.INSTANCE.isEnabled()) {
			this.setRotations(position, face);
			super.onPreUpdate();
		}
	}
	
	@Override
	public void onPostUpdate() {
		if (timer.hasReach(mineDelay) && hasBlock() && !Freecam.INSTANCE.isEnabled()) {
			super.onPostUpdate();
			if (!blockExists() || !isWithinDistance()) {
				if (digging) {
					sendPacket(1);
					onTaskFinishPremature();
				}
				reset();
				return;
			}
			getMinecraft().getNetworkHandler().sendSwing(Hand.MAIN);
			if (curBlockDamage <= 0F) {
				digging = true;
				sendPacket(0);
				if (getController().getGameType() == GameType.CREATIVE || getWorld().getRelativeHardness(position) >= 1F) {
					getWorld().setToAir(position);
					onTaskFinish();
					reset();
					return;
				}
			}
			curBlockDamage += getWorld().getRelativeHardness(position);
			getWorld().sendBreakProgress(position, (int) (curBlockDamage * 10.0F) - 1);
			if (curBlockDamage >= 1F) {
				getWorld().setToAir(position);
				sendPacket(2);
				onTaskFinish();
				reset();
			}
		}
	}
	
	@Override
	public void onTaskCancelled() {
		if (isMining()) {
			sendPacket(1);
			onTaskFinishPremature();
			reset();
		}
	}
	
	@Override
	public void setRunning(boolean running) {
		super.setRunning(running);
		if (!running && isMining()) {
			sendPacket(1);
			onTaskFinishPremature();
			reset();
		}
	}
	
	public int getMineDelay() {
		return mineDelay;
	}

	public void setMineDelay(int mineDelay) {
		this.mineDelay = mineDelay;
	}

	/**
	 * @return True if the block is within player reach distance.
	 * */
	public boolean isWithinDistance() {
		return getPlayer().getDistanceTo(position.toDouble()) < getController().getBlockReach();
	}

	/**
	 * @return True if the block is not air.
	 * */
	public boolean blockExists() {
		return getWorld().blockExists(position);
	}
	
	public boolean isMining() {
		return digging;
	}

	/**
	 * Sends a mining packet based on the mode given.
	 * */
	private void sendPacket(int mode) {
		DigAction action = mode == 0 ? DigAction.START : (mode == 1 ? DigAction.ABORT : (mode == 2 ? DigAction.COMPLETE : null));
		getMinecraft().getNetworkHandler().sendDigging(action, position, face.ordinal());
	}
	
	private void onTaskFinishPremature() {
		timer.reset();
	}

	private void onTaskFinish() {
		timer.reset();
	}

	/**
	 * Cancels the mining simulation if the block is not air.
	 * */
	public void cancelMining() {
		if (hasBlock()) {
			if (isMining()) {
				sendPacket(1);
				onTaskFinishPremature();
			}
			reset();
		}
	}

	/**
	 * Sets the block position and face value.
	 *
	 * TODO - Change param to Vector3i
	 * */
	public void setBlock(Vector3i position, Face face) {
		this.position = position == null ? Vector3i.ZERO : new Vector3i(position.getX(), position.getY(), position.getZ());
		this.face = face;
		if (position != null && face != null)
			this.setRotations(position, face);
	}

	/**
     * @return True if the position and face values are present for this task.
     * */
	public boolean hasBlock() {
		return position != null && face != null;
	}

	/**
     * Resets the mining information.
     * */
	protected void reset() {
		setBlock(null, null);
		curBlockDamage = 0F;
		digging = false;
		timer.reset();
	}
	
	protected boolean shouldRotate() {
		return isWithinDistance();
	}
	
}
