package cyano.wonderfulwands.wizardrobes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

/**
 * 3D rendered head item that just looks cool.
 * 
 * @author cybergnome
 *
 */
public class TopHat extends net.minecraft.item.ItemArmor {

	//final TopHatRenderer renderer; // moved to ClientProxy for server compatibility
	
	public TopHat( int renderIndex) {
		super( WizardsHat.NONARMOR, renderIndex, 0);
		this.setCreativeTab(CreativeTabs.tabMisc);
		// set values
		this.setMaxDamage(100);
	}
	
	
	@SideOnly(Side.CLIENT)
	@Override public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot){
		return cyano.wonderfulwands.ClientProxy.topHatRenderer;
	}
	
	@Override public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
    	return "wizardrobes:textures/models/armor/empty_armor_layer.png";
    }
    

}
