package cyano.wizardrobes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cyano.wizardrobes.client.ClientProxy;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Wizards and Witches hats are expensive head-slot items that are rendered in 
 * 3D. A new hat can be combined with a potion to make the potion effect 
 * permanent (wearing the hat continuously reapplies the potion effect). 
 * 
 * @author cybergnome
 *
 */
public class WitchsHat extends WizardsHat {
	//private final WizardHatRenderer renderer;
	
	public WitchsHat(int itemID, int renderIndex) {
		super(itemID,renderIndex);
	}

	@SideOnly(Side.CLIENT) // best way to register icons
	@Override public void registerIcons(IconRegister r){
		this.itemIcon = r.registerIcon("wizardrobes:witchsHat");
	}
	
	@SideOnly(Side.CLIENT)
	@Override public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot){
		return ClientProxy.witchHatRenderer;
	}
}
