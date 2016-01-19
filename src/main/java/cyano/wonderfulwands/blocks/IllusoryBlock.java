package cyano.wonderfulwands.blocks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class IllusoryBlock extends net.minecraft.block.Block{

	private static final Map<Block,IllusoryBlock> blockLookup = new HashMap<Block,IllusoryBlock>();

	public final String name;
	
	public IllusoryBlock(Block sourceBlock) {
		this(
				sourceBlock.getMapColor(sourceBlock.getDefaultState()),
				"illusion_" + (sourceBlock.getUnlocalizedName().replace("tile.", "")),
				sourceBlock, sourceBlock.getUnlocalizedName().replace("tile.", "")
		);
	}
	

	public IllusoryBlock(MapColor color, String name, Block sourceBlock) {
		this(color,name,sourceBlock,sourceBlock.getUnlocalizedName().replace("tile.", ""));
	}
	

	public IllusoryBlock(MapColor color, String name, Block sourceBlock, String sourceBlockName) {
		this(color,name,sourceBlock,sourceBlockName,sourceBlockName);
	}
	
	public IllusoryBlock(MapColor color, String name, Block sourceBlock, String sourceBlockName, String sourceBlockUnlocalizedName) {
		super(Material.carpet, color);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setHardness(0.0F);
		this.setStepSound(this.soundTypeCloth);
		this.name = name;
		this.setUnlocalizedName(sourceBlockUnlocalizedName);
		blockLookup.put(sourceBlock, this);
	}
	
	public static IllusoryBlock getIllusionForBlock(Block b){
		return blockLookup.get(b);
	}
	
	public static Map<Block,IllusoryBlock> getLookUpTable(){
		return Collections.unmodifiableMap(blockLookup);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override public AxisAlignedBB getCollisionBoundingBox(World w, BlockPos coord, IBlockState state)
	{
		return null;
	}


	@Override
	public Item getItemDropped(IBlockState state, Random prng, int fortune)
	{
		return null;
	}
	
	@Override
	protected boolean canSilkHarvest()
	{
		return true;
	}
	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	@Override public boolean isOpaqueCube()
	{
		return false;
	}

	@Override public boolean isFullCube(){
		return false;
	}

	@Override public EnumWorldBlockLayer getBlockLayer(){
		return EnumWorldBlockLayer.SOLID;
	}
}
