package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import cyano.wonderfulwands.entities.EntityLightWisp;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


/**
 * This wand spawns little wisps that seek out darkness and then turn into 
 * mage-light blocks
 * @author DrCyano
 *
 */
public class WandOfGreaterLight extends Wand {
	public static final String itemName = "wand_greater_light";

	public static int cooldown = 10;
	
	public static int defaultCharges = 64;
	
	public WandOfGreaterLight() {
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
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
		if(chargetime < 5) return;
		BlockPos center = playerEntity.getPosition();
		if (playerEntity instanceof EntityPlayer && !((EntityPlayer)playerEntity).capabilities.isCreativeMode)
		{
			if(isOutOfCharge(srcItemStack)){
				// wand out of magic
				playSound(noChargeAttackSound,world,playerEntity);
				return;
			}
			srcItemStack.damageItem(1, playerEntity);
		}
		

		if(!world.isRemote){
			EntityLightWisp[] e = new EntityLightWisp[9];
			e[0] = new EntityLightWisp(world,center);
			e[1] = new EntityLightWisp(world,center.add(-1, 0, -1));
			e[2] = new EntityLightWisp(world,center.add( 0, 0, -1));
			e[3] = new EntityLightWisp(world,center.add( 1, 0, -1));
			e[4] = new EntityLightWisp(world,center.add(-1, 0,  0));
			e[5] = new EntityLightWisp(world,center.add( 1, 0,  0));
			e[6] = new EntityLightWisp(world,center.add(-1, 0,  1));
			e[7] = new EntityLightWisp(world,center.add( 0, 0,  1));
			e[8] = new EntityLightWisp(world,center.add( 1, 0,  1));
			for(int i = 0; i < e.length; i++){
				world.spawnEntityInWorld(e[i]);
			}
		}
		
        playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE,world,playerEntity);
		
		return;
		
	}


}
