package cyano.wizardrobes;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;

public class WizardingArmorLegs extends WizardingArmor {

	public WizardingArmorLegs(int itemID, int renderIndex){
		super(itemID,2, renderIndex);
	}
	
	
	@SideOnly(Side.CLIENT) // better way!
	@Override public void registerIcons(IconRegister r){
		this.itemIcon = r.registerIcon("wizardrobes:robes2");
	}
	
	
	@Override public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
    {
		
		return "wizardrobes:textures/models/armor/robes_layer_2.png";

    }
	@Override public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return getArmorTexture(stack, entity, slot, (slot == 2 ? 2 : 1));
    }
}
