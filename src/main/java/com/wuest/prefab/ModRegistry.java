package com.wuest.prefab;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.wuest.prefab.Blocks.BlockAndesiteStairs;
import com.wuest.prefab.Blocks.BlockBoundary;
import com.wuest.prefab.Blocks.BlockCompressedObsidian;
import com.wuest.prefab.Blocks.BlockCompressedStone;
import com.wuest.prefab.Blocks.BlockDioriteStairs;
import com.wuest.prefab.Blocks.BlockDoubleAndesiteSlab;
import com.wuest.prefab.Blocks.BlockDoubleDioriteSlab;
import com.wuest.prefab.Blocks.BlockDoubleGlassSlab;
import com.wuest.prefab.Blocks.BlockDoubleGraniteSlab;
import com.wuest.prefab.Blocks.BlockDrafter;
import com.wuest.prefab.Blocks.BlockGlassSlab;
import com.wuest.prefab.Blocks.BlockGlassStairs;
import com.wuest.prefab.Blocks.BlockGraniteStairs;
import com.wuest.prefab.Blocks.BlockHalfAndesiteSlab;
import com.wuest.prefab.Blocks.BlockHalfDioriteSlab;
import com.wuest.prefab.Blocks.BlockHalfGlassSlab;
import com.wuest.prefab.Blocks.BlockHalfGraniteSlab;
import com.wuest.prefab.Blocks.BlockPaperLantern;
import com.wuest.prefab.Blocks.BlockPhasing;
import com.wuest.prefab.Capabilities.IStructureConfigurationCapability;
import com.wuest.prefab.Capabilities.StructureConfigurationCapability;
import com.wuest.prefab.Capabilities.Storage.StructureConfigurationStorage;
import com.wuest.prefab.Config.Structures.ChickenCoopConfiguration;
import com.wuest.prefab.Config.Structures.StructureConfiguration;
import com.wuest.prefab.Config.Structures.BasicStructureConfiguration.EnumBasicStructureName;
import com.wuest.prefab.Gui.GuiDrafter;
import com.wuest.prefab.Gui.Structures.GuiAdvancedWareHouse;
import com.wuest.prefab.Gui.Structures.GuiBasicStructure;
import com.wuest.prefab.Gui.Structures.GuiChickenCoop;
import com.wuest.prefab.Gui.Structures.GuiFishPond;
import com.wuest.prefab.Gui.Structures.GuiHorseStable;
import com.wuest.prefab.Gui.Structures.GuiModerateHouse;
import com.wuest.prefab.Gui.Structures.GuiModularHouse;
import com.wuest.prefab.Gui.Structures.GuiMonsterMasher;
import com.wuest.prefab.Gui.Structures.GuiNetherGate;
import com.wuest.prefab.Gui.Structures.GuiProduceFarm;
import com.wuest.prefab.Gui.Structures.GuiStartHouseChooser;
import com.wuest.prefab.Gui.Structures.GuiTreeFarm;
import com.wuest.prefab.Gui.Structures.GuiVillaerHouses;
import com.wuest.prefab.Gui.Structures.GuiWareHouse;
import com.wuest.prefab.Items.ItemBlockAndesiteSlab;
import com.wuest.prefab.Items.ItemBlockDioriteSlab;
import com.wuest.prefab.Items.ItemBlockGlassSlab;
import com.wuest.prefab.Items.ItemBlockGraniteSlab;
import com.wuest.prefab.Items.ItemBlockMeta;
import com.wuest.prefab.Items.ItemBogus;
import com.wuest.prefab.Items.ItemBundleOfTimber;
import com.wuest.prefab.Items.ItemCoilOfLanterns;
import com.wuest.prefab.Items.ItemCompressedChest;
import com.wuest.prefab.Items.ItemPalletOfBricks;
import com.wuest.prefab.Items.ItemPileOfBricks;
import com.wuest.prefab.Items.ItemStringOfLanterns;
import com.wuest.prefab.Items.ItemWarehouseUpgrade;
import com.wuest.prefab.Items.Structures.ItemAdvancedWareHouse;
import com.wuest.prefab.Items.Structures.ItemBasicStructure;
import com.wuest.prefab.Items.Structures.ItemChickenCoop;
import com.wuest.prefab.Items.Structures.ItemFishPond;
import com.wuest.prefab.Items.Structures.ItemHorseStable;
import com.wuest.prefab.Items.Structures.ItemInstantBridge;
import com.wuest.prefab.Items.Structures.ItemModerateHouse;
import com.wuest.prefab.Items.Structures.ItemModularHouse;
import com.wuest.prefab.Items.Structures.ItemMonsterMasher;
import com.wuest.prefab.Items.Structures.ItemNetherGate;
import com.wuest.prefab.Items.Structures.ItemProduceFarm;
import com.wuest.prefab.Items.Structures.ItemStartHouse;
import com.wuest.prefab.Items.Structures.ItemTreeFarm;
import com.wuest.prefab.Items.Structures.ItemVillagerHouses;
import com.wuest.prefab.Items.Structures.ItemWareHouse;
import com.wuest.prefab.Proxy.Messages.ConfigSyncMessage;
import com.wuest.prefab.Proxy.Messages.PlayerEntityTagMessage;
import com.wuest.prefab.Proxy.Messages.StructureTagMessage;
import com.wuest.prefab.Proxy.Messages.Handlers.ConfigSyncHandler;
import com.wuest.prefab.Proxy.Messages.Handlers.PlayerEntityHandler;
import com.wuest.prefab.Proxy.Messages.Handlers.StructureHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockStone;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import scala.Tuple2;

/**
 * This is the mod registry so there is a way to get to all instances of the blocks/items created by this mod.
 * @author WuestMan
 *
 */
public class ModRegistry
{
	/**
	 * The compressed dirt ore dictionary name.
	 */
	public static final String CompressedDirt = "compressedDirt1";
	
	/**
	 * The double compressed dirt ore dictionary name.
	 */
	public static final String DoubleCompressedDirt = "compressedDirt2";
	
	/**
	 * The compressed stone ore dictionary name.
	 */
	public static final String CompressedStone = "compressedStone1";
	
	/**
	 * The double compressed stone ore dictionary name.
	 */
	public static final String DoubleCompressedStone = "compressedStone2";
	
	/**
	 * The triple compressed stone ore dictionary name.
	 */
	public static final String TripleCompressedStone = "compressedStone3";
	
	/**
	 * The compressed glowstone ore dictionary name.
	 */
	public static final String CompressedGlowstone = "compressedGlowstone1";
	
	/**
	 * The double compressed glowstone ore dictionary name.
	 */
	public static final String DoubleCompressedGlowstone = "compressedGlowstone2";
	
	/**
	 * The compressed obsidian ore dictionary name.
	 */
	public static final String CompressedObsidian = "compressedObsidian1";
	
	/**
	 * The double compressed obsidian ore dictionary name
	 */
	public static final String DoubleCompressedObsidian = "compressedObsidian2";
	
	/**
	 * The ArrayList of mod registered items.
	 */
	public static ArrayList<Item> ModItems = new ArrayList<Item>();
	
	/**
	 * The ArrayList of mod registered blocks.
	 */
	public static ArrayList<Block> ModBlocks = new ArrayList<Block>();
	
	/**
	 * The hashmap of mod guis.
	 */
	public static HashMap<Integer, Class> ModGuis = new HashMap<Integer, Class>();

	/**
	 * The object which contains all recipes for this mod.
	 */
	public static HashMap<String, Tuple2<Boolean, ArrayList<IRecipe>>> SavedRecipes = new HashMap<String, Tuple2<Boolean, ArrayList<IRecipe>>>();
	
	/**
	 * The identifier for the WareHouse GUI.
	 */
	public static final int GuiWareHouse = 1;
	
	/**
	 * The identifier for the Chicken coop GUI.
	 */
	public static final int GuiChickenCoop = 2;
	
	/**
	 * The identifier for the Produce Farm GUI.
	 */
	public static final int GuiProduceFarm = 3;
	
	/**
	 * The identifier for the Tree Farm GUI.
	 */
	public static final int GuiTreeFarm = 4;
	
	/**
	 * The identifier for the FishPond GUI.
	 */
	public static final int GuiFishPond = 5;
	
	/**
	 * The identifier for the Starting House GUI.
	 */
	public static final int GuiStartHouseChooser = 6;
	
	/**
	 * The identifier for the Advanced WareHouse GUI.
	 */
	public static final int GuiAdvancedWareHouse = 7;
	
	/**
	 * The identifier for the Monster Masher GUI.
	 */
	public static final int GuiMonsterMasher = 8;
	
	/**
	 * The identifier for the Horse Stable GUI.
	 */
	public static final int GuiHorseStable = 9;
	
	/**
	 * The identifier for the Nether Gate GUI.
	 */
	public static final int GuiNetherGate = 10;
	
	/**
	 * The identifier for the Modular House GUI.
	 */
	public static final int GuiModularHouse = 11;
	
