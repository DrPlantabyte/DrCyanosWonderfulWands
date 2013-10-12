package cyano.wizardrobes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class WizardingArmorFeet extends WizardingArmor {

	public WizardingArmorFeet(int itemID, int renderIndex){
		super(itemID,3, renderIndex);
	}
	
	
	@SideOnly(Side.CLIENT) // better way!
	@Override public void registerIcons(IconRegister r){
		this.itemIcon = r.registerIcon("wizardrobes:robes3");
	}
	
	
	@Override public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
    {
		
		return "wizardrobes:textures/models/armor/robes_layer_1.png";

    }
	@Override public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return getArmorTexture(stack, entity, slot, (slot == 2 ? 2 : 1));
    }
}
