package net.halalaboos.mcwrapper.api.block;

import net.halalaboos.mcwrapper.api.MCWrapper;

/**
 * Used to reference any of the (vanilla) Block types.
 * <p>The reason this is not an Enum is because this method of listing the Blocks allows developers
 * to quickly do things such as:</p>
 * <code>if (block == BlockTypes.AIR)</code>
 * <p>While with an Enum-based approach it would look more like:</p>
 * <code>if (block == BlockTypes.AIR.getBlock())</code>
 * <p>This class isn't as clean as an Enum would be, but uses of this class are much cleaner as a result
 * of taking this approach.</p>
 */
public class BlockTypes {

	public static final Block AIR;
	public static final Block STONE;
	public static final Block GRASS; //TODO - Make a class for this Block
	public static final Block DIRT;
	public static final Block COBBLESTONE;
	public static final Block PLANKS;
	public static final Block SAPLING;
	public static final Block BEDROCK;
	public static final Block FLOWING_WATER; //TODO - Make a class for this Block
	public static final Block WATER; //TODO - Make a class for this Block
	public static final Block FLOWING_LAVA; //TODO - Make a class for this Block
	public static final Block LAVA; //TODO - Make a class for this Block
	public static final Block SAND; //TODO - Make a class for this Block
	public static final Block GRAVEL;
	public static final Block GOLD_ORE;
	public static final Block IRON_ORE;
	public static final Block COAL_ORE;
	public static final Block LOG;
	public static final Block LOG2;
	public static final Block LEAVES; //TODO - Make a class for this Block
	public static final Block LEAVES2; //TODO - Make a class for this Block
	public static final Block SPONGE;
	public static final Block GLASS;
	public static final Block LAPIS_ORE;
	public static final Block LAPIS_BLOCK;
	public static final Block DISPENSER;
	public static final Block SANDSTONE;
	public static final Block NOTEBLOCK;
	public static final Block BED;
	public static final Block GOLDEN_RAIL;
	public static final Block DETECTOR_RAIL;
	public static final Block STICKY_PISTON; //TODO - Make a class for this Block
	public static final Block WEB;
	public static final Block TALLGRASS; //TODO - Make a class for this Block
	public static final Block DEADBUSH; //TODO - Make a class for this Block
	public static final Block PISTON; //TODO - Make a class for this Block
	public static final Block PISTON_HEAD; //TODO - Make a class for this Block
	public static final Block WOOL;
	public static final Block PISTON_EXTENSION; //TODO - Make a class for this Block
	public static final Block YELLOW_FLOWER; //TODO - Make a class for this Block
	public static final Block RED_FLOWER; //TODO - Make a class for this Block
	public static final Block BROWN_MUSHROOM; //TODO - Make a class for this Block
	public static final Block RED_MUSHROOM; //TODO - Make a class for this Block
	public static final Block GOLD_BLOCK;
	public static final Block IRON_BLOCK;
	public static final Block DOUBLE_STONE_SLAB; //TODO - Make a class for this Block
	public static final Block STONE_SLAB; //TODO - Make a class for this Block
	public static final Block BRICK_BLOCK;
	public static final Block TNT;
	public static final Block BOOKSHELF;
	public static final Block MOSSY_COBBLESTONE;
	public static final Block OBSIDIAN;
	public static final Block TORCH;
	public static final Block FIRE; //TODO - Make a class for this Block
	public static final Block MOB_SPAWNER;
	public static final Block OAK_STAIRS;
	public static final Block CHEST; //TODO - Make a class for this Block
	public static final Block REDSTONE_WIRE; //TODO - Make a class for this Block
	public static final Block DIAMOND_ORE;
	public static final Block DIAMOND_BLOCK;
	public static final Block CRAFTING_TABLE;
	public static final Block WHEAT;
	public static final Block FARMLAND;
	public static final Block FURNACE;
	public static final Block LIT_FURNACE;
	public static final Block STANDING_SIGN;
	public static final Block OAK_DOOR; //TODO - Make a class for this Block
	public static final Block SPRUCE_DOOR; //TODO - Make a class for this Block
	public static final Block BIRCH_DOOR; //TODO - Make a class for this Block
	public static final Block JUNGLE_DOOR; //TODO - Make a class for this Block
	public static final Block ACACIA_DOOR; //TODO - Make a class for this Block
	public static final Block DARK_OAK_DOOR; //TODO - Make a class for this Block
	public static final Block LADDER;
	public static final Block RAIL;
	public static final Block STONE_STAIRS;
	public static final Block WALL_SIGN;
	public static final Block LEVER;
	public static final Block STONE_PRESSURE_PLATE;
	public static final Block IRON_DOOR; //TODO - Make a class for this Block
	public static final Block WOODEN_PRESSURE_PLATE;
	public static final Block REDSTONE_ORE;
	public static final Block LIT_REDSTONE_ORE;
	public static final Block UNLIT_REDSTONE_TORCH;
	public static final Block REDSTONE_TORCH;
	public static final Block STONE_BUTTON;
	public static final Block SNOW_LAYER;
	public static final Block ICE;
	public static final Block SNOW;
	public static final Block CACTUS; //TODO - Make a class for this Block
	public static final Block CLAY;
	public static final Block REEDS; //TODO - Make a class for this Block
	public static final Block JUKEBOX;
	public static final Block OAK_FENCE;
	public static final Block SPRUCE_FENCE;
	public static final Block BIRCH_FENCE;
	public static final Block JUNGLE_FENCE;
	public static final Block DARK_OAK_FENCE;
	public static final Block ACACIA_FENCE;
	public static final Block PUMPKIN;
	public static final Block NETHERRACK;
	public static final Block SOUL_SAND;
	public static final Block GLOWSTONE;
	public static final Block PORTAL; //TODO - Make a class for this Block
	public static final Block LIT_PUMPKIN;
	public static final Block CAKE;
	public static final Block UNPOWERED_REPEATER; //TODO - Make a class for this Block
	public static final Block POWERED_REPEATER; //TODO - Make a class for this Block
	public static final Block TRAPDOOR;
	public static final Block MONSTER_EGG;
	public static final Block STONEBRICK;
	public static final Block BROWN_MUSHROOM_BLOCK;
	public static final Block RED_MUSHROOM_BLOCK;
	public static final Block IRON_BARS;
	public static final Block GLASS_PANE;
	public static final Block MELON_BLOCK;
	public static final Block PUMPKIN_STEM;
	public static final Block MELON_STEM;
	public static final Block VINE;
	public static final Block OAK_FENCE_GATE;
	public static final Block SPRUCE_FENCE_GATE;
	public static final Block BIRCH_FENCE_GATE;
	public static final Block JUNGLE_FENCE_GATE;
	public static final Block DARK_OAK_FENCE_GATE;
	public static final Block ACACIA_FENCE_GATE;
	public static final Block BRICK_STAIRS;
	public static final Block STONE_BRICK_STAIRS;
	public static final Block MYCELIUM; //TODO - Make a class for this Block
	public static final Block WATERLILY;
	public static final Block NETHER_BRICK;
	public static final Block NETHER_BRICK_FENCE;
	public static final Block NETHER_BRICK_STAIRS;
	public static final Block NETHER_WART;
	public static final Block ENCHANTING_TABLE;
	public static final Block BREWING_STAND;
	public static final Block CAULDRON; //TODO - Make a class for this Block
	public static final Block END_PORTAL;
	public static final Block END_PORTAL_FRAME;
	public static final Block END_STONE;
	public static final Block DRAGON_EGG;
	public static final Block REDSTONE_LAMP;
	public static final Block LIT_REDSTONE_LAMP;
	public static final Block DOUBLE_WOODEN_SLAB; //TODO - Make a class for this Block
	public static final Block WOODEN_SLAB; //TODO - Make a class for this Block
	public static final Block COCOA;
	public static final Block SANDSTONE_STAIRS;
	public static final Block EMERALD_ORE;
	public static final Block ENDER_CHEST;
	public static final Block TRIPWIRE_HOOK; //TODO - Make a class for this Block
	public static final Block TRIPWIRE;
	public static final Block EMERALD_BLOCK;
	public static final Block SPRUCE_STAIRS;
	public static final Block BIRCH_STAIRS;
	public static final Block JUNGLE_STAIRS;
	public static final Block COMMAND_BLOCK;
	public static final Block BEACON; //TODO - Make a class for this Block
	public static final Block COBBLESTONE_WALL;
	public static final Block FLOWER_POT;
	public static final Block CARROTS;
	public static final Block POTATOES;
	public static final Block WOODEN_BUTTON;
	public static final Block SKULL; //TODO - Make a class for this Block
	public static final Block ANVIL;
	public static final Block TRAPPED_CHEST;
	public static final Block LIGHT_WEIGHTED_PRESSURE_PLATE;
	public static final Block HEAVY_WEIGHTED_PRESSURE_PLATE;
	public static final Block UNPOWERED_COMPARATOR; //TODO - Make a class for this Block
	public static final Block POWERED_COMPARATOR; //TODO - Make a class for this Block
	public static final Block DAYLIGHT_DETECTOR; //TODO - Make a class for this Block
	public static final Block DAYLIGHT_DETECTOR_INVERTED; //TODO - Make a class for this Block
	public static final Block REDSTONE_BLOCK;
	public static final Block QUARTZ_ORE;
	public static final Block HOPPER; //TODO - Make a class for this Block
	public static final Block QUARTZ_BLOCK;
	public static final Block QUARTZ_STAIRS;
	public static final Block ACTIVATOR_RAIL;
	public static final Block DROPPER;
	public static final Block STAINED_HARDENED_CLAY;
	public static final Block BARRIER;
	public static final Block IRON_TRAPDOOR;
	public static final Block HAY_BLOCK;
	public static final Block CARPET;
	public static final Block HARDENED_CLAY;
	public static final Block COAL_BLOCK;
	public static final Block PACKED_ICE;
	public static final Block ACACIA_STAIRS;
	public static final Block DARK_OAK_STAIRS;
	public static final Block SLIME_BLOCK;
	public static final Block DOUBLE_PLANT; //TODO - Make a class for this Block
	public static final Block STAINED_GLASS; //TODO - Make a class for this Block
	public static final Block STAINED_GLASS_PANE; //TODO - Make a class for this Block
	public static final Block PRISMARINE;
	public static final Block SEA_LANTERN;
	public static final Block STANDING_BANNER;
	public static final Block WALL_BANNER;
	public static final Block RED_SANDSTONE;
	public static final Block RED_SANDSTONE_STAIRS;
	public static final Block DOUBLE_STONE_SLAB2; //TODO - Make a class for this Block
	public static final Block STONE_SLAB2; //TODO - Make a class for this Block
	public static final Block END_ROD;
	public static final Block CHORUS_PLANT;
	public static final Block CHORUS_FLOWER;
	public static final Block PURPUR_BLOCK;
	public static final Block PURPUR_PILLAR;
	public static final Block PURPUR_STAIRS;
	public static final Block PURPUR_DOUBLE_SLAB;
	public static final Block PURPUR_SLAB;
	public static final Block END_BRICKS;
	public static final Block BEETROOTS;
	public static final Block GRASS_PATH;
	public static final Block END_GATEWAY;
	public static final Block REPEATING_COMMAND_BLOCK;
	public static final Block CHAIN_COMMAND_BLOCK;
	public static final Block FROSTED_ICE;
	public static final Block MAGMA;
	public static final Block NETHER_WART_BLOCK;
	public static final Block RED_NETHER_BRICK;
	public static final Block BONE_BLOCK;
	public static final Block STRUCTURE_VOID;
	public static final Block OBSERVER;
	public static final Block WHITE_SHULKER_BOX;
	public static final Block ORANGE_SHULKER_BOX;
	public static final Block MAGENTA_SHULKER_BOX;
	public static final Block LIGHT_BLUE_SHULKER_BOX;
	public static final Block YELLOW_SHULKER_BOX;
	public static final Block LIME_SHULKER_BOX;
	public static final Block PINK_SHULKER_BOX;
	public static final Block GRAY_SHULKER_BOX;
	public static final Block SILVER_SHULKER_BOX;
	public static final Block CYAN_SHULKER_BOX;
	public static final Block PURPLE_SHULKER_BOX;
	public static final Block BLUE_SHULKER_BOX;
	public static final Block BROWN_SHULKER_BOX;
	public static final Block GREEN_SHULKER_BOX;
	public static final Block RED_SHULKER_BOX;
	public static final Block BLACK_SHULKER_BOX;
	public static final Block STRUCTURE_BLOCK;

