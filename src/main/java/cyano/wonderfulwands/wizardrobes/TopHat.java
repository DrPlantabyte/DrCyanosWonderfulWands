package cyano.wonderfulwands.wizardrobes;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

/**
 * 3D rendered head item that just looks cool.
 * 
 * @author cybergnome
 *
 */
public class TopHat extends net.minecraft.item.ItemArmor {

	//final TopHatRenderer renderer; // moved to ClientProxy for server compatibility
	public final String itemName = "tophat";
	public TopHat( ) {
		super( WonderfulWands.NONARMOR, 0, 0);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+itemName);
		this.setCreativeTab(WonderfulWands.robesTab);
		// set values
		this.setMaxDamage(100);
	}
	
	
	@SideOnly(Side.CLIENT)
	@Override public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot){
		return cyano.wonderfulwands.ClientProxy.topHatRenderer;
	}
	
	@Override public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
    	return WonderfulWands.MODID +":textures/models/armor/empty_armor_layer_1.png";
    }
    
	 
}
