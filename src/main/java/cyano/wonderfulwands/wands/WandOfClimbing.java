package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.BlockVine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WandOfClimbing extends Wand {
	public static final String itemName = "wand_climb";

	public static int cooldown = 10;
	
	public static int defaultCharges = 64;
	
	
	public WandOfClimbing() {
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
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
		if(blockFace == EnumFacing.UP || blockFace == EnumFacing.DOWN){
			return false;
		}
		boolean success = growVines(world,coord.offset(blockFace)) > 0;
		if(success){
	        playSound(SoundEvents.entity_experience_orb_pickup,world,playerEntity);
			if (!playerEntity.capabilities.isCreativeMode)
	        {
	        	srcItemStack.damageItem(1, playerEntity);
	        }
		}
		return success;
		
	}

	private int growVines( World world, BlockPos coord) {
		int numVinesAdded = 0;
		
		BlockPos p = coord;
		// first, go down
		while(p.getY() > 0 && world.isAirBlock(p)){
			int n = placeVinesAt(world,p);
			if(n == 0)break;
			numVinesAdded += n;
			p = p.down();
		}
		
		p = coord.up();
		// then go up
		while(p.getY() > 0 && world.isAirBlock(p)){
			int n = placeVinesAt(world,p);
			if(n == 0)break;
			numVinesAdded += n;
			p = p.up();
		}
		
		
		return numVinesAdded ;
	}

	private int placeVinesAt(World world,BlockPos p) {
		boolean n=false,e=false,s=false,w=false,canDo=false;
		if(world.getBlockState(p.north()).getMaterial().blocksMovement()){
			n = true;
			canDo = true;
		}
		if(world.getBlockState(p.east()).getMaterial().blocksMovement()){
			e = true;
			canDo = true;
		}
		if(world.getBlockState(p.south()).getMaterial().blocksMovement()){
			s = true;
			canDo = true;
		}
		if(world.getBlockState(p.west()).getMaterial().blocksMovement()){
			w = true;
			canDo = true;
		}
		if(canDo){
			world.setBlockState(p, Blocks.vine.getDefaultState()
					.withProperty(BlockVine.UP, false)
					.withProperty(BlockVine.NORTH, n)
					.withProperty(BlockVine.EAST, e)
					.withProperty(BlockVine.SOUTH, s)
					.withProperty(BlockVine.WEST, w));
			return 1;
		}else{
			return 0;
		}
		
		
		
	}
	

}
