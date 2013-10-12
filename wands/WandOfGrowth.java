package cyano.wonderfulwands.wands;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WandOfGrowth extends Wand {

	public static int cooldown = 10;
	
	public static int defaultCharges = 64;
	
	public WandOfGrowth(int itemID) {
		super(itemID);
		this.setCreativeTab(CreativeTabs.tabTools);
		setTextureName("wonderfulwands:wandIconGrowth");
        this.setMaxDamage(defaultCharges + 1);
	}

	@Override
	public int getUseCost() {
		return 1;
	}

	@Override
	public int getBaseRepairCost() {
		return 7;
	}
	
	@Override public boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, int targetX, int targetY, int targetZ, int par7, float par8, float par9, float par10){
		if (!playerEntity.capabilities.isCreativeMode)
        {
        	if(isOutOfCharge(srcItemStack)){
        		// wand out of magic
        		playSound(noChargeAttackSound,world,playerEntity);
        		return true;
        	}
        }
		boolean success = growBlock(playerEntity,world,targetX,targetY,targetZ);
		if(success){
			if (!playerEntity.capabilities.isCreativeMode)
	        {
	        	srcItemStack.damageItem(getUseCost(), playerEntity);
	        }
		}
		return success;
		
	}
	/**
	 * Acts like bonemeal, but also grows reeds and cactus and turns cobbleston/stone brick into their mossy equivalents
	 * @param playerEntity
	 * @param world
	 * @param targetX
	 * @param targetY
	 * @param targetZ
	 * @return True if anything happened, false otherwise (invalid target)
	 */
	protected boolean growBlock(EntityPlayer playerEntity, World world, int targetX, int targetY, int targetZ){

		int targetID = world.getBlockId(targetX, targetY, targetZ);
		ItemStack fauxItemStack = new ItemStack(Item.dyePowder);
		
		if(targetID == Block.cactus.blockID){
			// grow cactus
			int y = targetY+1;
			while(world.getBlockId(targetX, y, targetZ) == Block.cactus.blockID){
				y++;
			}
			if(world.getBlockId(targetX, y, targetZ) == 0){
				// place cactus block
				world.setBlock(targetX, y, targetZ, Block.cactus.blockID);
			}
			return true;
		} else if(targetID == Block.reed.blockID){
			// grow reeds
			int y = targetY+1;
			while(world.getBlockId(targetX, y, targetZ) == Block.reed.blockID){
				y++;
			}
			if(world.getBlockId(targetX, y, targetZ) == 0){
				// place reed block
				world.setBlock(targetX, y, targetZ, Block.reed.blockID);
			}
			return true;
		} else if(targetID == Block.cobblestone.blockID){
			// mossify cobblestone
			world.setBlock(targetX, targetY, targetZ, Block.cobblestoneMossy.blockID);
			return true;
		} else if(targetID == Block.stoneBrick.blockID){
			if( world.getBlockMetadata(targetX, targetY, targetZ) == 0){
				// mossify stonebrick
				world.setBlockMetadataWithNotify(targetX, targetY, targetZ, 1, 3);
				return true;
			}
		}
		return ItemDye.applyBonemeal(fauxItemStack, world, targetX, targetY, targetZ, playerEntity);
		
	}

}
