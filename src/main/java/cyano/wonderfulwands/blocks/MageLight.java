package cyano.wonderfulwands.blocks;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class MageLight extends Block {

	public final static String name = "mage_light";

	private final AxisAlignedBB bounds;
	
	public MageLight() {
        super(Material.CIRCUITS);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setHardness(0.0F);
        this.setLightLevel(1F);
        this.setSoundType(SoundType.GLASS);
        this.setUnlocalizedName(WonderfulWands.MODID+"_"+name);
        
        float f = 0.25F;
        float min = 0.5F - f;
        float max = 0.5F + f;
        this.bounds = new AxisAlignedBB(min,min,min,max,max,max);
        
        
        //net.minecraft.block.BlockReed
	}


	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return bounds;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState blockState, World worldIn, BlockPos pos){
		return NULL_AABB;
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
    @Override public boolean isOpaqueCube(IBlockState bs)
    {
        return false;
    }
    
    @Override public boolean isFullCube(IBlockState bs){
    	return false;
    }

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
    
    @SideOnly(Side.CLIENT)
    @Override
	public void randomDisplayTick(IBlockState bs, World w, BlockPos coord, Random rand) {
        final double d0 = coord.getX() + 0.5;
        final double d = coord.getY() + 0.5;
        final double d2 = coord.getZ() + 0.5;
        final double d3 = 0.22;
        final double d4 = 0.27;
        
        w.spawnParticle(EnumParticleTypes.FLAME, d0, d, d2, 0.0, 0.0, 0.0, new int[0]);
        
    }
    
}
