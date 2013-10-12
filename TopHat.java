package cyano.wizardrobes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cyano.wizardrobes.client.ClientProxy;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemStack;

/**
 * 3D rendered head item that just looks cool.
 * 
 * @author cybergnome
 *
 */
public class TopHat extends net.minecraft.item.ItemArmor {

	//final TopHatRenderer renderer; // moved to ClientProxy for server compatibility
	
	public TopHat(int itemID, int renderIndex) {
		super(itemID, WizardsHat.NONARMOR, renderIndex, 0);
		this.setCreativeTab(CreativeTabs.tabMisc);
		// set values
		this.setMaxDamage(100);
	}
	
	@SideOnly(Side.CLIENT) // best way to register icons
	@Override public void registerIcons(IconRegister r){
		this.itemIcon = r.registerIcon("wizardrobes:tophat");
	}
	
	@SideOnly(Side.CLIENT)
	@Override public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot){
		return ClientProxy.topHatRenderer;
	}
	
	@Override public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
    	return "wizardrobes:textures/models/armor/empty_armor_layer.png";
    }
    
    @Override public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
    {
		return "wizardrobes:textures/models/armor/empty_armor_layer.png";
    }

}
