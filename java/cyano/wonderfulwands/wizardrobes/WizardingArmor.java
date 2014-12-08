package cyano.wonderfulwands.wizardrobes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class WizardingArmor extends ItemArmor {

	public static final  int[] robeDamageReduction = new int[]{1, 1, 1, 1}; // head, shoulders, knees, and toes
	public static final  int robeEnchantibility = 40;
	public static final  int robeMaxDamageFactor = 15;
	private static final String itemName = "robes";
	
	public static final ArmorMaterial WIZARDROBE = net.minecraftforge.common.util.EnumHelper.addArmorMaterial("WIZARDCLOTH", robeMaxDamageFactor, robeDamageReduction, robeEnchantibility);
	 
	final private String color;
	
	public static String[] slotName = {"helmet","chestplate","leggings","boots"};
	
	public WizardingArmor(String color, int armorSlot, int renderIndex) {
		super(WIZARDROBE, renderIndex, armorSlot);
	//	// add icons
	//	func_111206_d("wizardrobes:robes"+armorSlot);
		String name = itemName+"_"+color+"_"+slotName[armorSlot];
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+name);
		this.setTextureName(WonderfulWands.MODID +":"+ name);
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.color = color;
	}

	/**
     * Return whether this item is repairable in an anvil.
     */
    @Override public boolean getIsRepairable(ItemStack srcItemStack, ItemStack rawMaterial)
    {
    	// repair with string or wool
        return rawMaterial.getUnlocalizedName().equals(Items.string.getUnlocalizedName()) 
        		|| rawMaterial.getUnlocalizedName().equals(Blocks.wool.getUnlocalizedName());
    }
	
    @Override public String getArmorTexture(ItemStack stack, Entity e, int slot, String layer){
    	// layer will either be "overlay" or null (ignore this variable)
    	return WonderfulWands.MODID+":textures/models/armor/robes_"+color+"_layer_"+(slot == 2 ? 2 : 1)+".png";
    }

}
