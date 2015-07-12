package cyano.wonderfulwands.wizardrobes;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import cyano.wonderfulwands.WonderfulWands;

/**
 * Wizards and Witches hats are expensive head-slot items that are rendered in 
 * 3D. A new hat can be combined with a potion to make the potion effect 
 * permanent (wearing the hat continuously reapplies the potion effect). 
 * 
 * @author cybergnome
 *
 */
public class WizardsHat extends  net.minecraft.item.ItemArmor {

	
	public static final int potionApplyInterval = 19;
	public static final int potionDuration = 11*20;
	
	
	public final String itemName = "hat_wizard";
	
	//private final WizardHatRenderer renderer;
	
	public WizardsHat( ) {
		super( WonderfulWands.NONARMOR, 0, 0);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+itemName);
		this.setCreativeTab(WonderfulWands.robesTab);
		// set values
		this.setMaxDamage(1);
		this.setHasSubtypes(true);
	}
	
	@Override public boolean isDamageable(){
		return false;
	}
	
	public void setPotionEffectID(ItemStack src, int potionEffectCode){
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("EffectID", potionEffectCode);
		src.setTagCompound(tag);
	}
	
	public Integer getPotionEffectID(ItemStack src){
		if(src.hasTagCompound() == false) return null;
		NBTTagCompound tag = src.getTagCompound();
		if(tag.hasKey("EffectID")){
			return tag.getInteger("EffectID");
		} else {
			return null;
		}
	}
	
	
	@SideOnly(Side.CLIENT)
	@Override public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot){
		return cyano.wonderfulwands.ClientProxy.wizardHatRenderer;
	}
	
	 @Override public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return WonderfulWands.MODID +":textures/models/armor/empty_armor_layer_1.png";
	}
	
	/**
	 * Return whether this item is repairable in an anvil.
	 */
	@Override public boolean getIsRepairable(ItemStack srcItemStack, ItemStack rawMaterial)
	{
		// repair with string or wool
		return false;
	}
	
	@Override public void onArmorTick(World world, EntityPlayer player, ItemStack src){
		super.onArmorTick(world, player, src);
		if(world.getTotalWorldTime() % (potionApplyInterval) == 0){
			if(this.getPotionEffectID(src) == null){
				if(player.getActivePotionEffects().isEmpty() == false){
					// soak up a potion effect
					Collection<PotionEffect> c = player.getActivePotionEffects();
					PotionEffect[] effect = c.toArray(new PotionEffect[c.size()]);
					int i = world.rand.nextInt(effect.length);
					int potionCode = effect[i].getPotionID();
					this.setPotionEffectID(src, potionCode);
					player.removePotionEffect(potionCode);
				}
			} else {
				player.addPotionEffect(new PotionEffect(this.getPotionEffectID(src),potionDuration));
			}
		} 
	}

	
   

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b){
		super.addInformation(stack,player,list,b);
		Integer potionID = this.getPotionEffectID(stack);
		if(potionID != null && Potion.potionTypes[potionID] != null){
			list.add(StatCollector.translateToLocal(Potion.potionTypes[potionID].getName()));
		}
	}

}
