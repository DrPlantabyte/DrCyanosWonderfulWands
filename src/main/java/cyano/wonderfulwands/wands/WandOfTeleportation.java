package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WandOfTeleportation extends Wand {
	public static final String itemName = "wand_teleportation";

	
	public static int defaultCharges = 64;
	
	public WandOfTeleportation() {
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
        this.setMaxDamage(defaultCharges + 1);
	}


	@Override
	public int getBaseRepairCost() {
		return 3;
	}
	
	@Override public int getMaxItemUseDuration(ItemStack par1ItemStack){
		return 1200;
	}
	@Override public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.BOW;
    }

	@Override public ActionResult<ItemStack> onItemRightClick(ItemStack srcItemStack, World world, EntityPlayer playerEntity, EnumHand hand){
		playerEntity.setActiveHand(hand);
		return  new ActionResult(EnumActionResult.SUCCESS, srcItemStack);
	}

	/**
	 * Callback for item usage, invoked when right-clicking on a block. If the item
	 * does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	@Override public boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, BlockPos coord, EnumFacing blockFace, float par8, float par9, float par10){
		return false;
	}

	@Override public void onPlayerStoppedUsing (ItemStack srcItemStack, World world, EntityLivingBase playerEntity, int timeRemain){
		int chargetime = this.getMaxItemUseDuration(srcItemStack) - timeRemain;
		if(chargetime < 3) return;
		if (playerEntity instanceof EntityPlayer && !((EntityPlayer)playerEntity).capabilities.isCreativeMode)
		{
			if(isOutOfCharge(srcItemStack)){
				// wand out of magic
				playSound(noChargeAttackSound,world,playerEntity);
				return;
			}
			srcItemStack.damageItem(1, playerEntity);
		}

		playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT,world,playerEntity);
			
			final int maxRange = 160;
			Vec3d origin = (new Vec3d(playerEntity.posX, playerEntity.posY + playerEntity.getEyeHeight(), playerEntity.posZ));
			Vec3d vector = playerEntity.getLookVec();
			Vec3d pos = origin;
			Vec3d next = pos;
			BlockPos coord = new BlockPos(pos);
			
			for(int i = 0; i < maxRange; i++){
				next = pos.add(vector);
				if(next.yCoord < 0 || next.yCoord > 255) break;
				BlockPos nextBlock = new BlockPos(next);
				if(world.isAreaLoaded(coord, 1, true) ){
					if(world.isAirBlock(nextBlock)){
						world.spawnParticle(EnumParticleTypes.PORTAL, 
								pos.xCoord + (world.rand.nextDouble() - 0.5), pos.yCoord + (world.rand.nextDouble() - 0.5), pos.zCoord + (world.rand.nextDouble() - 0.5), 
								(world.rand.nextFloat() - 0.5f) * 0.2f, (world.rand.nextFloat() - 0.5f) * 0.2f, (world.rand.nextFloat() - 0.5f) * 0.2f, 
								new int[0]);
						pos = next;
						coord = nextBlock;
						continue;
					} else {
						if(world.isAirBlock(nextBlock.up())){
							coord = nextBlock.up();
						}
						break;
					}
				} else{
					break;
				}
			}

			playerEntity.setLocationAndAngles(coord.getX()+0.5,coord.getY()+0.25,coord.getZ()+0.5,playerEntity.rotationYaw, playerEntity.rotationPitch);
			if(world.isRemote)playerEntity.setVelocity(0, 0, 0);
			playerEntity.fallDistance = 0;

		playSound(playerEntity.getEntityWorld(),next,12,SoundEvents.ENTITY_ENDERMEN_TELEPORT);
	}

}
