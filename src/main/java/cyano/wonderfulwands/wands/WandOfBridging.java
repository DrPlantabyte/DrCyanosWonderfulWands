package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WandOfBridging extends Wand {
	public static final String itemName = "wand_bridge";

	public static int cooldown = 10;
	
	public static int defaultCharges = 64;
	
	final int limit = 32;
	
	private final Block bridgeBlock;
	
	public WandOfBridging(Block bridgeBlock) {
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
		this.bridgeBlock = bridgeBlock;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.BOW;
	}

	@Override
	public int getBaseRepairCost() {
		return 2;
	}
	@Override public boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, BlockPos coord, EnumFacing blockFace, float px, float py, float pz){
		return true;
	}
	@Override public ItemStack onItemUseFinish (ItemStack srcItemStack, World world, EntityLivingBase playerEntity){

		if (playerEntity instanceof EntityPlayer && !((EntityPlayer)playerEntity).capabilities.isCreativeMode)
		{
			if(isOutOfCharge(srcItemStack)){
				// wand out of magic
				playSound(noChargeAttackSound,world,playerEntity);
				return srcItemStack;
			}
		}
		int blocksChanged = 0;
		
		Vec3d delta = playerEntity.getLookVec();
		delta = (new Vec3d(delta.xCoord,0,delta.zCoord)).normalize();
		BlockPos coord = playerEntity.getPosition().down();
		Vec3d originPrime = new Vec3d(coord.getX(), coord.getY(), coord.getZ());
		
		int[] changeTracker = new int[3];
		Vec3d pos = originPrime;
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
			playSound(SoundEvents.entity_player_levelup,world,playerEntity);
			if (playerEntity instanceof EntityPlayer && !((EntityPlayer)playerEntity).capabilities.isCreativeMode)
			{
				srcItemStack.damageItem(1, playerEntity);
			}
		}

		return srcItemStack;
	}
	
	private int placeBridgeBlock(World world, BlockPos coord) {
		if(world.isRemote)return 0;
		if(world.isAirBlock(coord)){
			world.setBlockState(coord, bridgeBlock.getDefaultState());
			return 1;
		}
		IBlockState current = world.getBlockState(coord);
		if(current.getMaterial().blocksMovement()) return 0;
		world.setBlockState(coord, bridgeBlock.getDefaultState());
		return 1;
	}


}
