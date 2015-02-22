package cyano.wonderfulwands.wands;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public abstract class Wand extends Item {
	/** vanilla minecraft sound to play when you try to use a wand that has no charge left */
    public static String noChargeAttackSound = "random.bow";
	/**
	 * Default constructor
	 * @param itemID id of this item
	 */
	public Wand() {
		super();
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
        return EnumAction.BLOCK;
    }
    
    private List<ItemStack> allowedItems = null;
    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override public boolean getIsRepairable(ItemStack srcItemStack, ItemStack rawMaterial)
    {
    	// repair with gold ingots
    	if(allowedItems == null)allowedItems = OreDictionary.getOres("ingotGold"); 
    	for(int i = 0; i < allowedItems.size(); i++){
    		if(allowedItems.get(i).getUnlocalizedName().equals(rawMaterial.getUnlocalizedName())) return true;
    	}
    	return false;
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
