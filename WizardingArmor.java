package cyano.wizardrobes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;

public class WizardingArmor extends ItemArmor {

	public static final  int[] robeDamageReduction = new int[]{1, 1, 1, 1}; // head, shoulders, knees, and toes
	public static final  int robeEnchantibility = 40;
	public static final  int robeMaxDamageFactor = 15;
	
	
	public static final EnumArmorMaterial WIZARDROBE = EnumHelper.addArmorMaterial("WIZARDCLOTH",robeMaxDamageFactor, robeDamageReduction,robeEnchantibility);
	 
	
	public WizardingArmor(int itemID, int armorSlot, int renderIndex) {
		super(itemID, WIZARDROBE, renderIndex, armorSlot);
	//	// add icons
	//	func_111206_d("wizardrobes:robes"+armorSlot);
		this.setCreativeTab(CreativeTabs.tabCombat);
		
		
	}

	/**
     * Return whether this item is repairable in an anvil.
     */
    @Override public boolean getIsRepairable(ItemStack srcItemStack, ItemStack rawMaterial)
    {
    	// repair with gold nuggets
        return rawMaterial.itemID == Item.silk.itemID || (rawMaterial.itemID == Block.cloth.blockID && rawMaterial.getItemDamage() == 10);
    }
	

}
