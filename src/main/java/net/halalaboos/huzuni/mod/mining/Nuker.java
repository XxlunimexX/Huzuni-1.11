package net.halalaboos.huzuni.mod.mining;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Toggleable;
import net.halalaboos.huzuni.api.node.Value;
import net.halalaboos.huzuni.api.task.MineTask;
import net.halalaboos.huzuni.api.util.BlockLocator;
import net.halalaboos.huzuni.api.util.MinecraftUtils;
import net.halalaboos.huzuni.gui.Notification.NotificationType;
import net.halalaboos.mcwrapper.api.event.input.MouseEvent;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.event.world.WorldLoadEvent;
import net.halalaboos.mcwrapper.api.util.Face;
import net.halalaboos.mcwrapper.api.util.MouseButton;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.getWorld;

/**
 * TODO: Port to MCWrapper
 */
public final class Nuker extends BasicMod {
	
	private final Value radius = new Value("Radius", " blocks", 1F, 5F, 5F, 1F, "Radius around the player to mine blocks");
	
	private final Value mineDelay = new Value("Mine delay", " ms", 0F, 100F, 500F, 5F, "Delay in MS between mining of each block");

	private final Toggleable distanceCheck = new Toggleable("Distance check", "Checks if the player can regularly mine the block");
	
	private final Toggleable silent = new Toggleable("Silent", "Adjust player rotation server-sided only");

	private final MineTask mineTask = new MineTask(this);
	
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
	
	private Block type;
	
	public Nuker() {
		super("Nuker", "Annihilates blocks within a radius surrounding the player", Keyboard.KEY_L);
		this.addChildren(radius, mineDelay, distanceCheck, silent);
		setAuthor("Halalaboos");
		this.setCategory(Category.MINING);
		silent.setEnabled(true);
		huzuni.lookManager.registerTaskHolder(this);
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
			mineTask.setReset(silent.isEnabled());
			mineTask.setMineDelay((int) mineDelay.getValue());
			if (mineTask.hasBlock())
				huzuni.lookManager.requestTask(this, mineTask);
			else {
				huzuni.lookManager.withdrawTask(mineTask);
				findBlock();
			}
		}
	}

	private void findBlock() {
		blockLocator.setDistanceCheck(distanceCheck.isEnabled());
		if (blockLocator.locateClosest(this.radius.getValue(), false)) {
			mineTask.setBlock(blockLocator.getPosition(), blockLocator.getFace());
			huzuni.lookManager.requestTask(this, mineTask);
		}
	}

	private void onMouseClicked(MouseEvent event) {
		if (event.getButton() == MouseButton.RIGHT) {
			if (mc.objectMouseOver != null) {
				if (mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
					IBlockState blockState = mc.world.getBlockState(mc.objectMouseOver.getBlockPos());
					this.type = blockState.getBlock();
					mineTask.cancelMining();
				}
			}
		}
	}
	
	@Override
	public String getDisplayNameForRender() {
		return super.getDisplayNameForRender() + (type == null ? "" : String.format(" (%s)", type.getLocalizedName()));
	}
	
}
