package cyano.wonderfulwands.wands;

import java.util.List;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WandOfHarvesting extends Wand {
	public static final String itemName = "wand_harvesting";

	public static int cooldown = 10;
	
	public static int defaultCharges = 64;
	
	public WandOfHarvesting() {
		super();
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
		this.setTextureName(WonderfulWands.MODID +":"+ itemName);
		this.setCreativeTab(CreativeTabs.tabTools);
        this.setMaxDamage(defaultCharges + 1);
	}

	@Override
	public int getUseCost() {
		return 1;
	}

	@Override
	public int getBaseRepairCost() {
		return 2;
	}

	@Override public boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, int targetX, int targetY, int targetZ, int par7, float par8, float par9, float par10){
		// TODO: stop it from making phantom blocks
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

	        playSound("random.orb",world,playerEntity);
	        
			return true;
		}
		return false;
	}
	
	protected void harvestBlock(World w, int x, int y, int z){
		
		if(isHarvestable(w,x,y,z)){
			List<ItemStack> drops = w.getBlock(x, y, z).getDrops(w, x, y, z, w.getBlockMetadata(x, y, z), 0);
			w.setBlockToAir(x, y, z);
			if(!drops.isEmpty() && !w.isRemote){
				for(ItemStack s : drops){
					w.spawnEntityInWorld(new net.minecraft.entity.item.EntityItem(w, x, y, z, s));
				}
			}
		}
	}
	
	protected boolean isHarvestable(World w, int x, int y, int z){
		Material mat = w.getBlock(x, y, z).getMaterial();
		return mat == Material.cactus || mat == Material.leaves 
				|| mat == Material.plants || mat == Material.gourd || mat == Material.vine
				|| mat == Material.web;
	}
}