	/**
	 * The identifier for the Drafter GUI.
	 */
	public static final int GuiDrafter = 12;
	
	/**
	 * The identifier for the Basic structure GUI.
	 */
	public static final int GuiBasicStructure = 13;
	
	/**
	 * The identifier for the Villaer Houses GUI.
	 */
	public static final int GuiVillagerHouses = 14;
	
	/**
	 * THe identifier for the moderate house GUI.
	 */
	public static final int GuiModerateHouse = 15;
	
	/**
	 * This capability is used to save the locations where a player spawns when transferring dimensions.
	 */
	@CapabilityInject(IStructureConfigurationCapability.class)
	public static Capability<IStructureConfigurationCapability> StructureConfiguration = null;
	
	/**
	 * The starting house registered item.
	 * @return An instance of {@link ItemStartHouse}.
	 */
	public static ItemStartHouse StartHouse()
	{
		return ModRegistry.GetItem(ItemStartHouse.class);
	}
	
	/**
	 * The warehouse registered item.
	 * @return An instance of {@link ItemWareHouse}.
	 */
	public static ItemWareHouse WareHouse()
	{
		return ModRegistry.GetItem(ItemWareHouse.class);
	}
	
	/**
	 * The advanced ware house registered item.
	 * @return An instance of {@link ItemAdvancedWareHouse}.
	 */
	public static ItemAdvancedWareHouse AdvancedWareHouse()
	{
		return ModRegistry.GetItem(ItemAdvancedWareHouse.class);
	}
	
	/**
	 * The chicken coop registered item.
	 * @return An instance of {@link ItemAdvancedWareHouse}.
	 */
	public static ItemChickenCoop ChickenCoop()
	{
		return ModRegistry.GetItem(ItemChickenCoop.class);
	}
	
	/**
	 * The CompressedStone registered item.
	 * @return An instance of {@link ItemBlockMeta}.
	 */
	public static ItemBlockMeta CompressedStoneItem()
	{
		return ModRegistry.GetItem(ItemBlockMeta.class);
	}
	
	/**
	 * The CompressedStone registered Block.
	 * @return An instance of {@link BlockCompressedStone}.
	 */
	public static BlockCompressedStone CompressedStoneBlock()
	{
		return ModRegistry.GetBlock(BlockCompressedStone.class);
	}
	
	/**
	 * The Compressed Chest registered item.
	 * @return An instance of {@link ItemCompressedChest}.
	 */
	public static ItemCompressedChest CompressedChestItem() 
	{
		return ModRegistry.GetItem(ItemCompressedChest.class);
	}
	
	/**
	 * The Produce Farm registered item.
	 * @return An instance of {@link ItemProduceFarm}.
	 */
	public static ItemProduceFarm ProduceFarm() 
	{
		return ModRegistry.GetItem(ItemProduceFarm.class);
	}
	
	/**
	 * The Tree Farm registered item.
	 * @return An instance of {@link ItemTreeFarm}.
	 */
	public static ItemTreeFarm TreeFarm() 
	{
		return ModRegistry.GetItem(ItemTreeFarm.class);
	}
	
	/**
	 * The Fish Pond registered item.
	 * @return An instance of {@link ItemFishPond}.
	 */
	public static ItemFishPond FishPond()
	{
		return ModRegistry.GetItem(ItemFishPond.class);
	}
	
	/**
	 * The Pile of Bricks registered item.
	 * @return An instance of {@link ItemPileOfBricks}.
	 */
	public static ItemPileOfBricks PileOfBricks() 
	{
		return ModRegistry.GetItem(ItemPileOfBricks.class);
	}
	
	/**
	 * The Pallet of Bricks registered item.
	 * @return An instance of {@link ItemPalletOfBricks}.
	 */
	public static ItemPalletOfBricks PalletOfBricks()
	{
		return ModRegistry.GetItem(ItemPalletOfBricks.class);
	}
	
	/**
	 * The Monster Masher registered item.
	 * @return An instance of {@link ItemMonsterMasher}.
	 */
	public static ItemMonsterMasher MonsterMasher()
	{
		return ModRegistry.GetItem(ItemMonsterMasher.class);
	}

	/**
	 * The Warehouse Upgrade registered item.
	 * @return An instance of {@link ItemWarehouseUpgrade}.
	 */
	public static ItemWarehouseUpgrade WareHouseUpgrade()
	{
		return ModRegistry.GetItem(ItemWarehouseUpgrade.class);
	}
	
	/**
	 * The Bundle of Timber registered item.
	 * @return An instance of {@link ItemBundleOfTimber}.
	 */
	public static ItemBundleOfTimber BundleOfTimber()
	{
		return ModRegistry.GetItem(ItemBundleOfTimber.class);
	}
	
	/**
	 * The Horse Stable registered item.
	 * @return An instance of {@link ItemHorseStable}.
	 */
	public static ItemHorseStable HorseStable()
	{
		return ModRegistry.GetItem(ItemHorseStable.class);
	}
	
	/**
	 * The Nether Gate registered item.
	 * @return An instance of {@link ItemNetherGate}.
	 */
	public static ItemNetherGate NetherGate()
	{
		return ModRegistry.GetItem(ItemNetherGate.class);
	}
	
	/**
	 * The Modular House registered item.
	 * @return An instance of {@link ItemModularHouse}.
	 */
	public static ItemModularHouse ModularHouse()
	{
		return ModRegistry.GetItem(ItemModularHouse.class);
	}
	
	/**
	 * The Drafter registered block.
	 * @return An instance of {@link BlockDrafter}.
	 */
	public static BlockDrafter Drafter()
	{
		return ModRegistry.GetBlock(BlockDrafter.class);
	}
	
	/**
	 * The Basic Structure registered item.
	 * @return An instance of {@link ItemBasicStructure}.
	 */
	public static ItemBasicStructure BasicStructure()
	{
		return ModRegistry.GetItem(ItemBasicStructure.class);
	}
	
	/**
	 * The Instant Bridge registered item.
	 * @return An instance of {@link ItemInstantBridge}.
	 */
	public static ItemInstantBridge InstantBridge()
	{
		return ModRegistry.GetItem(ItemInstantBridge.class);
	}
	
	/**
	 * This method is used to get an ItemStack for compressed stone.
	 * @param enumType The type of compressed stone.
	 * @return An item stack with the appropriate meta data with 1 item in the stack
	 */
	public static ItemStack GetCompressedStoneType(BlockCompressedStone.EnumType enumType)
	{
		return ModRegistry.GetCompressedStoneType(enumType, 1);
	}
	
	/**
	 * This method is used to get an ItemStack for compressed stone.
	 * @param enumType The type of compressed stone.
	 * @param count The number to have in the returned stack.
	 * @return An item stack with the appropriate meta data with 1 item in the stack
	 */
	public static ItemStack GetCompressedStoneType(BlockCompressedStone.EnumType enumType, int count)
	{
		return new ItemStack(Item.getItemFromBlock(ModRegistry.CompressedStoneBlock()), count, enumType.getMetadata());
	}
	
	/**
	 * The Paper Lantern registered block.
	 * @return An instance of {@link BlockPaperLantern}.
	 */
	public static BlockPaperLantern PaperLantern()
	{
		return ModRegistry.GetBlock(BlockPaperLantern.class);
	}
	
	/**
	 * The String of Lanterns registered item.
	 * @return An instance of {@link ItemStringOfLanterns}.
	 */
	public static ItemStringOfLanterns StringOfLanterns()
	{
		return ModRegistry.GetItem(ItemStringOfLanterns.class);
	}

	/**
	 * The Coil of Lanterns registered item.
	 * @return An instance of {@link ItemCoilOfLanterns}.
	 */
	public static ItemCoilOfLanterns CoilOfLanterns()
	{
		return ModRegistry.GetItem(ItemCoilOfLanterns.class);
	}
	
	/**
	 * The {@link ItemModerateHouse} registered item.
	 * @return An instance of the registered item.
	 */
	public static ItemModerateHouse ModerateHouse()
	{
		return ModRegistry.GetItem(ItemModerateHouse.class);
	}
	
	/**
	 * The Compressed Obsidian registered block.
	 * @return An instance of {@link CompressedObsidianBlock}.
	 */
	public static BlockCompressedObsidian CompressedObsidianBlock()
	{
		return ModRegistry.GetBlock(BlockCompressedObsidian.class);
	}
	
	/**
	 * The Villager Houses registered item.
	 * @return An instance of {@link ItemVillagerHouses}.
	 */
	public static ItemVillagerHouses VillagerHouses()
	{
		return ModRegistry.GetItem(ItemVillagerHouses.class);
	}

	/**
	 * The Phasing Block registered Block.
	 * @return An instance of {@link BlockPhasing}.
	 */
	public static BlockPhasing PhasingBlock()
	{
		return ModRegistry.GetBlock(BlockPhasing.class);
	}
	
