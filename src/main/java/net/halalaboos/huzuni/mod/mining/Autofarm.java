package net.halalaboos.huzuni.mod.mining;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.Mode;
import net.halalaboos.huzuni.api.node.impl.Toggleable;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.huzuni.api.task.MineTask;
import net.halalaboos.huzuni.api.task.PlaceTask;
import net.halalaboos.huzuni.api.util.BlockLocator;
import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.block.BlockTypes;
import net.halalaboos.mcwrapper.api.block.types.Crops;
import net.halalaboos.mcwrapper.api.block.types.Farmland;
import net.halalaboos.mcwrapper.api.event.player.PreMotionUpdateEvent;
import net.halalaboos.mcwrapper.api.event.world.WorldLoadEvent;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.item.ItemTypes;
import net.halalaboos.mcwrapper.api.item.types.Hoe;
import net.halalaboos.mcwrapper.api.item.types.Seeds;
import net.halalaboos.mcwrapper.api.util.enums.Face;
import net.halalaboos.mcwrapper.api.util.math.Vector3i;

import static net.halalaboos.mcwrapper.api.MCWrapper.getPlayer;
import static net.halalaboos.mcwrapper.api.MCWrapper.getWorld;

/**
 * Automatically performs farming tasks for the user.
 *
 * TODO: Fix bugs
 * */
public final class Autofarm extends BasicMod {
	
	public final Value radius = new Value("Radius", " blocks", 1F, 5F, 5F, 1F, "Radius around the player to mine blocks");

	public final Value mineDelay = new Value("Mine delay", " ms", 0F, 100F, 500F, 5F, "Delay in MS between mining of each block");

	public final Value placeDelay = new Value("Place delay", " ms", 0F, 100F, 500F, 5F, "Delay in MS between placing of each block");

	public final Toggleable distanceCheck = new Toggleable("Distance check", "Checks if the player can regularly mine the block");

	public final Toggleable silent = new Toggleable("Silent", "Adjust player rotation server-sided only");

	public final Mode<String> mode = new Mode<String>("Mode", "Switch between harvesting, planting, and using bonemeal", "Harvest", "Plant", "Bonemeal", "Hoe") {
		
		@Override
		public void setSelectedItem(int selectedItem) {
			super.setSelectedItem(selectedItem);
			placeTask.cancelPlacing();
			mineTask.cancelMining();
		}
	};
	
	private final MineTask mineTask = new MineTask(this);
	
	private final PlaceTask placeTask = new PlaceTask(this) {
		@Override
		protected boolean hasRequiredItem(ItemStack item) {
			return mode.getSelected() == 3 ? item.getItemType() instanceof Hoe : mode.getSelected() == 2 ? (item.getItemType() == ItemTypes.DYE && item.getData() == 15) : (item.getItemType() instanceof Seeds);
		}
		
		@Override
		public boolean shouldResetBlock() {
			Block block = getBlock();
			if (isCrop(block) && mode.getSelected() == 2) {
				Crops crops = ((Crops) block);
				return crops.isGrown(position);
			}
			return mode.getSelected() == 3 ? !isDirt(block) : super.shouldResetBlock();
		}
	};
	
	private final BlockLocator blockLocator = new BlockLocator() {

		@Override
		protected boolean isValidBlock(Vector3i position) {
			Block block = getWorld().getBlock(position);
			switch (mode.getSelected()) {
				case 0:
					if (isCrop(block)) {
						Crops crops = (Crops) block;
						return crops.isGrown(position);
					}
					break;
				case 1:
					return isFarmland(block) && !(getWorld().getBlock(position.up()) instanceof Crops);
				case 2:
					if (isCrop(block)) {
						Crops crops = (Crops) block;
						int age = crops.getAge(position);
						return age < crops.getMaxAge();
					}
					break;
				case 3:
					return isDirt(block);
			}
			return false;
		}

		@Override
		protected Face getFace(Vector3i position) {
			switch (mode.getSelected()) {
				case 0:
				case 2:
					return getPlayer().getFace().getOppositeFace();
				case 1:
				case 3:
					return Face.UP;
			}
			return null;
		}
		
	};

	public Autofarm() {
		super("Auto farm", "Harvest/plant crops automagically");
		setAuthor("Halalaboos");
		this.addChildren(radius, mineDelay, placeDelay, distanceCheck, silent, mode);
		this.setCategory(Category.MINING);
		silent.setEnabled(true);
		huzuni.lookManager.registerTaskHolder(this);
		subscribe(WorldLoadEvent.class, event -> {
			if (mineTask.hasBlock())
				huzuni.lookManager.withdrawTask(mineTask);
			if (placeTask.hasBlock())
				huzuni.lookManager.withdrawTask(placeTask);
		});
		subscribe(PreMotionUpdateEvent.class, this::onUpdate);
	}
	
	@Override
	public void onDisable() {
		huzuni.lookManager.withdrawTask(mineTask);
		huzuni.lookManager.withdrawTask(placeTask);
		blockLocator.reset();
	}

	private void onUpdate(PreMotionUpdateEvent event) {
		if (huzuni.lookManager.hasPriority(this)) {
			mineTask.setReset(silent.isEnabled());
			placeTask.setReset(silent.isEnabled());
			mineTask.setMineDelay((int) mineDelay.getValue());
			placeTask.setPlaceDelay((int) placeDelay.getValue());
			if (mineTask.hasBlock())
				huzuni.lookManager.requestTask(this, mineTask);
			else if (placeTask.hasBlock())
				huzuni.lookManager.requestTask(this, placeTask);
			else {
				huzuni.lookManager.withdrawTask(mineTask);
				huzuni.lookManager.withdrawTask(placeTask);
				findBlock();
			}
		}
	}

	/**
     * Finds a block using the block locator and updates the necessary tasks.
     * */
	private void findBlock() {
		blockLocator.setDistanceCheck(distanceCheck.isEnabled());
		if (blockLocator.locateClosest(radius.getValue(), false)) {
			if (mode.getSelected() == 0) {
				mineTask.setBlock(blockLocator.getPosition(), blockLocator.getFace());
				huzuni.lookManager.requestTask(this, mineTask);
			}
			if (mode.getSelected() > 0) {
				placeTask.setBlock(blockLocator.getPosition(), blockLocator.getFace());
				huzuni.lookManager.requestTask(this, placeTask);
			}
		}
	}
	
	@Override
	public String getDisplayNameForRender() {
		return super.getDisplayNameForRender();
	}

	/**
     * @return True if the block state is dirt.
     * */
	private boolean isDirt(Block block) {
		return block != BlockTypes.AIR && (block == BlockTypes.DIRT || block == BlockTypes.GRASS);
	}

	/**
     * @return True if the block state is farm land.
     * */
	private boolean isFarmland(Block block) {
		return block != BlockTypes.AIR && block instanceof Farmland;
	}

	/**
     * @return True if the block state is a crop.
     * */
	private boolean isCrop(Block block) {
		return block != BlockTypes.AIR && block instanceof Crops;
	}
	
}
