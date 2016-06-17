package com.wuest.prefab.Blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.wuest.prefab.ModRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPurpurSlab.Variant;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Provides a way to store large amounts of stone. 
 * @author WuestMan
 */
public class BlockCompressedStone extends Block implements IMetaBlock
{
	public static final PropertyEnum<BlockCompressedStone.EnumType> VARIANT = PropertyEnum.<BlockCompressedStone.EnumType>create("variant", BlockCompressedStone.EnumType.class);
	
	/**
	 * Initializes a new instance of the CompressedStone class.
	 */
	public BlockCompressedStone()
	{
		super(Material.ROCK);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setHardness(1.5F);
		this.setResistance(10.0F);
		this.setHarvestLevel("pickaxe", 0);
		this.setSoundType(SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.COMPRESSED_STONE));
		ModRegistry.setBlockName(this, "blockCompressedStone");
	}
	
    /**
     * Gets the localized name of this block. Used for the statistics page.
     */
	@Override
    public String getLocalizedName()
    {
        return I18n.translateToLocal("tile.prefab" + BlockCompressedStone.EnumType.COMPRESSED_STONE.getUnlocalizedName() + ".name");
    }
	
    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Nullable
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModRegistry.CompressedStoneBlock());
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    @Override
    public int damageDropped(IBlockState state)
    {
        return this.getMetaFromState(state);
    }
    
    @Override
    /**
     * Called when a user uses the creative pick block button on this block
     *
     * @param target The full target the player is looking at
     * @return A ItemStack to add to the player's inventory, Null if nothing should be added.
     */
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
    	 return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(world.getBlockState(pos)));
    }

    @Override
    public String getSpecialName(ItemStack stack) 
    {
        for (BlockCompressedStone.EnumType enumType : BlockCompressedStone.EnumType.values())
        {
        	if (enumType.meta == stack.getItemDamage())
        	{
        		return enumType.name;
        	}
        }
        
        return "";
    }
    
    @Override
    public String getMetaDataUnLocalizedName(int metaData)
    {
    	EnumType type = EnumType.byMetadata(metaData);
    	
    	return type.unlocalizedName;
    }
    
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for (BlockCompressedStone.EnumType enumType : BlockCompressedStone.EnumType.values())
        {
            list.add(new ItemStack(itemIn, 1, enumType.getMetadata()));
        }
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockCompressedStone.EnumType.byMetadata(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((BlockCompressedStone.EnumType)state.getValue(VARIANT)).getMetadata();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }
	
	public static enum EnumType implements IStringSerializable
	{
		COMPRESSED_STONE(0, "blockcompressedstone", "blockCompressedStone"),
		DOUBLE_COMPRESSED_STONE(1, "blockdoublecompressedstone", "blockDoubleCompressedStone"),
		TRIPLE_COMPRESSED_STONE(2, "blocktriplecompressedstone", "blockTripleCompressedStone");
		
        private final int meta;
		
        /** The EnumType's name. */
        private final String name;
        private final String unlocalizedName;
        /** Array of the Block's BlockStates */
        private static final BlockCompressedStone.EnumType[] META_LOOKUP = new BlockCompressedStone.EnumType[values().length];
        
        private EnumType(int meta, String name)
        {
            this(meta, name, name);
        }

        private EnumType(int meta, String name, String unlocalizedName)
        {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        public static ResourceLocation[] GetNames()
        {
        	List<ResourceLocation> list = Lists.newArrayList();
        	
        	for (EnumType type : EnumType.values())
        	{
        		list.add(new ResourceLocation("prefab", type.unlocalizedName));
        	}
        	
        	return list.toArray(new ResourceLocation[list.size()]);
        }
        
        /**
         * Returns the EnumType's metadata value.
         */
        public int getMetadata()
        {
            return this.meta;
        }
        
        public String toString()
        {
            return this.name;
        }

		@Override
		public String getName()
		{
			return this.name;
		}
		
        public String getUnlocalizedName()
        {
            return this.unlocalizedName;
        }
		
        /**
         * Returns an EnumType for the BlockState from a metadata value.
         */
        public static BlockCompressedStone.EnumType byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }
        
        static
        {
            for (BlockCompressedStone.EnumType type : values())
            {
                META_LOOKUP[type.getMetadata()] = type;
            }
        }
	}
}
