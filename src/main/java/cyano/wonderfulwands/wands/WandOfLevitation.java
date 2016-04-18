package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class WandOfLevitation extends Wand {
	public static final String itemName = "wand_levitation";



	public static int defaultCharges = 64;
	public static int range = 16;
	public static int duration = 5 * 20;


	public WandOfLevitation() {
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
		return false;
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

		Entity target;
		RayTraceResult trace = RayTrace.rayTraceBlocksAndEntities(world,range,playerEntity);
		if(trace == null) {
			target  = playerEntity;
		}else{
			target = trace.entityHit;
		}
		if(!(target instanceof EntityLivingBase)){
			target = playerEntity;
		}
		if(target instanceof EntityLivingBase){
			EntityLivingBase e = (EntityLivingBase)target;
			playSound(SoundEvents.entity_experience_orb_pickup,world,playerEntity);
			playSound(SoundEvents.entity_endermen_teleport,world,e);
			e.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("levitation"),duration,0));
			e.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("glowing"),duration,0));
			if (playerEntity instanceof EntityPlayer && !((EntityPlayer)playerEntity).capabilities.isCreativeMode)
			{
				srcItemStack.damageItem(1, playerEntity);
			}
		}
	}

}
