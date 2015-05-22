package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import cyano.wonderfulwands.projectiles.Fireball;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
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
		this.setCreativeTab(CreativeTabs.tabCombat);
	}


	@Override
	public int getBaseRepairCost() {
		return 1;
	}

	@Override public int getMaxItemUseDuration(ItemStack par1ItemStack){
		return cooldown;
	}

	@Override  public ItemStack onItemRightClick(ItemStack srcItemStack, World world, EntityPlayer playerEntity){
		 playerEntity.setItemInUse(srcItemStack, getMaxItemUseDuration(srcItemStack));
	        return srcItemStack;
	 }
	
	 /**
	     * Callback for item usage, invoked when right-clicking on a block. If the item 
	     * does something special on right clicking, he will have one of those. Return
	     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	     */
	@Override public boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, BlockPos coord, EnumFacing blockFace, float par8, float par9, float par10){
		return super.onItemUse(srcItemStack, playerEntity, world, coord,blockFace, par8, par9, par10);
	}
	 

	/**
	 * Invoked when the player releases the right-click button
	 */
	@Override public void onPlayerStoppedUsing (ItemStack srcItemStack, World world, EntityPlayer playerEntity, int timeRemain){
		super.onPlayerStoppedUsing(srcItemStack, world, playerEntity, timeRemain);
	}
	
	 /**
	  * This method is invoked after the item has been used for an amount of time equal to the duration 
	  * provided to the EntityPlayer.setItemInUse(stack, duration).
	  */
	 @Override public ItemStack onItemUseFinish (ItemStack srcItemStack, World world, EntityPlayer playerEntity)
	 { // 
		 
	        if (!playerEntity.capabilities.isCreativeMode)
	        {
	        	if(isOutOfCharge(srcItemStack)){
	        		// wand out of magic
	        		playSound(noChargeAttackSound,world,playerEntity);
	        		return srcItemStack;
	        	}
	        	srcItemStack.damageItem(1, playerEntity);
	        }

	        playSound("mob.endermen.portal",world,playerEntity);

	     
	        if (!world.isRemote)
	        {
	        	// try a few times and strike the highest point
	        	BlockPos origin = playerEntity.getPosition();
        		BlockPos target = origin; 
	        	for(int i = 0; i < 8; i++){
	        		BlockPos test = origin.add(world.rand.nextInt(AOEdiameter)+AOEsubtractor, 0, world.rand.nextInt(AOEdiameter)+AOEsubtractor);
		        	test = world.getTopSolidOrLiquidBlock(test);
		        	if(test.getY() > target.getY()){
		        		target = test;
		        	}
	        	}
	        	
	            world.addWeatherEffect(new EntityLightningBolt(world,target.getX(), target.getY(), target.getZ()));
	        }
	        return srcItemStack;
	    }

}
