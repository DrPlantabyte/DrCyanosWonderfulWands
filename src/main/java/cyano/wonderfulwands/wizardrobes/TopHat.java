package cyano.wonderfulwands.wizardrobes;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		super( WonderfulWands.NONARMOR, 0, EntityEquipmentSlot.HEAD);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+itemName);
		this.setCreativeTab(WonderfulWands.robesTab);
		// set values
		this.setMaxDamage(100);
	}


	@SideOnly(Side.CLIENT)
	public net.minecraft.client.model.ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, net.minecraft.client.model.ModelBiped biped){
		return cyano.wonderfulwands.ClientProxy.topHatRenderer;
	}
	
	@Override public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
    	return WonderfulWands.MODID +":textures/models/armor/empty_armor_layer_1.png";
    }
    
	 
}
