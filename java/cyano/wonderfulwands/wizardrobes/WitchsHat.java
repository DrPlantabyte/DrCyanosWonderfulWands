package cyano.wonderfulwands.wizardrobes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
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
	
	public WitchsHat(int renderIndex) {
		super(renderIndex);
		String name = "hat_witch";
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+name);
		this.setTextureName(WonderfulWands.MODID +":"+ name);
	}

	
	@SideOnly(Side.CLIENT)
	@Override public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot){
		return cyano.wonderfulwands.ClientProxy.witchHatRenderer;
	}
}
