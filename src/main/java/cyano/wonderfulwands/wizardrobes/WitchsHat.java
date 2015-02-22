package cyano.wonderfulwands.wizardrobes;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	public final String itemName = "hat_witch";
	
	public WitchsHat() {
		super();
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+itemName);
	}

	
	@SideOnly(Side.CLIENT)
	@Override public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot){
		return cyano.wonderfulwands.ClientProxy.witchHatRenderer;
	}
	
}
