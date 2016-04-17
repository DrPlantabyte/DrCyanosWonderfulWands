package cyano.wonderfulwands.blocks;

import net.minecraft.block.BlockRail;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

/**
 * Created by Chris on 4/17/2016.
 */
public class FeyRail extends BlockRail {

	public FeyRail(){
		super();
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
		return Collections.emptyList();
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player){return false;}
}
