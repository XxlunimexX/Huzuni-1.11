package net.halalaboos.huzuni.mod.visual;

import net.halalaboos.huzuni.api.mod.BasicMod;
import net.halalaboos.huzuni.api.mod.Category;
import net.halalaboos.huzuni.api.node.impl.ItemSelector;
import net.halalaboos.huzuni.api.node.impl.Value;
import net.halalaboos.mcwrapper.api.block.Block;
import net.halalaboos.mcwrapper.api.block.BlockTypes;
import net.halalaboos.mcwrapper.api.block.types.Bush;
import net.halalaboos.mcwrapper.api.item.ItemStack;
import net.halalaboos.mcwrapper.api.item.ItemTypes;
import org.lwjgl.input.Keyboard;

import static net.halalaboos.mcwrapper.api.MCWrapper.getMinecraft;

/**
 * Shows blocks hidden behind other blocks.
 *
 * TODO - Make selection like it was in 1.8x versions
 * */
public class Xray extends BasicMod {
	
	public static final Xray INSTANCE = new Xray();
	
	public final ItemSelector<Block> blockList = new ItemSelector<>("Xray blocks", "Select blocks that you want enabled.");
	
	public final Value opacity = new Value("Opacity", "%", 0F, 30F, 100F, 1F, "Opacity blocks are rendered with.");

    private float brightness = 0;