	static
	{
		AIR = getBlock("air");
		STONE = getBlock("stone");
		GRASS = getBlock("grass");
		DIRT = getBlock("dirt");
		COBBLESTONE = getBlock("cobblestone");
		PLANKS = getBlock("planks");
		SAPLING = getBlock("sapling");
		BEDROCK = getBlock("bedrock");
		FLOWING_WATER = getBlock("flowing_water");
		WATER = getBlock("water");
		FLOWING_LAVA = getBlock("flowing_lava");
		LAVA = getBlock("lava");
		SAND = getBlock("sand");
		GRAVEL = getBlock("gravel");
		GOLD_ORE = getBlock("gold_ore");
		IRON_ORE = getBlock("iron_ore");
		COAL_ORE = getBlock("coal_ore");
		LOG = getBlock("log");
		LOG2 = getBlock("log2");
		LEAVES = getBlock("leaves");
		LEAVES2 = getBlock("leaves2");
		SPONGE = getBlock("sponge");
		GLASS = getBlock("glass");
		LAPIS_ORE = getBlock("lapis_ore");
		LAPIS_BLOCK = getBlock("lapis_block");
		DISPENSER = getBlock("dispenser");
		SANDSTONE = getBlock("sandstone");
		NOTEBLOCK = getBlock("noteblock");
		BED = getBlock("bed");
		GOLDEN_RAIL = getBlock("golden_rail");
		DETECTOR_RAIL = getBlock("detector_rail");
		STICKY_PISTON = getBlock("sticky_piston");
		WEB = getBlock("web");
		TALLGRASS = getBlock("tallgrass");
		DEADBUSH = getBlock("deadbush");
		PISTON = getBlock("piston");
		PISTON_HEAD = getBlock("piston_head");
		WOOL = getBlock("wool");
		PISTON_EXTENSION = getBlock("piston_extension");
		YELLOW_FLOWER = getBlock("yellow_flower");
		RED_FLOWER = getBlock("red_flower");
		BROWN_MUSHROOM = getBlock("brown_mushroom");
		RED_MUSHROOM = getBlock("red_mushroom");
		GOLD_BLOCK = getBlock("gold_block");
		IRON_BLOCK = getBlock("iron_block");
		DOUBLE_STONE_SLAB = getBlock("double_stone_slab");
		STONE_SLAB = getBlock("stone_slab");
		BRICK_BLOCK = getBlock("brick_block");
		TNT = getBlock("tnt");
		BOOKSHELF = getBlock("bookshelf");
		MOSSY_COBBLESTONE = getBlock("mossy_cobblestone");
		OBSIDIAN = getBlock("obsidian");
		TORCH = getBlock("torch");
		FIRE = getBlock("fire");
		MOB_SPAWNER = getBlock("mob_spawner");
		OAK_STAIRS = getBlock("oak_stairs");
		CHEST = getBlock("chest");
		REDSTONE_WIRE = getBlock("redstone_wire");
		DIAMOND_ORE = getBlock("diamond_ore");
		DIAMOND_BLOCK = getBlock("diamond_block");
		CRAFTING_TABLE = getBlock("crafting_table");
		WHEAT = getBlock("wheat");
		FARMLAND = getBlock("farmland");
		FURNACE = getBlock("furnace");
		LIT_FURNACE = getBlock("lit_furnace");
		STANDING_SIGN = getBlock("standing_sign");
		OAK_DOOR = getBlock("wooden_door");
		SPRUCE_DOOR = getBlock("spruce_door");
		BIRCH_DOOR = getBlock("birch_door");
		JUNGLE_DOOR = getBlock("jungle_door");
		ACACIA_DOOR = getBlock("acacia_door");
		DARK_OAK_DOOR = getBlock("dark_oak_door");
		LADDER = getBlock("ladder");
		RAIL = getBlock("rail");
		STONE_STAIRS = getBlock("stone_stairs");
		WALL_SIGN = getBlock("wall_sign");
		LEVER = getBlock("lever");
		STONE_PRESSURE_PLATE = getBlock("stone_pressure_plate");
		IRON_DOOR = getBlock("iron_door");
		WOODEN_PRESSURE_PLATE = getBlock("wooden_pressure_plate");
		REDSTONE_ORE = getBlock("redstone_ore");
		LIT_REDSTONE_ORE = getBlock("lit_redstone_ore");
		UNLIT_REDSTONE_TORCH = getBlock("unlit_redstone_torch");
		REDSTONE_TORCH = getBlock("redstone_torch");
		STONE_BUTTON = getBlock("stone_button");
		SNOW_LAYER = getBlock("snow_layer");
		ICE = getBlock("ice");
		SNOW = getBlock("snow");
		CACTUS = getBlock("cactus");
		CLAY = getBlock("clay");
		REEDS = getBlock("reeds");
		JUKEBOX = getBlock("jukebox");
		OAK_FENCE = getBlock("fence");
		SPRUCE_FENCE = getBlock("spruce_fence");
		BIRCH_FENCE = getBlock("birch_fence");
		JUNGLE_FENCE = getBlock("jungle_fence");
		DARK_OAK_FENCE = getBlock("dark_oak_fence");
		ACACIA_FENCE = getBlock("acacia_fence");
		PUMPKIN = getBlock("pumpkin");
		NETHERRACK = getBlock("netherrack");
		SOUL_SAND = getBlock("soul_sand");
		GLOWSTONE = getBlock("glowstone");
		PORTAL = getBlock("portal");
		LIT_PUMPKIN = getBlock("lit_pumpkin");
		CAKE = getBlock("cake");
		UNPOWERED_REPEATER = getBlock("unpowered_repeater");
		POWERED_REPEATER = getBlock("powered_repeater");
		TRAPDOOR = getBlock("trapdoor");
		MONSTER_EGG = getBlock("monster_egg");
		STONEBRICK = getBlock("stonebrick");
		BROWN_MUSHROOM_BLOCK = getBlock("brown_mushroom_block");
		RED_MUSHROOM_BLOCK = getBlock("red_mushroom_block");
		IRON_BARS = getBlock("iron_bars");
		GLASS_PANE = getBlock("glass_pane");
		MELON_BLOCK = getBlock("melon_block");
		PUMPKIN_STEM = getBlock("pumpkin_stem");
		MELON_STEM = getBlock("melon_stem");
		VINE = getBlock("vine");
		OAK_FENCE_GATE = getBlock("fence_gate");
		SPRUCE_FENCE_GATE = getBlock("spruce_fence_gate");
		BIRCH_FENCE_GATE = getBlock("birch_fence_gate");
		JUNGLE_FENCE_GATE = getBlock("jungle_fence_gate");
		DARK_OAK_FENCE_GATE = getBlock("dark_oak_fence_gate");
		ACACIA_FENCE_GATE = getBlock("acacia_fence_gate");
		BRICK_STAIRS = getBlock("brick_stairs");
		STONE_BRICK_STAIRS = getBlock("stone_brick_stairs");
		MYCELIUM = getBlock("mycelium");
		WATERLILY = getBlock("waterlily");
		NETHER_BRICK = getBlock("nether_brick");
		NETHER_BRICK_FENCE = getBlock("nether_brick_fence");
		NETHER_BRICK_STAIRS = getBlock("nether_brick_stairs");
		NETHER_WART = getBlock("nether_wart");
		ENCHANTING_TABLE = getBlock("enchanting_table");
		BREWING_STAND = getBlock("brewing_stand");
		CAULDRON = getBlock("cauldron");
		END_PORTAL = getBlock("end_portal");
		END_PORTAL_FRAME = getBlock("end_portal_frame");
		END_STONE = getBlock("end_stone");
		DRAGON_EGG = getBlock("dragon_egg");
		REDSTONE_LAMP = getBlock("redstone_lamp");
		LIT_REDSTONE_LAMP = getBlock("lit_redstone_lamp");
		DOUBLE_WOODEN_SLAB = getBlock("double_wooden_slab");
		WOODEN_SLAB = getBlock("wooden_slab");
		COCOA = getBlock("cocoa");
		SANDSTONE_STAIRS = getBlock("sandstone_stairs");
		EMERALD_ORE = getBlock("emerald_ore");
		ENDER_CHEST = getBlock("ender_chest");
		TRIPWIRE_HOOK = getBlock("tripwire_hook");
		TRIPWIRE = getBlock("tripwire");
		EMERALD_BLOCK = getBlock("emerald_block");
		SPRUCE_STAIRS = getBlock("spruce_stairs");
		BIRCH_STAIRS = getBlock("birch_stairs");
		JUNGLE_STAIRS = getBlock("jungle_stairs");
		COMMAND_BLOCK = getBlock("command_block");
		BEACON = getBlock("beacon");
		COBBLESTONE_WALL = getBlock("cobblestone_wall");
		FLOWER_POT = getBlock("flower_pot");
		CARROTS = getBlock("carrots");
		POTATOES = getBlock("potatoes");
		WOODEN_BUTTON = getBlock("wooden_button");
		SKULL = getBlock("skull");
		ANVIL = getBlock("anvil");
		TRAPPED_CHEST = getBlock("trapped_chest");
		LIGHT_WEIGHTED_PRESSURE_PLATE = getBlock("light_weighted_pressure_plate");
		HEAVY_WEIGHTED_PRESSURE_PLATE = getBlock("heavy_weighted_pressure_plate");
		UNPOWERED_COMPARATOR = getBlock("unpowered_comparator");
		POWERED_COMPARATOR = getBlock("powered_comparator");
		DAYLIGHT_DETECTOR = getBlock("daylight_detector");
		DAYLIGHT_DETECTOR_INVERTED = getBlock("daylight_detector_inverted");
		REDSTONE_BLOCK = getBlock("redstone_block");
		QUARTZ_ORE = getBlock("quartz_ore");
		HOPPER = getBlock("hopper");
		QUARTZ_BLOCK = getBlock("quartz_block");
		QUARTZ_STAIRS = getBlock("quartz_stairs");
		ACTIVATOR_RAIL = getBlock("activator_rail");
		DROPPER = getBlock("dropper");
		STAINED_HARDENED_CLAY = getBlock("stained_hardened_clay");
		BARRIER = getBlock("barrier");
		IRON_TRAPDOOR = getBlock("iron_trapdoor");
		HAY_BLOCK = getBlock("hay_block");
		CARPET = getBlock("carpet");
		HARDENED_CLAY = getBlock("hardened_clay");
		COAL_BLOCK = getBlock("coal_block");
		PACKED_ICE = getBlock("packed_ice");
		ACACIA_STAIRS = getBlock("acacia_stairs");
		DARK_OAK_STAIRS = getBlock("dark_oak_stairs");
		SLIME_BLOCK = getBlock("slime");
		DOUBLE_PLANT = getBlock("double_plant");
		STAINED_GLASS = getBlock("stained_glass");
		STAINED_GLASS_PANE = getBlock("stained_glass_pane");
		PRISMARINE = getBlock("prismarine");
		SEA_LANTERN = getBlock("sea_lantern");
		STANDING_BANNER = getBlock("standing_banner");
		WALL_BANNER = getBlock("wall_banner");
		RED_SANDSTONE = getBlock("red_sandstone");
		RED_SANDSTONE_STAIRS = getBlock("red_sandstone_stairs");
		DOUBLE_STONE_SLAB2 = getBlock("double_stone_slab2");
		STONE_SLAB2 = getBlock("stone_slab2");
		END_ROD = getBlock("end_rod");
		CHORUS_PLANT = getBlock("chorus_plant");
		CHORUS_FLOWER = getBlock("chorus_flower");
		PURPUR_BLOCK = getBlock("purpur_block");
		PURPUR_PILLAR = getBlock("purpur_pillar");
		PURPUR_STAIRS = getBlock("purpur_stairs");
		PURPUR_DOUBLE_SLAB = getBlock("purpur_double_slab");
		PURPUR_SLAB = getBlock("purpur_slab");
		END_BRICKS = getBlock("end_bricks");
		BEETROOTS = getBlock("beetroots");
		GRASS_PATH = getBlock("grass_path");
		END_GATEWAY = getBlock("end_gateway");
		REPEATING_COMMAND_BLOCK = getBlock("repeating_command_block");
		CHAIN_COMMAND_BLOCK = getBlock("chain_command_block");
		FROSTED_ICE = getBlock("frosted_ice");
		MAGMA = getBlock("magma");
		NETHER_WART_BLOCK = getBlock("nether_wart_block");
		RED_NETHER_BRICK = getBlock("red_nether_brick");
		BONE_BLOCK = getBlock("bone_block");
		STRUCTURE_VOID = getBlock("structure_void");
		OBSERVER = getBlock("observer");
		WHITE_SHULKER_BOX = getBlock("white_shulker_box");
		ORANGE_SHULKER_BOX = getBlock("orange_shulker_box");
		MAGENTA_SHULKER_BOX = getBlock("magenta_shulker_box");
		LIGHT_BLUE_SHULKER_BOX = getBlock("light_blue_shulker_box");
		YELLOW_SHULKER_BOX = getBlock("yellow_shulker_box");
		LIME_SHULKER_BOX = getBlock("lime_shulker_box");
		PINK_SHULKER_BOX = getBlock("pink_shulker_box");
		GRAY_SHULKER_BOX = getBlock("gray_shulker_box");
		SILVER_SHULKER_BOX = getBlock("silver_shulker_box");
		CYAN_SHULKER_BOX = getBlock("cyan_shulker_box");
		PURPLE_SHULKER_BOX = getBlock("purple_shulker_box");
		BLUE_SHULKER_BOX = getBlock("blue_shulker_box");
		BROWN_SHULKER_BOX = getBlock("brown_shulker_box");
		GREEN_SHULKER_BOX = getBlock("green_shulker_box");
		RED_SHULKER_BOX = getBlock("red_shulker_box");
		BLACK_SHULKER_BOX = getBlock("black_shulker_box");
		STRUCTURE_BLOCK = getBlock("structure_block");
	}

	private static Block getBlock(String name) {
		return MCWrapper.getAdapter().getBlockRegistry().getBlock(name);
	}
}
