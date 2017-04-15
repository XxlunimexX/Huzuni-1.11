package net.halalaboos.huzuni.mod.mining;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.huzuni.api.task.MineTask;
import net.halalaboos.huzuni.api.util.BlockLocator;
import net.halalaboos.huzuni.api.util.MinecraftUtils;
import net.halalaboos.huzuni.gui.Notification.NotificationType;
import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.client.Controller;
import net.halalaboos.mcwrapper.api.event.input.MouseEvent;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.event.world.WorldLoadEvent;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.enums.MouseButton;
import net.halalaboos.mcwrapper.api.util.math.Result;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.getWorld;

/**
 * Mines, or 'nukes' a specific {@link Block} type in the set radius.
 *
 * <p>This is done by locating nearby {@link Block blocks} using the {@link BlockLocator}, and then
 * mining them with the {@link MineTask}.</p>
 *
 * @author Halalaboos
 */
public final class Nuker extends BasicMod {

	/**
	 * This is used to set the radius for locating and mining Blocks.
	 *
	 * <p>The maximum amount this can be set to is 5 since that is the maximum
	 * {@link Controller#getBlockReach() Player Block interaction distance} in the context of vanilla gameplay.</p>
	 */
	private final Value radius = new Value("Radius", " blocks", 1F, 5F, 5F, 1F, "Radius around the player to mine blocks");

	/**
	 * This is used to set the delay between mining each Block.  This can be useful to compensate for lag when trying
	 * to avoid getting flagged by anti-cheating plugins, or just to mine areas quicker.
	 *
	 * <p>The actual mining speed is not configurable, since that is already calculated automatically using the
	 * {@link MineTask}.</p>
	 */
	private final Value mineDelay = new Value("Mine delay", " ms", 0F, 100F, 500F, 5F, "Delay in MS between mining of each block");

	/**
	 * Sets the {@link BlockLocator}'s {@link BlockLocator#isDistanceCheck()}.
	 */
	private final Toggleable distanceCheck = new Toggleable("Distance check", "Checks if the player can regularly mine the block");

	/**
	 * To avoid getting flagged by anti-cheating plugins, the {@link MineTask} will face the Blocks we are mining to
	 * make it appear that we aren't cheating.
	 *
	 * <p>This option allows the user to specify whether or not they want to face the Blocks client-sided, meaning
	 * they will see themselves facing each Block (which can be very disorienting), or to do it 'silently', or
	 * in other terms, server-sided.  We will still be looking at the Block either way, but enabling this option
	 * will make things a bit less distracting.</p>
	 */
	private final Toggleable silent = new Toggleable("Silent", "Adjust player rotation server-sided only");

	/**
	 * The {@link MineTask} used for mining the Blocks.
	 */
	private final MineTask mineTask = new MineTask(this);

	/**
	 * The {@link BlockLocator} used for locating nearby Blocks that are the same as the set {@link #type}.
	 */
	private final BlockLocator blockLocator = new BlockLocator() {

		@Override
		protected boolean isValidBlock(Vector3i position) {
			return getWorld().blockExists(position) && getWorld().getBlock(position) == type;
		}

		@Override
		protected Face getFace(Vector3i position) {
			return MinecraftUtils.findFace(position);
		}

	};

	/**
	 * This is the Block that the Nuker will target for mining.
	 *
	 * <p>Refer to {@link #onMouseClicked(MouseEvent)} to see how this is set.</p>
	 */
	private Block type;

	public Nuker() {
		super("Nuker", "Annihilates blocks within a radius surrounding the player", Keyboard.KEY_L);
		this.addChildren(radius, mineDelay, distanceCheck, silent);
		setAuthor("Halalaboos");
		this.setCategory(Category.MINING);
		silent.setEnabled(true);
		huzuni.lookManager.registerTaskHolder(this);
		//Stops running the mine task when we first load the world in case it is still running
		subscribe(WorldLoadEvent.class, event -> {
			if (mineTask.hasBlock()) huzuni.lookManager.withdrawTask(mineTask);
		});
		subscribe(MouseEvent.class, this::onMouseClicked);
		subscribe(PreMotionUpdateEvent.class, this::onUpdate);
	}

	@Override
	public void onEnable() {
		huzuni.addNotification(NotificationType.INFO, this, 5000, "Select a block type by right-clicking!");
	}

	@Override
	public void onDisable() {
		huzuni.lookManager.withdrawTask(mineTask);
		blockLocator.reset();
		this.type = null;
	}

	public void onUpdate(PreMotionUpdateEvent event) {
		if (huzuni.lookManager.hasPriority(this)) {
			//If we have silent enabled, then reset the Player's rotation after the task runs.
			mineTask.setReset(silent.isEnabled());
			//Set the delay to our set delay value
			mineTask.setMineDelay((int) mineDelay.getValue());
			//If the MineTask has a Block, then run the task.  Otherwise, cancel the task and find a Block.
			if (mineTask.hasBlock())
				huzuni.lookManager.requestTask(this, mineTask);
			else {
				huzuni.lookManager.withdrawTask(mineTask);
				findBlock();
			}
		}
	}

	/**
	 * Finds the nearby Blocks using the {@link #blockLocator BlockLocator} given the set options, such
	 * as {@link #distanceCheck}.
	 */
	private void findBlock() {
		blockLocator.setDistanceCheck(distanceCheck.isEnabled());
		//If there is a Block of our set type in the given radius
		if (blockLocator.locateClosest(this.radius.getValue(), false)) {
			//Set the MineTask's Block to that Block
			mineTask.setBlock(blockLocator.getPosition(), blockLocator.getFace());
			//Start the MineTask
			huzuni.lookManager.requestTask(this, mineTask);
		}
	}

	/**
	 * Sets the {@link #type Block type} for mining when the Player right clicks a Block.
	 */
	private void onMouseClicked(MouseEvent event) {
		//If we right clicked
		if (event.getButton() == MouseButton.RIGHT) {
			//If the result is present (this technically isn't necessary, since 99% of the time it is)
			if (mc.getMouseResult().isPresent()) {
				//If our mouse is over a Block
				if (mc.getMouseResult().get() == Result.BLOCK) {
					//Set the Block type to the Block at the moused position
					this.type = getWorld().getBlock(mc.getMouseVector());
					//Cancel mining whatever Block we're mining now
					mineTask.cancelMining();
				}
			}
		}
	}

	@Override
	public String getDisplayNameForRender() {
		return super.getDisplayNameForRender() + (type == null ? "" : String.format(" (%s)", type.name()));
	}

}
