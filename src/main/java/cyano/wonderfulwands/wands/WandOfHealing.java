package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import cyano.wonderfulwands.projectiles.DeathSkull;
import cyano.wonderfulwands.util.RayTrace;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class WandOfHealing extends Wand {
	public static final String itemName = "wand_healing";



	public static int defaultCharges = 64;
	public static int range = 16;

	public static float healingAmount = 5.0F;
	
	public WandOfHealing() {
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
	}


	@Override
	public int getBaseRepairCost() {
		return 4;
	}


	@Override public ActionResult<ItemStack> onItemRightClick(ItemStack srcItemStack, World world, EntityPlayer playerEntity, EnumHand hand){
		playerEntity.setActiveHand(hand);
		return  new ActionResult(EnumActionResult.SUCCESS, srcItemStack);
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.BOW;
	}
	/**
	 * Callback for item usage, invoked when right-clicking on a block. If the item
	 * does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	@Override public boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, BlockPos coord, EnumFacing blockFace, float par8, float par9, float par10){
		return true;
	}

	@Override public int getMaxItemUseDuration(ItemStack par1ItemStack){
		return 7200;
	}

	/**
	 * Invoked when the player releases the right-click button
	 */
	@Override public void onPlayerStoppedUsing (ItemStack srcItemStack, World world, EntityLivingBase playerEntity, int timeRemain){

		if (playerEntity instanceof EntityPlayer && !((EntityPlayer)playerEntity).capabilities.isCreativeMode)
		{
			if(isOutOfCharge(srcItemStack)){
				// wand out of magic
				playSound(noChargeAttackSound,world,playerEntity);
				return ;
			}
		}

		RayTraceResult trace = RayTrace.rayTraceBlocksAndEntities(world,range,playerEntity);
		if(trace == null) return ;
		Entity target = trace.entityHit;
		if(!(target instanceof EntityLivingBase)){
			target = playerEntity;
		}
		if(target instanceof EntityLivingBase){
			EntityLivingBase e = (EntityLivingBase)target;
			float health = e.getHealth();
			float maxHealth = e.getMaxHealth();
			if(health < maxHealth){
				e.setHealth(Math.min(maxHealth, health + healingAmount) );
				playSound(SoundEvents.entity_experience_orb_pickup,world,playerEntity);
				playSound(SoundEvents.entity_player_levelup,world,e);
				e.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("regeneration"),32,1));
				if (playerEntity instanceof EntityPlayer && !((EntityPlayer)playerEntity).capabilities.isCreativeMode)
				{
					srcItemStack.damageItem(1, playerEntity);
				}
			}
		}


		if (!world.isRemote)
		{
			double vecX = (double)(-MathHelper.sin(playerEntity.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(playerEntity.rotationPitch / 180.0F * (float)Math.PI));
			double vecY = (double)(-MathHelper.sin(playerEntity.rotationPitch / 180.0F * (float)Math.PI));
			double vecZ = (double)( MathHelper.cos(playerEntity.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(playerEntity.rotationPitch / 180.0F * (float)Math.PI));

			double deltaX = (double)(-MathHelper.sin(playerEntity.rotationYaw / 180.0F * (float)Math.PI));
			double deltaZ = (double)( MathHelper.cos(playerEntity.rotationYaw / 180.0F * (float)Math.PI));

			world.spawnEntityInWorld(new DeathSkull(world, playerEntity,playerEntity.posX+deltaX,playerEntity.posY+1,playerEntity.posZ+deltaZ,  vecX, vecY, vecZ));
		}
	}

}
