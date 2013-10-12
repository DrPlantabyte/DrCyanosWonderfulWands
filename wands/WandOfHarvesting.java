package cyano.wonderfulwands.wands;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WandOfHarvesting extends Wand {

	public static int cooldown = 10;
	
	public static int defaultCharges = 64;
	
	public WandOfHarvesting(int itemID) {
		super(itemID);
		this.setCreativeTab(CreativeTabs.tabTools);
		setTextureName("wonderfulwands:wandIconHarvesting");
        this.setMaxDamage(defaultCharges + 1);
	}

	@Override
	public int getUseCost() {
		return 1;
	}

	@Override
	public int getBaseRepairCost() {
		return 5;
	}

	@Override public boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, int targetX, int targetY, int targetZ, int par7, float par8, float par9, float par10){
		if(isHarvestable(world,targetX,targetY,targetZ)){
			if (!playerEntity.capabilities.isCreativeMode)
	        {
	        	if(isOutOfCharge(srcItemStack)){
	        		// wand out of magic
	        		playSound(noChargeAttackSound,world,playerEntity);
	        		return true;
	        	}
	        	srcItemStack.damageItem(getUseCost(), playerEntity);
	        }
			for(int y = targetY+1; y >= targetY-1; y--){
				if(y < 0) continue;
				for(int x = targetX-1; x <= targetX+1; x++){
					for(int z = targetZ-1; z <= targetZ+1; z++){
						harvestBlock(world,x,y,z);
					}
				}
			}
			
			return true;
		}
		return false;
	}
	
	protected void harvestBlock(World w, int x, int y, int z){
		if(isHarvestable(w,x,y,z)){
			//w.setBlockToAir(x, y, z);
			w.destroyBlock(x, y, z, true);
		}
	}
	
	protected boolean isHarvestable(World w, int x, int y, int z){
		Material mat = w.getBlockMaterial(x, y, z);
		return mat == Material.cactus || mat == Material.leaves 
				|| mat == Material.plants || mat == Material.pumpkin || mat == Material.vine
				|| mat == Material.web;
	}
}
