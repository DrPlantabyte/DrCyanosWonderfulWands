package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import cyano.wonderfulwands.projectiles.EntityMagicMissile;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WandOfMagicMissile extends Wand {
	public static final String itemName = "wand_missiles";

	public static int cooldown = 10;
	
	public static int defaultCharges = 64;
	
	/**
	 * Constructor
	 * @param itemID id of this item
	 */
	public WandOfMagicMissile(){
		super();
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
		this.setTextureName(WonderfulWands.MODID +":"+ itemName);
		this.setCreativeTab(CreativeTabs.tabCombat);
        this.setMaxDamage(defaultCharges + 1);
	}
	
	public int getMaxItemUseDuration(ItemStack par1ItemStack){
		return cooldown;
	}
	
	 public ItemStack onItemRightClick(ItemStack srcItemStack, World world, EntityPlayer playerEntity){
		 playerEntity.setItemInUse(srcItemStack, getMaxItemUseDuration(srcItemStack));
	        return srcItemStack;
	 }
	
	 /**
	     * Callback for item usage, invoked when right-clicking on a block. If the item 
	     * does something special on right clicking, he will have one of those. Return
	     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	     */
	@Override public boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10){
		return super.onItemUse(srcItemStack, playerEntity, world, par4, par5, par6, par7, par8, par9, par10);
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
	 @Override public ItemStack onEaten (ItemStack srcItemStack, World world, EntityPlayer playerEntity)
	 { // 
		 
	        if (!playerEntity.capabilities.isCreativeMode)
	        {
	        	if(isOutOfCharge(srcItemStack)){
	        		// wand out of magic
	        		playSound(noChargeAttackSound,world,playerEntity);
	        		return srcItemStack;
	        	}
	        	srcItemStack.damageItem(getUseCost(), playerEntity);
	        }

	        playSound("mob.endermen.portal",world,playerEntity);

	        if (!world.isRemote)
	        {
	            world.spawnEntityInWorld(new EntityMagicMissile(world, playerEntity,  2.0F));
	        }
	        return srcItemStack;
	    }

	@Override
	public int getUseCost() {
		return 1;
	}
	
	@Override  public int getBaseRepairCost(){
    	return 3;
    }
}
