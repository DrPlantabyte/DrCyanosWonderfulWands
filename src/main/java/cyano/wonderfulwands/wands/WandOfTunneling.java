package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WandOfTunneling extends Wand {
	public static final String itemName = "wand_tunneling";

	public static int cooldown = 10;

	public static int defaultCharges = 64;

	public static int range = 16;

	public WandOfTunneling() {
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
        this.setMaxDamage(defaultCharges + 1);
	}


	@Override
	public int getBaseRepairCost() {
		return 1;
	}
	
	@Override public boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, BlockPos coord, EnumFacing blockFace, float par8, float par9, float par10){
		if (!playerEntity.capabilities.isCreativeMode)
        {
        	if(isOutOfCharge(srcItemStack)){
        		// wand out of magic
        		playSound(noChargeAttackSound,world,playerEntity);
        		return true;
        	}
        }
		mineTunnel(world,coord,blockFace.getOpposite());
		playSound(SoundEvents.ENTITY_PLAYER_LEVELUP,world,playerEntity);

		if (!playerEntity.capabilities.isCreativeMode)
		{
			srcItemStack.damageItem(1, playerEntity);
		}

		return true;
		
	}

	protected void mineTunnel(World w, BlockPos origin, EnumFacing direction){
		BlockPos pos = origin;
		for(int i = 0; i < range; i += 3){
			if(pos.getY() < 1) break;
			mineChunk(w,pos);
			pos = pos.offset(direction,3);
		}
	}

	protected void mineChunk(World w, BlockPos center){
		for(int dx = -1; dx <= 1; dx++){
			for(int dz = -1; dz <= 1; dz++){
				for(int dy = -1; dy <= 1; dy++){
					mineBlock(w,center.add(dx,dy,dz));
				}
			}
		}
	}

	protected boolean mineBlock( World world, BlockPos coord){
		IBlockState targetBlock = world.getBlockState(coord);
		if(targetBlock.getBlock() == Blocks.BEDROCK){
			return false;
		}
		
		
		if(targetBlock.getBlockHardness(world,coord) < 100.0F){
			// mine it
			int fortuneLevel = 0;
		//	if(!world.isRemote){ }
			world.setBlockToAir(coord);
			targetBlock.getBlock().dropBlockAsItemWithChance(world, coord, targetBlock, 1F, fortuneLevel);
			return true;
		} 
		return false;
		
	}

}