	/**
	 * The Glass Stairs registered Block.
	 * @return An instance of {@link BlockGlassStairs}.
	 */
	public static BlockGlassStairs GlassStairs()
	{
		return ModRegistry.GetBlock(BlockGlassStairs.class);
	}
	
	/**
	 * The Andesite Stairs registered Block.
	 * @return An instance of {@link BlockAndesiteStairs}.
	 */
	public static BlockAndesiteStairs AndesiteStairs()
	{
		return ModRegistry.GetBlock(BlockAndesiteStairs.class);
	}
	
	/**
	 * The Diorite Stairs registered Block.
	 * @return An instance of {@link BlockDioriteStairs}.
	 */
	public static BlockDioriteStairs DioriteStairs()
	{
		return ModRegistry.GetBlock(BlockDioriteStairs.class);
	}
	
	/**
	 * The Granite Stairs registered Block.
	 * @return An instance of {@link BlockGraniteStairs}.
	 */
	public static BlockGraniteStairs GraniteStairs()
	{
		return ModRegistry.GetBlock(BlockGraniteStairs.class);
	}
	
	/**
	 * The Glass Slab registered Block.
	 * @return An instance of {@link BlockHalfGlassSlab}.
	 */
	public static BlockHalfGlassSlab GlassSlab()
	{
		return ModRegistry.GetBlock(BlockHalfGlassSlab.class);
	}
	
	/**
	 * The Andesite Slab registered Block.
	 * @return An instance of {@link BlockHalfAndesiteSlab}.
	 */
	public static BlockHalfAndesiteSlab AndesiteSlab()
	{
		return ModRegistry.GetBlock(BlockHalfAndesiteSlab.class);
	}
	
	/**
	 * The Diorite Slab registered Block.
	 * @return An instance of {@link BlockHalfDioriteSlab}.
	 */
	public static BlockHalfDioriteSlab DioriteSlab()
	{
		return ModRegistry.GetBlock(BlockHalfDioriteSlab.class);
	}
	
	/**
	 * The Granite Slab registered Block.
	 * @return An instance of {@link BlockHalfGraniteSlab}.
	 */
	public static BlockHalfGraniteSlab GraniteSlab()
	{
		return ModRegistry.GetBlock(BlockHalfGraniteSlab.class);
	}
	
	/**
	 * The Boundary Block registered Block.
	 * @return An instance of {@link BlockBoundary}.
	 */
	public static BlockBoundary BoundaryBlock()
	{
		return ModRegistry.GetBlock(BlockBoundary.class);
	}
	
	/**
	 * Static constructor for the mod registry.
	 */
	static
	{
		ModRegistry.RegisterModComponents();
	}
	
	/**
	 * Gets the item from the ModItems collections.
	 * @param <T> The type which extends item.
	 * @param genericClass The class of item to get from the collection.
	 * @return Null if the item could not be found otherwise the item found.
	 */
	public static <T extends Item> T GetItem(Class<T> genericClass)
	{
		for (Item entry : ModRegistry.ModItems)
		{
			if (entry.getClass() == genericClass)
			{
				return (T)entry;
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the block from the ModBlockss collections.
	 * @param <T> The type which extends Block.
	 * @param genericClass The class of block to get from the collection.
	 * @return Null if the block could not be found otherwise the block found.
	 */
	public static <T extends Block> T GetBlock(Class<T> genericClass)
	{
		for (Block entry : ModRegistry.ModBlocks)
		{
			if (entry.getClass() == genericClass)
			{
				return (T)entry;
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the gui screen for the ID and passes position data to it.
	 * @param id The ID of the screen to get.
	 * @param x The X-Axis of where this screen was created from, this is used to create a BlockPos.
	 * @param y The Y-Axis of where this screen was created from, this is used to create a BlockPos.
	 * @param z The Z-Axis of where this screen was created from, this is used to create a BlockPos.
	 * @return Null if the screen wasn't found, otherwise the screen found.
	 */
	public static GuiScreen GetModGuiByID(int id, int x, int y, int z)
	{
		for (Entry<Integer, Class> entry : ModRegistry.ModGuis.entrySet())
		{
			if (entry.getKey() == id)
			{
				try
				{
					return (GuiScreen)entry.getValue().getConstructor(int.class, int.class, int.class).newInstance(x, y, z);
				}
				catch (InstantiationException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IllegalArgumentException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InvocationTargetException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (NoSuchMethodException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (SecurityException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * This is where all in-game mod components (Items, Blocks) will be registered.
	 */
	public static void RegisterModComponents()
	{
		ModRegistry.registerItem(new ItemStartHouse("item_start_house"));
		ModRegistry.registerItem(new ItemWareHouse("item_warehouse"));
		ModRegistry.registerItem(new ItemChickenCoop("item_chicken_coop"));
		ModRegistry.registerItem(new ItemProduceFarm("item_produce_farm"));
		ModRegistry.registerItem(new ItemTreeFarm("item_tree_farm"));
		ModRegistry.registerItem(new ItemCompressedChest("item_compressed_chest"));
		ModRegistry.registerItem(new ItemPileOfBricks("item_pile_of_bricks"));
		ModRegistry.registerItem(new ItemPalletOfBricks("item_pallet_of_bricks"));
		ModRegistry.registerItem(new ItemFishPond("item_fish_pond"));
		ModRegistry.registerItem(new ItemAdvancedWareHouse("item_advanced_warehouse"));
		ModRegistry.registerItem(new ItemMonsterMasher("item_monster_masher"));
		ModRegistry.registerItem(new ItemWarehouseUpgrade("item_warehouse_upgrade"));
		ModRegistry.registerItem(new ItemBundleOfTimber("item_bundle_of_timber"));
		ModRegistry.registerItem(new ItemHorseStable("item_horse_stable"));
		ModRegistry.registerItem(new ItemNetherGate("item_nether_gate"));
		ModRegistry.registerItem(new ItemInstantBridge("item_instant_bridge"));
		//ModRegistry.registerItem(new ItemModularHouse("item_modular_house"));
		ModRegistry.registerItem(new ItemStringOfLanterns("item_string_of_lanterns"));
		ModRegistry.registerItem(new ItemCoilOfLanterns("item_coil_of_lanterns"));
		ModRegistry.registerItem(new ItemModerateHouse("item_moderate_house"));
		//ModRegistry.registerItem(new ItemBogus("item_bogus"));
		
		// Register all the basic structures here. The resource location is used for the item models and textures.
		// Only the first one in this list should have the last variable set to true.
		ModRegistry.registerItem(new ItemBasicStructure("item_basic_structure"));
		
		// Create/register the item block with this block as it's needed due to this being a meta data block.
		BlockCompressedStone stone = new BlockCompressedStone();
		ItemBlockMeta meta = new ItemBlockMeta(stone);
		ModRegistry.setItemName(meta, "block_compressed_stone");
		ModRegistry.registerBlock(stone, meta);
		
		ModRegistry.registerBlock(new BlockPaperLantern("block_paper_lantern"));
		
		BlockCompressedObsidian obsidian = new BlockCompressedObsidian();
		ItemBlockMeta metaObsidian = new ItemBlockMeta(obsidian);
		ModRegistry.setItemName(metaObsidian, "block_compressed_obsidian");
		ModRegistry.registerBlock(obsidian, metaObsidian);
		
		ModRegistry.registerItem(new ItemVillagerHouses("item_villager_houses"));
		
		ModRegistry.registerBlock(new BlockPhasing("block_phasing"));
		
		ModRegistry.registerBlock(new BlockBoundary("block_boundary"));
		
		ModRegistry.registerBlock(new BlockGlassStairs("block_glass_stairs"));
		
		ModRegistry.registerBlock(new BlockAndesiteStairs("block_andesite_stairs"));
		
		ModRegistry.registerBlock(new BlockDioriteStairs("block_diorite_stairs"));
		
		ModRegistry.registerBlock(new BlockGraniteStairs("block_granite_stairs"));
		
		// Glass Slab.
		BlockHalfGlassSlab registeredHalfGlassBlock = new BlockHalfGlassSlab();
		BlockDoubleGlassSlab registeredDoubleGlassSlab = new BlockDoubleGlassSlab();

		ItemBlockGlassSlab itemHalfGlassSlab = new ItemBlockGlassSlab(registeredHalfGlassBlock, registeredHalfGlassBlock, registeredDoubleGlassSlab, true);

		itemHalfGlassSlab = (ItemBlockGlassSlab) itemHalfGlassSlab.setRegistryName("block_half_glass_slab");

		ModRegistry.registerBlock(registeredHalfGlassBlock, itemHalfGlassSlab);
		ModRegistry.registerBlock(registeredDoubleGlassSlab, false);
		
		// Andesite slab.
		BlockHalfAndesiteSlab registeredHalfAndesiteBlock = new BlockHalfAndesiteSlab();
		BlockDoubleAndesiteSlab registeredDoubleAndesiteSlab = new BlockDoubleAndesiteSlab();

		ItemBlockAndesiteSlab itemHalfAndesiteSlab = new ItemBlockAndesiteSlab(registeredHalfAndesiteBlock, registeredHalfAndesiteBlock, registeredDoubleAndesiteSlab, true);

		itemHalfAndesiteSlab = (ItemBlockAndesiteSlab) itemHalfAndesiteSlab.setRegistryName("block_half_andesite_slab");

		ModRegistry.registerBlock(registeredHalfAndesiteBlock, itemHalfAndesiteSlab);
		ModRegistry.registerBlock(registeredDoubleAndesiteSlab, false);
		
		// Diorite slab.
		BlockHalfDioriteSlab registeredHalfDioriteBlock = new BlockHalfDioriteSlab();
		BlockDoubleDioriteSlab registeredDoubleDioriteSlab = new BlockDoubleDioriteSlab();

		ItemBlockDioriteSlab itemHalfDioriteSlab = new ItemBlockDioriteSlab(registeredHalfDioriteBlock, registeredHalfDioriteBlock, registeredDoubleDioriteSlab, true);

		itemHalfDioriteSlab = (ItemBlockDioriteSlab) itemHalfDioriteSlab.setRegistryName("block_half_diorite_slab");

		ModRegistry.registerBlock(registeredHalfDioriteBlock, itemHalfDioriteSlab);
		ModRegistry.registerBlock(registeredDoubleDioriteSlab, false);
		
		// Granite slab.
		BlockHalfGraniteSlab registeredHalfGraniteBlock = new BlockHalfGraniteSlab();
		BlockDoubleGraniteSlab registeredDoubleGraniteSlab = new BlockDoubleGraniteSlab();

		ItemBlockGraniteSlab itemHalfGraniteSlab = new ItemBlockGraniteSlab(registeredHalfGraniteBlock, registeredHalfGraniteBlock, registeredDoubleGraniteSlab, true);

		itemHalfGraniteSlab = (ItemBlockGraniteSlab) itemHalfGraniteSlab.setRegistryName("block_half_granite_slab");

		ModRegistry.registerBlock(registeredHalfGraniteBlock, itemHalfGraniteSlab);
		ModRegistry.registerBlock(registeredDoubleGraniteSlab, false);
		
		Blocks.STRUCTURE_BLOCK.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		
		//BlockDrafter drafter = new BlockDrafter();
		//ModRegistry.registerBlock(drafter);
		//GameRegistry.registerTileEntity(TileEntityDrafter.class, "Drafter");
	}
	
	/**
	 * Registers records into the ore dictionary.
	 */
	public static void RegisterOreDictionaryRecords()
	{
		// Register certain blocks into the ore dictionary.
		OreDictionary.registerOre("compressedDirt1", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.COMPRESSED_DIRT));
		OreDictionary.registerOre("compressedDirt2", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.DOUBLE_COMPRESSED_DIRT));
		OreDictionary.registerOre("compressedStone1", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.COMPRESSED_STONE));
		OreDictionary.registerOre("compressedStone2", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.DOUBLE_COMPRESSED_STONE));
		OreDictionary.registerOre("compressedStone3", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.TRIPLE_COMPRESSED_STONE));
		OreDictionary.registerOre("compressedGlowstone1", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.COMPRESSED_GLOWSTONE));
		OreDictionary.registerOre("compressedGlowstone2", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.DOUBLE_COMPRESSED_GLOWSTONE));
		OreDictionary.registerOre(
				"compressedObsidian1", 
				new ItemStack(Item.getItemFromBlock(ModRegistry.CompressedObsidianBlock()), 1, BlockCompressedObsidian.EnumType.COMPRESSED_OBSIDIAN.getMetadata()));
		OreDictionary.registerOre(
				"compressedObsidian2", 
				new ItemStack(Item.getItemFromBlock(ModRegistry.CompressedObsidianBlock()), 1, BlockCompressedObsidian.EnumType.DOUBLE_COMPRESSED_OBSIDIAN.getMetadata()));
	}
	
	/**
	 * This is where the mod recipes are registered.
	 */
	public static void RegisterRecipes()
	{
		// Compressed Stone.
		ModRegistry.addShapedRecipe("Compressed Stone", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.COMPRESSED_STONE),
				"xxx",
				"xxx",
				"xxx",
				'x', new ItemStack(Blocks.STONE, 1, OreDictionary.WILDCARD_VALUE));
		
		ModRegistry.addShapedRecipe("Compressed Stone", new ItemStack(Item.getItemFromBlock(Blocks.STONE), 9), 
				"x",
				'x', ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.COMPRESSED_STONE));
		
		// Double Compressed Stone.
		ModRegistry.addShapedRecipe("Compressed Stone", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.DOUBLE_COMPRESSED_STONE),
				"xxx",
				"xxx",
				"xxx",
				'x', ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.COMPRESSED_STONE));
		
		ModRegistry.addShapedRecipe("Compressed Stone", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.COMPRESSED_STONE, 9),
				"x",
				'x',ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.DOUBLE_COMPRESSED_STONE));
		
