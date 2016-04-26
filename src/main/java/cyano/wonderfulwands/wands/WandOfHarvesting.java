package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WandOfHarvesting extends Wand {
	public static final String itemName = "wand_harvesting";

	public static int cooldown = 10;
	
	public static int defaultCharges = 128;
	
	public WandOfHarvesting() {
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
        this.setMaxDamage(defaultCharges + 1);
	}
	
	@Override
	public int getBaseRepairCost() {
		return 1;
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
	        	srcItemStack.damageItem(1, playerEntity);
	        }
			final int r = 3;
			for(int y = targetY+r; y >= targetY-r; y--){
				if(y < 0) continue;
				for(int x = targetX-r; x <= targetX+r; x++){
					for(int z = targetZ-r; z <= targetZ+r; z++){
						harvestBlock(world,new BlockPos(x,y,z));
					}
				}
			}

	        playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,world,playerEntity);
	        
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
		Material mat = w.getBlockState(coord).getMaterial();
		return mat == Material.CACTUS || mat == Material.LEAVES
				|| mat == Material.PLANTS || mat == Material.GOURD || mat == Material.VINE
				|| mat == Material.WEB || mat == Material.DRAGON_EGG || mat == Material.SPONGE;
	}
}
