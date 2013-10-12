package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.projectiles.MagicMissile;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WandOfHealing extends Wand {

	public static int cooldown = 10;
	
	public static int defaultCharges = 64;
	
	public WandOfHealing(int itemID) {
		super(itemID);
		this.setCreativeTab(CreativeTabs.tabTools);
		setTextureName("wonderfulwands:wandIconHealing");
        this.setMaxDamage(defaultCharges + 1);
	}

	@Override
	public int getUseCost() {
		return 1;
	}

	@Override
	public int getBaseRepairCost() {
		return 15;
	}

	
	@Override  public ItemStack onItemRightClick(ItemStack srcItemStack, World world, EntityPlayer playerEntity){
		if (!playerEntity.capabilities.isCreativeMode)
        {
        	if(isOutOfCharge(srcItemStack)){
        		// wand out of magic
        		playSound(noChargeAttackSound,world,playerEntity);
        		return srcItemStack;
        	}
        	srcItemStack.damageItem(getUseCost(), playerEntity);
        }
        playSound("random.pop",world,playerEntity);
        
        ItemStack hp = new ItemStack(Item.potion);
        hp.setItemDamage(0x0005 + /*0x0020 +*/ 0x4000);
        if (!world.isRemote)
        {
            world.spawnEntityInWorld(new EntityPotion(world, playerEntity,  hp));
        }
        
        return srcItemStack;
	}
}
