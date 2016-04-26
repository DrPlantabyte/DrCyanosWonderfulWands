package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import cyano.wonderfulwands.util.RayTrace;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WandOfStorms extends Wand  {
	public static final String itemName = "wand_storm";

	public static int cooldown = 20;
	
	public static int defaultCharges = 64;
	
	public static final int AOEdiameter = 64;
	private static final int AOEsubtractor = -1 * AOEdiameter / 2;
	
	public WandOfStorms() {
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
	}


	@Override
	public int getBaseRepairCost() {
		return 1;
	}

	@Override public int getMaxItemUseDuration(ItemStack par1ItemStack){
		return cooldown;
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


	/**
	 * Invoked when the player releases the right-click button
	 */
	@Override public void onPlayerStoppedUsing (ItemStack srcItemStack, World world, EntityLivingBase playerEntity, int timeRemain){
		super.onPlayerStoppedUsing(srcItemStack, world, playerEntity, timeRemain);
	}

	/**
	 * This method is invoked after the item has been used for an amount of time equal to the duration
	 * provided to the EntityPlayer.setItemInUse(stack, duration).
	 */
	@Override public ItemStack onItemUseFinish (ItemStack srcItemStack, World world, EntityLivingBase playerEntity)
	{ //

		if (playerEntity instanceof EntityPlayer && !((EntityPlayer)playerEntity).capabilities.isCreativeMode)
		{
			if(isOutOfCharge(srcItemStack)){
				// wand out of magic
				playSound(noChargeAttackSound,world,playerEntity);
				return srcItemStack;
			}
			srcItemStack.damageItem(1, playerEntity);
		}

		playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,world,playerEntity);


		if (!world.isRemote)
		{
			// drop lightning at looked-at location
			
			RayTraceResult trace = RayTrace.rayTraceBlocksAndEntities(world, 64, playerEntity);
			if(trace == null || trace.typeOfHit == RayTraceResult.Type.MISS){ // missed! Drop random lightning
				int r = 32;
				int d = r * 2;
				BlockPos target = new BlockPos(playerEntity.posX+world.rand.nextInt(d)-r,playerEntity.posY,playerEntity.posZ+world.rand.nextInt(d)-r);
				while(target.getY() < 255 && !world.isAirBlock(target)){
					target = target.up();
				}
				while(target.getY() > 0 && world.isAirBlock(target)){
					target = target.down();
				}
				world.addWeatherEffect(new EntityLightningBolt(world,target.getX(), target.getY(), target.getZ(),false));
			} else {
				Vec3d target = trace.hitVec;
				if(target == null){
					target = new Vec3d(trace.getBlockPos().getX(),trace.getBlockPos().getY(),trace.getBlockPos().getZ());
				}
				world.addWeatherEffect(new EntityLightningBolt(world,target.xCoord, target.yCoord, target.zCoord,false));
			}
			
			
		}
		return srcItemStack;
	}

	
	
}
