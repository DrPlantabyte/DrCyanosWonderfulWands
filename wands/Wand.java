package cyano.wonderfulwands.wands;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class Wand extends Item {
	/** vanilla minecraft sound to play when you try to use a wand that has no charge left */
    public static String noChargeAttackSound = "random.bow";
	/**
	 * Default constructor
	 * @param itemID id of this item
	 */
	public Wand(int itemID) {
		super(itemID);
        this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.tabTools);
	}
	
	/** returns damage caused per use */
	public abstract int getUseCost();
	/**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.block;
    }
    
    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override public boolean getIsRepairable(ItemStack srcItemStack, ItemStack rawMaterial)
    {
    	// repair with gold nuggets
        return rawMaterial.itemID == Item.ingotGold.itemID;
    }
    /** returns true if the wand is on its last damage point */
    public boolean isOutOfCharge(ItemStack srcItemStack){
    	return srcItemStack.getItemDamage() >= (srcItemStack.getMaxDamage() - getUseCost());
    }
    /** plays a sound at the player location */
    protected void playSound(String soundID, World world, EntityPlayer playerEntity){
    	 if (!world.isRemote)
         {
    		 world.playSoundAtEntity(playerEntity, soundID, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
         }
    }
    
    public abstract int getBaseRepairCost();
    
}
