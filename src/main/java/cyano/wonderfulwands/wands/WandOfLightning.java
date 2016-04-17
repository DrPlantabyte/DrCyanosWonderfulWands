package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import cyano.wonderfulwands.projectiles.EntityWandLightningBolt;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class WandOfLightning extends Wand {
	public static final String itemName = "wand_lightning";

	public static int cooldown = 20;

	public static final double rangeMax = 16;
	public static final double radius = 2;

	public static int defaultCharges = 64;

	public static final float damage = 8;

	//	public static final int burnTime = 3;
	/**
	 * Constructor
	 */
	public WandOfLightning(){
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
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




	private final double piOver2 = Math.PI / 2;

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


		if (!world.isRemote)
		{

			// zap all entities in a 16 block long, 4 block wide cylinder...
			// first, calculate range limit (which will be shorter if we hit a solid block)
			double vecX = (double)(-MathHelper.sin(playerEntity.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(playerEntity.rotationPitch / 180.0F * (float)Math.PI));
			double vecY = (double)(-MathHelper.sin(playerEntity.rotationPitch / 180.0F * (float)Math.PI));
			double vecZ = (double)( MathHelper.cos(playerEntity.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(playerEntity.rotationPitch / 180.0F * (float)Math.PI));

			double range = 0;
			double nx = playerEntity.posX,ny = playerEntity.posY+1,nz = playerEntity.posZ;
			while(range < rangeMax){
				if(world.getBlockState(new BlockPos((int)nx, (int)ny, (int)nz)).isOpaqueCube()){
					break;
				}
				nx += vecX;
				ny += vecY;
				nz += vecZ;
				range += 1;
			}
			if(range < 2) range = 2; 
			final double rangeSqr = range * range;
			vecX *= range;
			vecY *= range;
			vecZ *= range;

			final EntityLightningBolt fakeBolt = new  EntityLightningBolt(world,nx, ny, nz, true);


			// 	System.out.println("Lightning range = "+range);

			//	AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(playerEntity.posX-range,playerEntity.posY-range,playerEntity.posZ-range,
			//			playerEntity.posX+range,playerEntity.posY+range,playerEntity.posZ+range);
			AxisAlignedBB bb = new AxisAlignedBB(playerEntity.posX-range,playerEntity.posY-range,playerEntity.posZ-range,
					playerEntity.posX+range,playerEntity.posY+range,playerEntity.posZ+range);
			List entities = world.getEntitiesWithinAABB(EntityLivingBase.class, bb); // ArrayList<EntityLivingBase> 
			for(int i = 0; i < entities.size(); i++){
				if(!(entities.get(i) instanceof EntityLivingBase)) continue;
				EntityLivingBase e = (EntityLivingBase) entities.get(i);
				if(playerEntity == e){
					continue; // don't zap yourself
				}
				double dx = (e.posX - playerEntity.posX);
				double dy = (e.posY - playerEntity.posY);
				double dz = (e.posZ - playerEntity.posZ);
				double distSqr = dx*dx+dy*dy+dz*dz;
				if(distSqr < rangeSqr ){
					// target in range, but is it in AoE?
					double dist = Math.sqrt(distSqr);
					// A.B = A*B cos@
					double angle = Math.acos(dotProduct(dx,dy,dz,vecX,vecY,vecZ)/(dist * range));
					if(angle < piOver2){ // in front of player
						double perpendicular = dist*Math.sin(angle);
						if(perpendicular < radius){
							// in AoE
							e.attackEntityFrom(DamageSource.magic, damage);
							//	e.setFire(burnTime);
							e.onStruckByLightning(fakeBolt);
						}
					}
				}
			}


			playFadedSound(world,playerEntity.getPositionVector(),64, SoundEvents.entity_generic_explode,4F,1.7F);
			playSound(world,playerEntity.getPositionVector(),64, SoundEvents.entity_lightning_thunder,1F,1.7F);

			world.spawnEntityInWorld(new EntityWandLightningBolt(world, 
					playerEntity,playerEntity.posX,playerEntity.posY+1,playerEntity.posZ, 
					playerEntity.rotationYaw, playerEntity.rotationPitch, range / rangeMax));
		}
		return srcItemStack;
	}



	@Override  public int getBaseRepairCost(){
		return 2;
	}

	static double dotProduct(double x1, double y1, double z1, double x2, double y2, double z2){
		return x1 * x2 + y1 * y2 + z1 * z2;
	}
}
