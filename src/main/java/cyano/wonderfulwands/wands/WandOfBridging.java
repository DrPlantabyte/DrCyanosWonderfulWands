package cyano.wonderfulwands.wands;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cyano.wonderfulwands.WonderfulWands;

public class WandOfBridging extends Wand {
	public static final String itemName = "wand_bridge";

	public static int cooldown = 10;
	
	public static int defaultCharges = 64;
	
	final int limit = 32;
	
	private final Block bridgeBlock;
	
	public WandOfBridging(Block bridgeBlock) {
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
		this.setCreativeTab(CreativeTabs.tabTools);
		this.bridgeBlock = bridgeBlock;
	}


	@Override
	public int getBaseRepairCost() {
		return 2;
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
		int blocksChanged = 0;
		
		Vec3 delta = playerEntity.getLookVec();
		delta = (new Vec3(delta.xCoord,0,delta.zCoord)).normalize();
		Vec3 originPrime = new Vec3(coord.getX(), coord.getY(), coord.getZ());
		
		int[] changeTracker = new int[3];
		Vec3 pos = originPrime;
		for(int i = 0; i < limit; i++){
			int blockDelta = 0;
			BlockPos block = new BlockPos(pos);
			
			
			blockDelta += placeBridgeBlock(world,block);
			blockDelta += placeBridgeBlock(world,block.north());
			blockDelta += placeBridgeBlock(world,block.south());
			blockDelta += placeBridgeBlock(world,block.east());
			blockDelta += placeBridgeBlock(world,block.west());
			
			changeTracker[0] = changeTracker[1];
			changeTracker[1] = changeTracker[2];
			changeTracker[2] = blockDelta;
			if(changeTracker[0] + changeTracker[1] + changeTracker[2] == 0){
				// hit a wall, stop
				break;
			}
			blocksChanged += blockDelta;
			
			// move forward
			pos = pos.add(delta);
		}
		
		if(blocksChanged > 0){
			srcItemStack.damageItem(1, playerEntity);
			playSound("random.levelup",world,playerEntity);
			return true;
		}else {
			return false;
		}
	}
	
	private int placeBridgeBlock(World world, BlockPos coord) {
		if(world.isRemote)return 0;
		if(world.isAirBlock(coord)){
			world.setBlockState(coord, bridgeBlock.getDefaultState());
			return 1;
		}
		IBlockState current = world.getBlockState(coord);
		if(current.getBlock().getMaterial().blocksMovement()) return 0;
		world.setBlockState(coord, bridgeBlock.getDefaultState());
		return 1;
	}


}