    private Xray() {
		super("Xray", "See stuff", Keyboard.KEY_X);
		this.setCategory(Category.VISUAL);
		setAuthor("Halalaboos");
		this.addChildren(blockList);
		blockList.addItem(ItemStack.from(ItemTypes.WATER_BUCKET), BlockTypes.WATER, BlockTypes.FLOWING_WATER);
		blockList.addItem(ItemStack.from(ItemTypes.LAVA_BUCKET), BlockTypes.LAVA, BlockTypes.FLOWING_LAVA);
		blockList.addItem(ItemStack.from(BlockTypes.DIAMOND_ORE), BlockTypes.DIAMOND_ORE);
		blockList.addItem(ItemStack.from(BlockTypes.EMERALD_ORE), BlockTypes.EMERALD_ORE);
		blockList.addItem(ItemStack.from(BlockTypes.LAPIS_ORE), BlockTypes.LAPIS_ORE);
		blockList.addItem(ItemStack.from(BlockTypes.GOLD_ORE), BlockTypes.GOLD_ORE);
		
		blockList.addItem(ItemStack.from(BlockTypes.IRON_ORE), BlockTypes.IRON_ORE);
		blockList.addItem(ItemStack.from(BlockTypes.REDSTONE_ORE), BlockTypes.REDSTONE_ORE, BlockTypes.LIT_REDSTONE_ORE);
		blockList.addItem(ItemStack.from(BlockTypes.COAL_ORE), BlockTypes.COAL_ORE);
		blockList.addItem(ItemStack.from(BlockTypes.QUARTZ_ORE), BlockTypes.QUARTZ_ORE);
		blockList.addItem(ItemStack.from(BlockTypes.DIAMOND_BLOCK), BlockTypes.DIAMOND_BLOCK);
		blockList.addItem(ItemStack.from(BlockTypes.EMERALD_BLOCK), BlockTypes.EMERALD_BLOCK);
		
		blockList.addItem(ItemStack.from(BlockTypes.LAPIS_BLOCK), BlockTypes.LAPIS_BLOCK);
		blockList.addItem(ItemStack.from(BlockTypes.GOLD_BLOCK), BlockTypes.GOLD_BLOCK);
		blockList.addItem(ItemStack.from(BlockTypes.IRON_BLOCK), BlockTypes.IRON_BLOCK);
		blockList.addItem(ItemStack.from(BlockTypes.REDSTONE_BLOCK), BlockTypes.REDSTONE_BLOCK);
		blockList.addItem(ItemStack.from(BlockTypes.COAL_BLOCK), BlockTypes.COAL_BLOCK);
		blockList.addItem(ItemStack.from(BlockTypes.QUARTZ_BLOCK), BlockTypes.QUARTZ_BLOCK);

		blockList.addItem(ItemStack.from(BlockTypes.OBSIDIAN), BlockTypes.OBSIDIAN);
		blockList.addItem(ItemStack.from(BlockTypes.MOSSY_COBBLESTONE), BlockTypes.MOSSY_COBBLESTONE);
		blockList.addItem(ItemStack.from(BlockTypes.MOB_SPAWNER), BlockTypes.MOB_SPAWNER);
		blockList.addItem(ItemStack.from(BlockTypes.END_BRICKS), BlockTypes.END_BRICKS);
		blockList.addItem(ItemStack.from(BlockTypes.END_STONE), BlockTypes.END_STONE);
		blockList.addItem(ItemStack.from(BlockTypes.NETHER_BRICK), BlockTypes.NETHER_BRICK);
		
		blockList.addItem(ItemStack.from(BlockTypes.NETHERRACK), BlockTypes.NETHERRACK);
		blockList.addItem(ItemStack.from(BlockTypes.SOUL_SAND), BlockTypes.SOUL_SAND);
		blockList.addItem(ItemStack.from(BlockTypes.PRISMARINE), BlockTypes.PRISMARINE);
		blockList.addItem(ItemStack.from(BlockTypes.PURPUR_BLOCK), BlockTypes.PURPUR_BLOCK);
		blockList.addItem(ItemStack.from(BlockTypes.SLIME_BLOCK), BlockTypes.SLIME_BLOCK);
		blockList.addItem(ItemStack.from(BlockTypes.SPONGE), BlockTypes.SPONGE);
		
		blockList.addItem(ItemStack.from(BlockTypes.BOOKSHELF), BlockTypes.BOOKSHELF);
		blockList.addItem(ItemStack.from(BlockTypes.BRICK_BLOCK), BlockTypes.BRICK_BLOCK);
		blockList.addItem(ItemStack.from(BlockTypes.COBBLESTONE), BlockTypes.COBBLESTONE);
		blockList.addItem(ItemStack.from(BlockTypes.HAY_BLOCK), BlockTypes.HAY_BLOCK);
		blockList.addItem(ItemStack.from(BlockTypes.PUMPKIN), BlockTypes.PUMPKIN, BlockTypes.LIT_PUMPKIN);
		blockList.addItem(ItemStack.from(BlockTypes.LOG), BlockTypes.LOG, BlockTypes.LOG2);
		
		blockList.addItem(ItemStack.from(BlockTypes.SNOW), BlockTypes.SNOW);
		blockList.addItem(ItemStack.from(BlockTypes.WOOL), BlockTypes.WOOL);
		// blockList.addItem(ItemStack.from(BlockTypes.ICE), BlockTypes.ICE, BlockTypes.FROSTED_ICE, BlockTypes.PACKED_ICE);
		blockList.addItem(ItemStack.from(BlockTypes.BEDROCK), BlockTypes.BEDROCK);
		blockList.addItem(ItemStack.from(BlockTypes.CLAY), BlockTypes.CLAY);
		blockList.addItem(ItemStack.from(BlockTypes.HARDENED_CLAY), BlockTypes.HARDENED_CLAY, BlockTypes.STAINED_HARDENED_CLAY);
		
		blockList.addItem(ItemStack.from(BlockTypes.SAND), BlockTypes.SAND);
		blockList.addItem(ItemStack.from(BlockTypes.GRAVEL), BlockTypes.GRAVEL);
		blockList.addItem(ItemStack.from(BlockTypes.SANDSTONE), BlockTypes.SANDSTONE);
		blockList.addItem(ItemStack.from(BlockTypes.RED_SANDSTONE), BlockTypes.RED_SANDSTONE);
		// blockList.addItem(ItemStack.from(BlockTypes.LEAVES), BlockTypes.LEAVES, BlockTypes.LEAVES2);
		// blockList.addItem(ItemStack.from(BlockTypes.GLASS), BlockTypes.GLASS);
		this.settings.setDisplayable(false);
	}
	
	@Override
	protected void onEnable() {
		brightness = getMinecraft().getSettings().getGamma();
		getMinecraft().getSettings().setGamma(1000F);
	}

	@Override
	protected void onDisable() {
		getMinecraft().getSettings().setGamma(brightness);
	}
	
	@Override
	public void toggle() {
		super.toggle();
		getMinecraft().loadRenderers();
	}
	
	public boolean isEnabled(Block block) {
		return blockList.isEnabledObject(block) || block == BlockTypes.BED;
	}

	public boolean shouldIgnore(Block block) {
		return !isEnabled(block) || block instanceof Bush;
	}

	public int getOpacity() {
		return (int) ((opacity.getValue() / 100F) * 255F);
	}
	
	public boolean hasOpacity() {
		return getOpacity() > 20;
	}
}
