package cyano.wonderfulwands.wizardrobes;

import java.util.Collection;
import java.util.Random;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

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
	
	public static final int potionApplyInterval = 19;
	public static final int potionDuration = 11*20;
	
	
	public static final ArmorMaterial NONARMOR = net.minecraftforge.common.util.EnumHelper.addArmorMaterial("NONARMOR",maxDamageFactor, damageReduction,enchantibility);
	
	
	//private final WizardHatRenderer renderer;
	
	public WizardsHat( int renderIndex) {
		super( NONARMOR, renderIndex, 0);
		String name = "hat_wizard";
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+name);
		this.setTextureName(WonderfulWands.MODID +":"+ name);
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
	/**
     * Queries the percentage of the 'Durability' bar that should be drawn.
     * 
     * @param stack The current ItemStack
     * @return 1.0 for 100% 0 for 0%
     */
	@Override public double getDurabilityForDisplay(ItemStack stack)
    {
        return 1;
    }
	
	public void setPotionEffectID(ItemStack src, int potionEffectCode){
		src.setItemDamage( potionEffectCode);
	}
	
	public int getPotionEffectID(ItemStack src){
		return src.getItemDamage();
	}
	
	
	@SideOnly(Side.CLIENT)
	@Override public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot){
		return cyano.wonderfulwands.ClientProxy.wizardHatRenderer;
	}
	
	/**
     * Return whether this item is repairable in an anvil.
     */
    @Override public boolean getIsRepairable(ItemStack srcItemStack, ItemStack rawMaterial)
    {
    	// repair with string or wool
        return rawMaterial.getUnlocalizedName().equals(Items.string.getUnlocalizedName()) 
        		|| rawMaterial.getUnlocalizedName().equals(Blocks.wool.getUnlocalizedName());
    }
    
    @Override public void onArmorTick(World world, EntityPlayer player, ItemStack src){
    	super.onArmorTick(world, player, src);
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
    

}