		// Triple Compressed Stone.
		ModRegistry.addShapedRecipe("Compressed Stone", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.TRIPLE_COMPRESSED_STONE),
				"xxx",
				"xxx",
				"xxx",
				'x', ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.DOUBLE_COMPRESSED_STONE));
		
		ModRegistry.addShapedRecipe("Compressed Stone", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.DOUBLE_COMPRESSED_STONE, 9),
				"x",
				'x', ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.TRIPLE_COMPRESSED_STONE));
		
		// Compressed Glowstone.
		ModRegistry.addShapedRecipe("Compressed Glowstone", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.COMPRESSED_GLOWSTONE),
				"xxx",
				"xxx",
				"xxx",
				'x', Item.getItemFromBlock(Blocks.GLOWSTONE));
		
		ModRegistry.addShapedRecipe("Compressed Glowstone", new ItemStack(Item.getItemFromBlock(Blocks.GLOWSTONE), 9),
				"x",
				'x', ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.COMPRESSED_GLOWSTONE));
		
		// Double Compressed Glowstone.
		ModRegistry.addShapedRecipe("Compressed Glowstone", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.DOUBLE_COMPRESSED_GLOWSTONE),
				"xxx",
				"xxx",
				"xxx",
				'x', ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.COMPRESSED_GLOWSTONE));
		
		ModRegistry.addShapedRecipe("Compressed Glowstone", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.COMPRESSED_GLOWSTONE, 9),
				"x",
				'x', ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.DOUBLE_COMPRESSED_GLOWSTONE));
		
		// Compressed Dirt
		ModRegistry.addShapedRecipe("Compressed Dirt", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.COMPRESSED_DIRT),
				"xxx",
				"xxx",
				"xxx",
				'x', Item.getItemFromBlock(Blocks.DIRT));
		
		ModRegistry.addShapedRecipe("Compressed Dirt", new ItemStack(Item.getItemFromBlock(Blocks.DIRT), 9),
				"x",
				'x', ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.COMPRESSED_DIRT));
		
		// Double Compressed Dirt
		ModRegistry.addShapedRecipe("Compressed Dirt", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.DOUBLE_COMPRESSED_DIRT),
				"xxx",
				"xxx",
				"xxx",
				'x', ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.COMPRESSED_DIRT));
		
		ModRegistry.addShapedRecipe("Compressed Dirt", ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.COMPRESSED_DIRT, 9),
				"x",
				'x', ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.DOUBLE_COMPRESSED_DIRT));
		
		// Compressed Chest
		ModRegistry.addShapedRecipe("Compressed Chest", new ItemStack(ModRegistry.CompressedChestItem()),
				"xxx",
				"xyx",
				"xxx",
				'x', Item.getItemFromBlock(Blocks.CHEST),
				'y', Item.getItemFromBlock(Blocks.ENDER_CHEST));
		
		// Pile of Bricks
		ModRegistry.addShapedRecipe("Pile of Bricks", new ItemStack(ModRegistry.PileOfBricks()),
				"xxx",
				"xxx",
				"xxx",
				'x', Items.BRICK);
		
		ModRegistry.addShapedRecipe("Pile of Bricks", new ItemStack(Items.BRICK, 9),
				"x",
				'x', ModRegistry.PileOfBricks());
		
		// Pallet of Bricks
		ModRegistry.addShapedRecipe("Pile of Bricks", new ItemStack(ModRegistry.PalletOfBricks()),
				"xxx",
				"xxx",
				"xxx",
				'x', ModRegistry.PileOfBricks());
		
		ModRegistry.addShapedRecipe("Pile of Bricks", new ItemStack(ModRegistry.PileOfBricks(), 9),
				"x",
				'x', ModRegistry.PalletOfBricks());
		
		// Warehouse
		ModRegistry.addShapedOreRecipe("Warehouse", new ItemStack(ModRegistry.WareHouse()),
				"x x",
				"xyx",
				"zaz",
				'x', ModRegistry.DoubleCompressedStone,
				'y', ModRegistry.CompressedChestItem(),
				'z', ModRegistry.DoubleCompressedGlowstone,
				'a', ModRegistry.TripleCompressedStone);
		
		ModRegistry.addShapedOreRecipe("Warehouse", new ItemStack(ModRegistry.WareHouse()),
				"x x",
				"xyx",
				"zaz",
				'x', ModRegistry.DoubleCompressedStone,
				'y', ModRegistry.CompressedChestItem(),
				'z', ModRegistry.CoilOfLanterns(),
				'a', ModRegistry.TripleCompressedStone);
		
		// Produce Farm.
		ModRegistry.addShapedOreRecipe("Produce Farm", new ItemStack(ModRegistry.ProduceFarm()),
				"aba",
				"cdc",
				"aba",
				'a', ModRegistry.PalletOfBricks(),
				'b', ModRegistry.DoubleCompressedDirt,
				'c', Items.WATER_BUCKET,
				'd', ModRegistry.DoubleCompressedGlowstone);
		
		ModRegistry.addShapedOreRecipe("Produce Farm", new ItemStack(ModRegistry.ProduceFarm()),
				"aba",
				"cdc",
				"aba",
				'a', ModRegistry.PalletOfBricks(),
				'b', ModRegistry.DoubleCompressedDirt,
				'c', Items.WATER_BUCKET,
				'd', ModRegistry.CoilOfLanterns());
		
		// Tree Farm/Park
		ModRegistry.addShapedOreRecipe("Tree Farm", new ItemStack(ModRegistry.TreeFarm()),
				"aba",
				"cdc",
				"aba",
				'a', ModRegistry.PalletOfBricks(),
				'b', ModRegistry.DoubleCompressedDirt,
				'c', Items.FLOWER_POT,
				'd', ModRegistry.DoubleCompressedGlowstone);
		
		ModRegistry.addShapedOreRecipe("Tree Farm", new ItemStack(ModRegistry.TreeFarm()),
				"aba",
				"cdc",
				"aba",
				'a', ModRegistry.PalletOfBricks(),
				'b', ModRegistry.DoubleCompressedDirt,
				'c', Items.FLOWER_POT,
				'd', ModRegistry.CoilOfLanterns());
		
		// Chicken Coop
		ModRegistry.addShapedOreRecipe("Chicken Coop", new ItemStack(ModRegistry.ChickenCoop()),
				"eee",
				"aba",
				"cdc",
				'a', new ItemStack(Item.getItemFromBlock(Blocks.LOG), 1, BlockPlanks.EnumType.SPRUCE.getMetadata()),
				'b', Items.EGG,
				'c', ModRegistry.CompressedStone,
				'd', Item.getItemFromBlock(Blocks.HAY_BLOCK),
				'e', Item.getItemFromBlock(Blocks.BRICK_BLOCK));
		
		// Fish farm.
		ModRegistry.addShapedRecipe("Fish Farm", new ItemStack(ModRegistry.FishPond()),
				"abc",
				"ded",
				"fgf",
				'a', new ItemStack(Item.getItemFromBlock(Blocks.TALLGRASS), 1, 1),
				'b', new ItemStack(Items.REEDS),
				'c', new ItemStack(Item.getItemFromBlock(Blocks.SAPLING), 1, 0),
				'd', new ItemStack(Items.WATER_BUCKET),
				'e', new ItemStack(Items.FISHING_ROD, 1, 0),
				'f', new ItemStack(Item.getItemFromBlock(Blocks.SAND)),
				'g', new ItemStack(Items.FISH, 1, OreDictionary.WILDCARD_VALUE));
		
		// Warehouse Upgrade
		ModRegistry.addShapedRecipe("Warehouse Upgrade", new ItemStack(ModRegistry.WareHouseUpgrade()),
				"aba",
				"cdc",
				"aba",
				'a', Item.getItemFromBlock(Blocks.BOOKSHELF),
				'b', Items.BREWING_STAND,
				'c', Item.getItemFromBlock(Blocks.ENCHANTING_TABLE),
				'd', Item.getItemFromBlock(Blocks.ANVIL));
		
		// Advanced Warehouse
		ModRegistry.addShapedOreRecipe("Advanced Warehouse", new ItemStack(ModRegistry.AdvancedWareHouse()),
				"xbx",
				"xyx",
				"zaz",
				'x', ModRegistry.DoubleCompressedStone,
				'y', ModRegistry.CompressedChestItem(),
				'z', ModRegistry.DoubleCompressedGlowstone,
				'a', ModRegistry.TripleCompressedStone,
				'b', ModRegistry.WareHouseUpgrade());
		
		ModRegistry.addShapedOreRecipe("Advanced Warehouse", new ItemStack(ModRegistry.AdvancedWareHouse()),
				"xbx",
				"xyx",
				"zaz",
				'x', ModRegistry.DoubleCompressedStone,
				'y', ModRegistry.CompressedChestItem(),
				'z', ModRegistry.CoilOfLanterns(),
				'a', ModRegistry.TripleCompressedStone,
				'b', ModRegistry.WareHouseUpgrade());
		
		// Monster Masher.
		ModRegistry.addShapedOreRecipe("Monster Masher", new ItemStack(ModRegistry.MonsterMasher()),
				"abc",
				"ede",
				"fgh",
				'a', new ItemStack(Items.SKULL, 1, 2),
				'b', Item.getItemFromBlock(Blocks.REDSTONE_BLOCK),
				'c', new ItemStack(Items.SKULL, 1, 0),
				'e', Item.getItemFromBlock(Blocks.IRON_BARS),
				'd', ModRegistry.CompressedChestItem(),
				'f', ModRegistry.DoubleCompressedStone,
				'g', ModRegistry.TripleCompressedStone,
				'h', ModRegistry.DoubleCompressedGlowstone);

		// Planks to addShapedRecipe of timber.
		ModRegistry.addShapedOreRecipe("Bundle of Timber", new ItemStack(ModRegistry.BundleOfTimber()), 
				"aaa", 
				"aaa", 
				"aaa", 
				'a', "logWood");

		// Bundle of timber to oak planks.
		ModRegistry.addShapedRecipe("Bundle of Timber", new ItemStack(Item.getItemFromBlock(Blocks.LOG), 9), 
				"a",
				'a', ModRegistry.BundleOfTimber());
		
		// Horse Stable.
		ModRegistry.addShapedRecipe("Horse Stable", new ItemStack(ModRegistry.HorseStable()),
				"aaa",
				"bcb",
				"ded",
				'a', Item.getItemFromBlock(Blocks.BRICK_BLOCK),
				'b', ModRegistry.BundleOfTimber(),
				'c', Item.getItemFromBlock(Blocks.HAY_BLOCK),
				'd', Item.getItemFromBlock(Blocks.SPRUCE_FENCE),
				'e', Item.getItemFromBlock(Blocks.SPRUCE_FENCE_GATE));
		
		ModRegistry.addShapedRecipe("Horse Stable", new ItemStack(ModRegistry.HorseStable()),
				"aaa",
				"bcb",
				"ded",
				'a', Item.getItemFromBlock(Blocks.BRICK_BLOCK),
				'b', ModRegistry.BundleOfTimber(),
				'c', Item.getItemFromBlock(Blocks.HAY_BLOCK),
				'd', Item.getItemFromBlock(Blocks.OAK_FENCE),
				'e', Item.getItemFromBlock(Blocks.OAK_FENCE_GATE));
		
		ModRegistry.addShapedRecipe("Horse Stable", new ItemStack(ModRegistry.HorseStable()),
				"aaa",
				"bcb",
				"ded",
				'a', Item.getItemFromBlock(Blocks.BRICK_BLOCK),
				'b', ModRegistry.BundleOfTimber(),
				'c', Item.getItemFromBlock(Blocks.HAY_BLOCK),
				'd', Item.getItemFromBlock(Blocks.BIRCH_FENCE),
				'e', Item.getItemFromBlock(Blocks.BIRCH_FENCE_GATE));
		
		ModRegistry.addShapedRecipe("Horse Stable", new ItemStack(ModRegistry.HorseStable()),
				"aaa",
				"bcb",
				"ded",
				'a', Item.getItemFromBlock(Blocks.BRICK_BLOCK),
				'b', ModRegistry.BundleOfTimber(),
				'c', Item.getItemFromBlock(Blocks.HAY_BLOCK),
				'd', Item.getItemFromBlock(Blocks.ACACIA_FENCE),
				'e', Item.getItemFromBlock(Blocks.ACACIA_FENCE_GATE));
		
		ModRegistry.addShapedRecipe("Horse Stable", new ItemStack(ModRegistry.HorseStable()),
				"aaa",
				"bcb",
				"ded",
				'a', Item.getItemFromBlock(Blocks.BRICK_BLOCK),
				'b', ModRegistry.BundleOfTimber(),
				'c', Item.getItemFromBlock(Blocks.HAY_BLOCK),
				'd', Item.getItemFromBlock(Blocks.JUNGLE_FENCE),
				'e', Item.getItemFromBlock(Blocks.JUNGLE_FENCE_GATE));
		
		ModRegistry.addShapedRecipe("Horse Stable", new ItemStack(ModRegistry.HorseStable()),
				"aaa",
				"bcb",
				"ded",
				'a', Item.getItemFromBlock(Blocks.BRICK_BLOCK),
				'b', ModRegistry.BundleOfTimber(),
				'c', Item.getItemFromBlock(Blocks.HAY_BLOCK),
				'd', Item.getItemFromBlock(Blocks.DARK_OAK_FENCE),
				'e', Item.getItemFromBlock(Blocks.DARK_OAK_FENCE_GATE));
		
		// Nether Gate
		ModRegistry.addShapedRecipe("Nether Gate", new ItemStack(ModRegistry.NetherGate()),
				"aba",
				"bcb",
				"aba",
				'a', ModRegistry.GetCompressedStoneType(BlockCompressedStone.EnumType.DOUBLE_COMPRESSED_STONE),
				'b', Item.getItemFromBlock(Blocks.OBSIDIAN),
				'c', Items.FLINT_AND_STEEL);
		
		// Advanced Chicken Coop
		ItemStack result = new ItemStack(ModRegistry.BasicStructure());
		IStructureConfigurationCapability capability = result.getCapability(ModRegistry.StructureConfiguration, EnumFacing.NORTH);
		capability.getConfiguration().basicStructureName = EnumBasicStructureName.AdavancedCoop;
		
		ModRegistry.addShapelessRecipe("Advanced Chicken Coop", result, ModRegistry.ChickenCoop(), ModRegistry.PalletOfBricks());
		
		// Advanced Horse Stable
		result = new ItemStack(ModRegistry.BasicStructure());
		capability = result.getCapability(ModRegistry.StructureConfiguration, EnumFacing.NORTH);
		capability.getConfiguration().basicStructureName = EnumBasicStructureName.AdvancedHorseStable;

		ModRegistry.addShapelessOreRecipe("Advanced Horse Stable", result, 
				ModRegistry.HorseStable(), ModRegistry.HorseStable(), ModRegistry.HorseStable(), ModRegistry.HorseStable(),
				ModRegistry.DoubleCompressedGlowstone);
		
		ModRegistry.addShapelessRecipe("Advanced Horse Stable", result, 
				ModRegistry.HorseStable(), ModRegistry.HorseStable(), ModRegistry.HorseStable(), ModRegistry.HorseStable(),
				ModRegistry.CoilOfLanterns());
		
		// Barn
		result = new ItemStack(ModRegistry.BasicStructure());
		capability = result.getCapability(ModRegistry.StructureConfiguration, EnumFacing.NORTH);
		capability.getConfiguration().basicStructureName = EnumBasicStructureName.Barn;
		
		ModRegistry.addShapedOreRecipe("Barn", result,
				"aba",
				"ccc",
				"cdc",
				'a', ModRegistry.PalletOfBricks(),
				'b', ModRegistry.CompressedGlowstone,
				'c', ModRegistry.BundleOfTimber(),
				'd', ModRegistry.DoubleCompressedDirt);
		
		// Machinery Tower
		result = new ItemStack(ModRegistry.BasicStructure());
		capability = result.getCapability(ModRegistry.StructureConfiguration, EnumFacing.NORTH);
		capability.getConfiguration().basicStructureName = EnumBasicStructureName.MachineryTower;
		
		ModRegistry.addShapedOreRecipe("Machinery Tower", result, 
				"aaa",
				" b ",
				" c ",
				'a', Item.getItemFromBlock(Blocks.GOLDEN_RAIL),
				'b', ModRegistry.StringOfLanterns(),
				'c', ModRegistry.TripleCompressedStone);
		
		// Defense Bunker
		result = new ItemStack(ModRegistry.BasicStructure());
		capability = result.getCapability(ModRegistry.StructureConfiguration, EnumFacing.NORTH);
		capability.getConfiguration().basicStructureName = EnumBasicStructureName.DefenseBunker;
		
		ModRegistry.addShapedOreRecipe("Defense Bunker", result, 
				"dad",
				"bcb",
				"ddd",
				'a', ModRegistry.CoilOfLanterns(),
				'b', ModRegistry.TripleCompressedStone,
				'c', ModRegistry.DoubleCompressedObsidian,
				'd', Item.getItemFromBlock(Blocks.IRON_BLOCK));
		
		// Mineshaft Entrance
		result = new ItemStack(ModRegistry.BasicStructure());
		capability = result.getCapability(ModRegistry.StructureConfiguration, EnumFacing.NORTH);
		capability.getConfiguration().basicStructureName = EnumBasicStructureName.MineshaftEntrance;
		
		ModRegistry.addShapedOreRecipe("Mineshaft Entrance", result, 
				"dad",
				"bcb",
				"aaa",
				'a', ModRegistry.BundleOfTimber(),
				'b', ModRegistry.CompressedStone,
				'c', Items.BED,
				'd', Items.IRON_PICKAXE);
		
		// Ender Gateway
		result = new ItemStack(ModRegistry.BasicStructure());
		capability = result.getCapability(ModRegistry.StructureConfiguration, EnumFacing.NORTH);
		capability.getConfiguration().basicStructureName = EnumBasicStructureName.EnderGateway;
		
		ModRegistry.addShapedOreRecipe("Ender Gateway", result, 
				"bdb",
				"dcd",
				"bab",
				'a', ModRegistry.TripleCompressedStone,
				'b', new ItemStack(Item.getItemFromBlock(Blocks.PRISMARINE), 1, 1),
				'c', ModRegistry.DoubleCompressedObsidian,
				'd', Item.getItemFromBlock(Blocks.QUARTZ_BLOCK));
		
		// Aqua base
		result = new ItemStack(ModRegistry.BasicStructure());
		capability = result.getCapability(ModRegistry.StructureConfiguration, EnumFacing.NORTH);
		capability.getConfiguration().basicStructureName = EnumBasicStructureName.AquaBase;
		
		ModRegistry.addShapedOreRecipe("Aqua Base", result, 
				"xxx",
				"yzy",
				"yay",
				'x', new ItemStack(Item.getItemFromBlock(Blocks.GLASS)),
				'y', ModRegistry.DoubleCompressedStone,
				'z', new ItemStack(Item.getItemFromBlock(Blocks.SEA_LANTERN)),
				'a', ModRegistry.CoilOfLanterns());
		
		// Grassy Plains
		result = new ItemStack(ModRegistry.BasicStructure());
		capability = result.getCapability(ModRegistry.StructureConfiguration, EnumFacing.NORTH);
		capability.getConfiguration().basicStructureName = EnumBasicStructureName.GrassyPlain;
		
		ModRegistry.addShapedRecipe("Grassy Plains", result, 
				"   ",
				"yyy",
				"xxx",
				'x', new ItemStack(Item.getItemFromBlock(Blocks.DIRT)),
				'y', new ItemStack(Item.getItemFromBlock(Blocks.TALLGRASS), 1, 1));
		
		// Magic Temple
		result = new ItemStack(ModRegistry.BasicStructure());
		capability = result.getCapability(ModRegistry.StructureConfiguration, EnumFacing.NORTH);
		capability.getConfiguration().basicStructureName = EnumBasicStructureName.MagicTemple;
		
		ModRegistry.addShapedRecipe("Magic Temple", result, 
				"aaa",
				"cbd",
				"aaa",
				'a', new ItemStack(ModRegistry.BundleOfTimber()),
				'b', new ItemStack(Item.getItemFromBlock(Blocks.ENCHANTING_TABLE)),
				'c', new ItemStack(Items.BREWING_STAND),
				'd', new ItemStack(Items.BLAZE_ROD));
		
		// Greenhouse
		result = new ItemStack(ModRegistry.BasicStructure());
		capability = result.getCapability(ModRegistry.StructureConfiguration, EnumFacing.NORTH);
		capability.getConfiguration().basicStructureName = EnumBasicStructureName.GreenHouse;
		
		ModRegistry.addShapedOreRecipe("Green House", result, 
				"bcb",
				"bab",
				"ded",
				'a', new ItemStack(Items.WATER_BUCKET),
				'b', ModRegistry.DoubleCompressedStone,
				'c', new ItemStack(Item.getItemFromBlock(Blocks.GLASS_PANE)),
				'd', new ItemStack(ModRegistry.StringOfLanterns()),
				'e', ModRegistry.DoubleCompressedDirt);
		
		// Watch tower
		result = new ItemStack(ModRegistry.BasicStructure());
		capability = result.getCapability(ModRegistry.StructureConfiguration, EnumFacing.NORTH);
		capability.getConfiguration().basicStructureName = EnumBasicStructureName.WatchTower;
		
		ModRegistry.addShapedOreRecipe("Watch Tower", result, 
				"aaa",
				" a ",
				" a ",
				'a', ModRegistry.CompressedStone);
		
		// Instant Bridge.
		ModRegistry.addShapedOreRecipe("Instant Bridge", 
				new ItemStack(ModRegistry.InstantBridge()),
				"bab",
				"bab",
				"bab",
				'a', ModRegistry.DoubleCompressedStone,
				'b', Item.getItemFromBlock(Blocks.TORCH));
		
		// Paper Lantern.
		ModRegistry.addShapelessRecipe("Paper Lantern", new ItemStack(Item.getItemFromBlock(ModRegistry.PaperLantern())), Items.STICK, Items.PAPER, Item.getItemFromBlock(Blocks.TORCH));
		
		// String of lanterns (compressed paper lanterns).
		ModRegistry.addShapedRecipe("Paper Lantern", 
				new ItemStack(ModRegistry.StringOfLanterns()),
				"aaa",
				"aaa",
				"aaa",
				'a', Item.getItemFromBlock(ModRegistry.PaperLantern()));
		
		ModRegistry.addShapelessRecipe("Paper Lantern", 
				new ItemStack(Item.getItemFromBlock(ModRegistry.PaperLantern()), 9),
				ModRegistry.StringOfLanterns());
		
		// Coil of lanterns (double compressed paper lanterns).
		ModRegistry.addShapedRecipe("Paper Lantern", 
				new ItemStack(ModRegistry.CoilOfLanterns()),
				"aaa",
				"aaa",
				"aaa",
				'a', ModRegistry.StringOfLanterns());
		
		ModRegistry.addShapelessRecipe("Paper Lantern", 
				new ItemStack(ModRegistry.StringOfLanterns(), 9), ModRegistry.CoilOfLanterns());
		
		// Compressed Obsidian.
		ModRegistry.addShapedRecipe("Compressed Obsidian", 
				new ItemStack(Item.getItemFromBlock(ModRegistry.CompressedObsidianBlock()), 1, BlockCompressedObsidian.EnumType.COMPRESSED_OBSIDIAN.getMetadata()),
				"aaa",
				"aaa",
				"aaa",
				'a', Item.getItemFromBlock(Blocks.OBSIDIAN));
		
		ModRegistry.addShapelessRecipe("Compressed Obsidian", 
				new ItemStack(Item.getItemFromBlock(Blocks.OBSIDIAN), 9), 
				new ItemStack(Item.getItemFromBlock(ModRegistry.CompressedObsidianBlock()), 1, BlockCompressedObsidian.EnumType.COMPRESSED_OBSIDIAN.getMetadata()));
		
		// Double compressed obsidian.
		ModRegistry.addShapedRecipe("Compressed Obsidian", 
				new ItemStack(Item.getItemFromBlock(ModRegistry.CompressedObsidianBlock()), 1, BlockCompressedObsidian.EnumType.DOUBLE_COMPRESSED_OBSIDIAN.getMetadata()),
				"aaa",
				"aaa",
				"aaa",
				'a', new ItemStack(Item.getItemFromBlock(ModRegistry.CompressedObsidianBlock()), 1, BlockCompressedObsidian.EnumType.COMPRESSED_OBSIDIAN.getMetadata()));
		
		ModRegistry.addShapelessRecipe("Compressed Obsidian", 
				new ItemStack(Item.getItemFromBlock(ModRegistry.CompressedObsidianBlock()), 9, BlockCompressedObsidian.EnumType.COMPRESSED_OBSIDIAN.getMetadata()), 
				new ItemStack(Item.getItemFromBlock(ModRegistry.CompressedObsidianBlock()), 1, BlockCompressedObsidian.EnumType.DOUBLE_COMPRESSED_OBSIDIAN.getMetadata()));
		
		// Villager Houses.
		ModRegistry.addShapedOreRecipe("Villager Houses", new ItemStack(ModRegistry.VillagerHouses()),
				"aaa",
				"aaa",
				"bbb",
				'a', ModRegistry.BundleOfTimber(),
				'b', ModRegistry.CompressedStone);
		
		// Phasic Block
		ModRegistry.addShapedRecipe("Phasic Block", new ItemStack(ModRegistry.PhasingBlock()),
				"aaa",
				"aba",
				"aaa",
				'a', Item.getItemFromBlock(Blocks.STONE),
				'b', Items.ENDER_PEARL);
		
		// Smart Glass
		ModRegistry.addShapedRecipe("Smart Glass",new ItemStack(ModRegistry.BoundaryBlock()),
				"aaa",
				"aba",
				"aaa",
				'a', Item.getItemFromBlock(Blocks.GLASS),
				'b', Items.ENDER_PEARL);
		
		// Starting House.
		ModRegistry.addShapedOreRecipe("Starting House",  new ItemStack(ModRegistry.StartHouse()), 
				"abc",
				"ded",
				"fff",
				'a', Item.getItemFromBlock(Blocks.CRAFTING_TABLE),
				'b', Items.CLOCK,
				'c', Item.getItemFromBlock(Blocks.FURNACE),
				'd', ModRegistry.CompressedStone,
				'e', Items.BED,
				'f', ModRegistry.BundleOfTimber());
		
		// Glass Stairs
		ModRegistry.addShapedRecipe("Glass Stairs", new ItemStack(ModRegistry.GlassStairs(), 4),
				"a  ",
				"aa ",
				"aaa",
				'a', Item.getItemFromBlock(Blocks.GLASS));
		
		// Andesite Stairs
		ModRegistry.addShapedRecipe("Andesite Stairs",new ItemStack(ModRegistry.AndesiteStairs(), 4),
				"a  ",
				"aa ",
				"aaa",
				'a', new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, BlockStone.EnumType.ANDESITE_SMOOTH.getMetadata()));
		
		// Andesite Stairs
		ModRegistry.addShapedRecipe("Andesite Stairs",new ItemStack(ModRegistry.AndesiteStairs(), 4),
				"a  ",
				"aa ",
				"aaa",
				'a', new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, BlockStone.EnumType.ANDESITE.getMetadata()));
		
		// Diorite Stairs
		ModRegistry.addShapedRecipe("Diorite Stairs",new ItemStack(ModRegistry.DioriteStairs(), 4),
				"a  ",
				"aa ",
				"aaa",
				'a', new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, BlockStone.EnumType.DIORITE_SMOOTH.getMetadata()));
		
		// Diorite Stairs
		ModRegistry.addShapedRecipe("Diorite Stairs",new ItemStack(ModRegistry.DioriteStairs(), 4),
				"a  ",
				"aa ",
				"aaa",
				'a', new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, BlockStone.EnumType.DIORITE.getMetadata()));
		
		// Granite Stairs
		ModRegistry.addShapedRecipe("Granite Stairs",new ItemStack(ModRegistry.GraniteStairs(), 4),
				"a  ",
				"aa ",
				"aaa",
				'a', new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, BlockStone.EnumType.GRANITE_SMOOTH.getMetadata()));
		
		// Granite Stairs
		ModRegistry.addShapedRecipe("Granite Stairs",new ItemStack(ModRegistry.GraniteStairs(), 4),
				"a  ",
				"aa ",
				"aaa",
				'a', new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, BlockStone.EnumType.GRANITE.getMetadata()));
		
		// Glass Slabs
		ModRegistry.addShapedRecipe("Glass Slabs", new ItemStack(ModRegistry.GlassSlab(), 2),
				"a",
				"a",
				'a', Item.getItemFromBlock(Blocks.GLASS));
		
		// Andesite Slabs
		ModRegistry.addShapedRecipe("Andesite Slabs", new ItemStack(ModRegistry.AndesiteSlab(), 2),
				"a",
				"a",
				'a', new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, BlockStone.EnumType.ANDESITE_SMOOTH.getMetadata()));
		
		// Andesite Slabs
		ModRegistry.addShapedRecipe("Andesite Slabs", new ItemStack(ModRegistry.AndesiteSlab(), 2),
				"a",
				"a",
				'a', new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, BlockStone.EnumType.ANDESITE.getMetadata()));
		
		// Diorite Slabs
		ModRegistry.addShapedRecipe("Diorite Slabs", new ItemStack(ModRegistry.DioriteSlab(), 2),
				"a",
				"a",
				'a', new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, BlockStone.EnumType.DIORITE_SMOOTH.getMetadata()));
		
		// Diorite Slabs
		ModRegistry.addShapedRecipe("Diorite Slabs", new ItemStack(ModRegistry.DioriteSlab(), 2),
				"a",
				"a",
				'a', new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, BlockStone.EnumType.DIORITE.getMetadata()));
		
		// Granite Slabs
		ModRegistry.addShapedRecipe("Granite Slabs", new ItemStack(ModRegistry.GraniteSlab(), 2),
				"a",
				"a",
				'a', new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, BlockStone.EnumType.GRANITE_SMOOTH.getMetadata()));
		
		// Granite Slabs
		ModRegistry.addShapedRecipe("Granite Slabs", new ItemStack(ModRegistry.GraniteSlab(), 2),
				"a",
				"a",
				'a', new ItemStack(Item.getItemFromBlock(Blocks.STONE), 1, BlockStone.EnumType.GRANITE.getMetadata()));
		
		// Moderate House.
		ModRegistry.addShapelessRecipe("Moderate House", new ItemStack(ModRegistry.ModerateHouse()), 
				new ItemStack(ModRegistry.StartHouse()),
				new ItemStack(ModRegistry.StartHouse()),
				new ItemStack(ModRegistry.StartHouse()));
	}

	/**
	 * Saves the mod recipe to the field.
	 * @param displayName The name of the recipe.
	 * @param recipe The recipe to save.
	 */
	public static void SaveModRecipe(String displayName, IRecipe recipe)
	{
		// Make a hashmap with a string key and an arraylist of tuples for the value. The tuple will be of IRecipe and boolean
		if (ModRegistry.SavedRecipes.containsKey(displayName))
		{
			Tuple2<Boolean, ArrayList<IRecipe>> recipes = ModRegistry.SavedRecipes.get(displayName);
			recipes._2.add(recipe);
		}
		else
		{
			Tuple2<Boolean, ArrayList<IRecipe>> recipes = new Tuple2<Boolean, ArrayList<IRecipe>>(true, new ArrayList<IRecipe>());
			recipes._2.add(recipe);
			ModRegistry.SavedRecipes.put(displayName, recipes);
		}
	}
	
	/**
	 * Adds a shaped recipe to the game registry/mod registry.
	 * @param displayName The name of the recipe.
	 * @param stack The output of the recipel
	 * @param recipeComponents The components of the recipe.
	 * @return An IRecipe to be used.
	 */
	public static IRecipe addShapedRecipe(String displayName, ItemStack stack, Object... recipeComponents)
	{
		IRecipe recipe = GameRegistry.addShapedRecipe(stack, recipeComponents);
		
		ModRegistry.SaveModRecipe(displayName, recipe);
		
		return recipe;
	}
	
    /**
     * Adds a shapeless crafting recipe to the the game.
     * @param displayName the display name for this recipe.
     * @param stack The output of this recipe.
     * @param recipeComponents The various items/blocks for this recipe.
     * @return An IRecipe which was registered into the game registry.
     */
    public static IRecipe addShapelessRecipe(String displayName, ItemStack stack, Object... recipeComponents)
    {
        List<ItemStack> list = Lists.<ItemStack>newArrayList();

        for (Object object : recipeComponents)
        {
            if (object instanceof ItemStack)
            {
                list.add(((ItemStack)object).copy());
            }
            else if (object instanceof Item)
            {
                list.add(new ItemStack((Item)object));
            }
            else
            {
                if (!(object instanceof Block))
                {
                    throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
                }

                list.add(new ItemStack((Block)object));
            }
        }

        ShapelessRecipes returnValue = new ShapelessRecipes(stack, list); 
        GameRegistry.addRecipe(returnValue);
        
        ModRegistry.SaveModRecipe(displayName, returnValue);
        
        return returnValue;
    }

    /**
     * Adds a shaped ore recipe to the game.
     * @param displayName The display name.
     * @param stack The output of the recipe
     * @param recipeComponents The components of the recipe.
     * @return Returns the IRecipe.
     */
    public static IRecipe addShapedOreRecipe(String displayName, ItemStack stack, Object... recipeComponents)
    {
		IRecipe currentRecipe = new ShapedOreRecipe(stack, recipeComponents);
		
		GameRegistry.addRecipe(currentRecipe);
		
		ModRegistry.SaveModRecipe(displayName, currentRecipe);
		
		return currentRecipe;
    }
    
    public static IRecipe addShapelessOreRecipe(String displayName, ItemStack stack, Object... recipeComponents)
    {
    	IRecipe currentRecipe = new ShapelessOreRecipe(stack, recipeComponents);
    	GameRegistry.addRecipe(currentRecipe);
    	ModRegistry.SaveModRecipe(displayName, currentRecipe);
    	return currentRecipe;
    }
    
	/**
	 * This is where the mod messages are registered.
	 */
	public static void RegisterMessages()
	{
		Prefab.network.registerMessage(ConfigSyncHandler.class, ConfigSyncMessage.class, 1, Side.CLIENT);
		
		Prefab.network.registerMessage(StructureHandler.class, StructureTagMessage.class, 2, Side.SERVER);
		
		Prefab.network.registerMessage(PlayerEntityHandler.class, PlayerEntityTagMessage.class, 3, Side.CLIENT);
	}
	
	/**
	 * This is where mod capabilities are registered.
	 */
	public static void RegisterCapabilities()
	{
		// Register the dimension home capability.
		CapabilityManager.INSTANCE.register(IStructureConfigurationCapability.class, new StructureConfigurationStorage(), StructureConfigurationCapability.class);
	}

	/**
	 * Register an Item
	 *
	 * @param item The Item instance
	 * @param <T> The Item type
	 * @return The Item instance
	 */
	public static <T extends Item> T registerItem(T item)
	{
		ModRegistry.ModItems.add(item);

		return item;
	}
	
	/**
	 * Registers a block in the game registry.
	 * @param <T> The type of block to register.
	 * @param block The block to register.
	 * @return The block which was registered.
	 */
	public static <T extends Block> T registerBlock(T block)
	{
		return ModRegistry.registerBlock(block, true);
	}
	
	/**
	 * Registers a block in the game registry.
	 * @param <T> The type of block to register.
	 * @param block The block to register.
	 * @param includeItemBlock True to include a default item block.
	 * @return The block which was registered.
	 */
	public static <T extends Block> T registerBlock(T block, boolean includeItemBlock)
	{
		if (includeItemBlock)
		{
			ModItems.add(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
		
		ModRegistry.ModBlocks.add(block);
		
		return block;
	}
	
	/**
	 * Registers a block in the game registry.
	 * @param <T> The type of block to register.
	 * @param <I> The type of item block to register.
	 * @param block The block to register.
	 * @param itemBlock The item block to register with the block.
	 * @return The block which was registered.
	 */
	public static <T extends Block, I extends ItemBlock> T registerBlock(T block, I itemBlock)
	{
		ModRegistry.ModBlocks.add(block);
		
		if (itemBlock != null)
		{
			ModRegistry.ModItems.add(itemBlock);
		}
		
		return block;
	}
	
	/**
	 * Set the registry name of {@code item} to {@code itemName} and the un-localised name to the full registry name.
	 *
	 * @param item     The item
	 * @param itemName The item's name
	 */
	public static void setItemName(Item item, String itemName) 
	{
		if (itemName != null)
		{
			item.setRegistryName(itemName);
			item.setUnlocalizedName(item.getRegistryName().toString());
		}
	}
	
	/**
	 * Set the registry name of {@code block} to {@code blockName} and the un-localised name to the full registry name.
	 *
	 * @param block     The block
	 * @param blockName The block's name
	 */
	public static void setBlockName(Block block, String blockName) 
	{
		block.setRegistryName(blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}

	/**
	 * Adds all of the Mod Guis to the HasMap.
	 */
	public static void AddGuis()
	{
		ModRegistry.ModGuis.put(ModRegistry.GuiWareHouse, GuiWareHouse.class);
		ModRegistry.ModGuis.put(ModRegistry.GuiChickenCoop, GuiChickenCoop.class);
		ModRegistry.ModGuis.put(ModRegistry.GuiProduceFarm, GuiProduceFarm.class);
		ModRegistry.ModGuis.put(ModRegistry.GuiTreeFarm, GuiTreeFarm.class);
		ModRegistry.ModGuis.put(ModRegistry.GuiFishPond, GuiFishPond.class);
		ModRegistry.ModGuis.put(ModRegistry.GuiStartHouseChooser, GuiStartHouseChooser.class);
		ModRegistry.ModGuis.put(ModRegistry.GuiAdvancedWareHouse, GuiAdvancedWareHouse.class);
		ModRegistry.ModGuis.put(ModRegistry.GuiMonsterMasher, GuiMonsterMasher.class);
		ModRegistry.ModGuis.put(ModRegistry.GuiHorseStable, GuiHorseStable.class);
		ModRegistry.ModGuis.put(ModRegistry.GuiNetherGate,  GuiNetherGate.class);
		ModRegistry.ModGuis.put(ModRegistry.GuiModularHouse, GuiModularHouse.class);
		ModRegistry.ModGuis.put(ModRegistry.GuiDrafter, GuiDrafter.class);
		ModRegistry.ModGuis.put(ModRegistry.GuiBasicStructure, GuiBasicStructure.class);
		ModRegistry.ModGuis.put(ModRegistry.GuiVillagerHouses, GuiVillaerHouses.class);
		ModRegistry.ModGuis.put(ModRegistry.GuiModerateHouse, GuiModerateHouse.class);
	}

}