package cyano.wonderfulwands.wands;

import java.util.List;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WandOfHarvesting extends Wand {
	public static final String itemName = "wand_harvesting";

	public static int cooldown = 10;
	
	public static int defaultCharges = 64;
	
	public WandOfHarvesting() {
		super();
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
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

	@Override public boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, BlockPos coord, EnumFacing blockFace, float par8, float par9, float par10){
		int targetX = coord.getX(),targetY = coord.getY(),targetZ = coord.getZ();
		if(isHarvestable(world,coord)){
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
						harvestBlock(world,new BlockPos(x,y,z));
					}
				}
			}

	        playSound("random.orb",world,playerEntity);
	        
			return true;
		}
		return false;
	}
	
	protected void harvestBlock(World w, BlockPos coord){
		float dropProbability = 1;
		int fortuneLevel = 0;
		if(isHarvestable(w,coord)){
			IBlockState bs = w.getBlockState(coord);
			w.setBlockToAir(coord);
			if(!w.isRemote){
				bs.getBlock().dropBlockAsItemWithChance(w,coord,bs,dropProbability,fortuneLevel);
			}
		}
	}
	
	protected boolean isHarvestable(World w, BlockPos coord){
		Material mat = w.getBlockState(coord).getBlock().getMaterial();
		return mat == Material.cactus || mat == Material.leaves 
				|| mat == Material.plants || mat == Material.gourd || mat == Material.vine
				|| mat == Material.web;
	}
}
