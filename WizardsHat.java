package cyano.wizardrobes;

import java.util.Collection;
import java.util.Random;

import TombStone.TombStone;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cyano.wizardrobes.client.ClientProxy;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumHelper;

/**
 * Wizards and Witches hats are expensive head-slot items that are rendered in 
 * 3D. A new hat can be combined with a potion to make the potion effect 
 * permanent (wearing the hat continuously reapplies the potion effect). 
 * 
 * @author cybergnome
 *
 */
public class WizardsHat extends  net.minecraft.item.ItemArmor {

	public static final  int[] damageReduction = new int[]{0, 0, 0, 0}; // head, shoulders, knees, and toes
	public static final  int enchantibility = 0;
	public static final  int maxDamageFactor = 10;
	
	public static final int potionApplyInterval = 10*20;
	public static final int potionDuration = 22*20;
	
	
	public static final EnumArmorMaterial NONARMOR = EnumHelper.addArmorMaterial("NONARMOR",maxDamageFactor, damageReduction,enchantibility);
	
	
	//private final WizardHatRenderer renderer;
	
	public WizardsHat(int itemID, int renderIndex) {
		super(itemID, NONARMOR, renderIndex, 0);
		this.setCreativeTab(CreativeTabs.tabBrewing);
		// set values
		this.setMaxDamage(1);
        this.setHasSubtypes(true);
	}
	
	@Override public boolean isDamageable(){
		return false;
	}
	@Override public boolean isRepairable(){
		return false;
	}
	
	public void setPotionEffectID(ItemStack src, int potionEffectCode){
		src.setItemDamage( potionEffectCode);
	}
	
	public int getPotionEffectID(ItemStack src){
		return src.getItemDamage();
	}
	
	@SideOnly(Side.CLIENT) // best way to register icons
	@Override public void registerIcons(IconRegister r){
		this.itemIcon = r.registerIcon("wizardrobes:wizardsHat");
	}
	
	@SideOnly(Side.CLIENT)
	@Override public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot){
		return ClientProxy.wizardHatRenderer;
	}
	
	/**
     * Return whether this item is repairable in an anvil.
     */
    @Override public boolean getIsRepairable(ItemStack srcItemStack, ItemStack rawMaterial)
    {
    	// repair item
        return rawMaterial.itemID == Item.silk.itemID;
    }
    
    @Override public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack src){
    	super.onArmorTickUpdate(world, player, src);
    	if(this.getPotionEffectID(src) != 0){
    		if(world.getWorldTime() % (potionApplyInterval) == 0){
    			player.addPotionEffect(new PotionEffect(this.getPotionEffectID(src),potionDuration));
    		}
    	} else {
    		if(player.getActivePotionEffects().isEmpty() == false){
    			// soak up a potion effect
    			Collection<PotionEffect> c = player.getActivePotionEffects();
    			PotionEffect[] effect = c.toArray(new PotionEffect[c.size()]);
    			Random r = new Random(world.getSeed() ^ (world.getWorldTime() / potionApplyInterval));
    			int i = r.nextInt(effect.length);
    			int potionCode = effect[i].getPotionID();
    			this.setPotionEffectID(src, potionCode);
    		}
    	}
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
