package cyano.wonderfulwands.wands;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import cyano.wonderfulwands.WonderfulWands;

public class WandOfIce extends Wand {
	public static final String itemName = "wand_ice";

	public static int cooldown = 10;
	
	public static int defaultCharges = 256;
	
	public WandOfIce() {
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
		this.setCreativeTab(CreativeTabs.tabTools);
	}


	@Override
	public int getBaseRepairCost() {
		return 1;
	}
	
	@Override public boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, BlockPos coord, EnumFacing blockFace, float par8, float par9, float par10){
		int targetX=coord.getX(),targetY=coord.getY(),targetZ=coord.getZ();
		if (!playerEntity.capabilities.isCreativeMode)
        {
        	if(isOutOfCharge(srcItemStack)){
        		// wand out of magic
        		playSound(noChargeAttackSound,world,playerEntity);
        		return true;
        	}
        }
		int blocksChanged = 0;
		for(int y = targetY+1; y >= targetY-1; y--){
			if(y < 0) continue;
			for(int x = targetX-2; x <= targetX+2; x++){
				for(int z = targetZ-2; z <= targetZ+2; z++){
					// cut corners
					if((x == targetX-2 || x == targetX+2) && (z == targetZ-2 || z == targetZ+2))continue;
					// set to ice
					blocksChanged += freezeBlock(world,new BlockPos(x,y,z));
				}
			}
		}
		if(blocksChanged > 0){
			srcItemStack.damageItem(1, playerEntity);
			playSound("random.orb",world,playerEntity);
			return true;
		}else {
			return false;
		}
	}
	
	protected int freezeBlock(World w, BlockPos coord){
		IBlockState bs = w.getBlockState(coord);
		Block target = bs.getBlock();
		if(target == Blocks.water || target == Blocks.flowing_water){
			w.setBlockState(coord, Blocks.ice.getDefaultState());
			return 1;
		} else if(target == Blocks.lava || target == Blocks.flowing_lava){
			w.setBlockState(coord, Blocks.cobblestone.getDefaultState());
			return 1;
		}else if(target == Blocks.snow && ((Integer)bs.getValue(BlockSnow.LAYERS)) < 8){
			w.setBlockState(coord, Blocks.snow.getStateById(((Integer)bs.getValue(BlockSnow.LAYERS)) + 1));
		}else if(target.isSolidFullCube() && w.isAirBlock(coord.up())){
			w.setBlockState(coord.up(), BlockSnow.getStateById(2));
			return 1;
		}
		return 0;
	}

}
