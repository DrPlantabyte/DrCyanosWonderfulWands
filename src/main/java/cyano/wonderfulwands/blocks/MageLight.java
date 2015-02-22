package cyano.wonderfulwands.blocks;

import java.util.ArrayList;
import java.util.Random;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class MageLight extends Block {

	public final static String name = "mage_light";
	
	public MageLight() {
        super(Material.circuits);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHardness(0.0F);
        this.setLightLevel(1F);
        this.setStepSound(soundTypeCloth);
        this.setUnlocalizedName(WonderfulWands.MODID+"_"+name);
        
        float f = 0.25F;
        float min = 0.5F - f;
        float max = 0.5F + f;
        this.setBlockBounds(min,min,min,max,max,max);
        
        
        //net.minecraft.block.BlockReed
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
    	return EnumWorldBlockLayer.CUTOUT;
    }
    
}
