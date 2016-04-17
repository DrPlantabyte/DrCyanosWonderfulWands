package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WandOfIce extends Wand {
	public static final String itemName = "wand_ice";

	public static int cooldown = 10;
	
	public static int defaultCharges = 256;
	
	public WandOfIce() {
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
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
		int rSquared = 9;
		for(int dy = 2; dy >= -2; dy--){
			int y = targetY + dy;
			for(int dx = -2; dx <= 2; dx++){
				int x = targetX + dx;
				for(int dz = -2; dz <= 2; dz++){
					int z = targetZ + dz;
					BlockPos pos = new BlockPos(x,y,z);
					// spheritize
					if((dy * dy + dx * dx + dz * dz) <= rSquared){
						// set to ice
						blocksChanged += freezeBlock(world,pos);
					}
				}
			}
		}
		if(blocksChanged > 0){
			srcItemStack.damageItem(1, playerEntity);
			playSound(SoundEvents.entity_experience_orb_pickup,world,playerEntity);
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
		}else if(target == Blocks.snow_layer) {
			if(((Integer)bs.getValue(BlockSnow.LAYERS)) < 8){
				w.setBlockState(coord, Blocks.snow_layer.getDefaultState()
						.withProperty(BlockSnow.LAYERS,(((Integer)bs.getValue(BlockSnow.LAYERS)) + 1)));
			} else {
				w.setBlockState(coord, Blocks.snow.getDefaultState());
			}
		}else if(target.isFullCube(bs) && w.isAirBlock(coord.up())){
			w.setBlockState(coord.up(), Blocks.snow_layer.getDefaultState());
			return 1;
		}
		return 0;
	}

}
