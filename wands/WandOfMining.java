package cyano.wonderfulwands.wands;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WandOfMining extends Wand {

	public static int cooldown = 10;
	
	public static int defaultCharges = 256;
	
	public WandOfMining(int itemID) {
		super(itemID);
		this.setCreativeTab(CreativeTabs.tabTools);
		setTextureName("wonderfulwands:wandIconMining");
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
		if (!playerEntity.capabilities.isCreativeMode)
        {
        	if(isOutOfCharge(srcItemStack)){
        		// wand out of magic
        		playSound(noChargeAttackSound,world,playerEntity);
        		return true;
        	}
        }
		boolean success = mineBlock(playerEntity,world,targetX,targetY,targetZ);
		if(success){
			if (!playerEntity.capabilities.isCreativeMode)
	        {
	        	srcItemStack.damageItem(getUseCost(), playerEntity);
	        }
		}
		return success;
		
	}
	/**
	 * Acts like iron pickaxe
	 * @param playerEntity
	 * @param world
	 * @param targetX
	 * @param targetY
	 * @param targetZ
	 * @return True if anything happened, false otherwise (invalid target)
	 */
	protected boolean mineBlock(EntityPlayer playerEntity, World world, int targetX, int targetY, int targetZ){
		int targetID = world.getBlockId(targetX, targetY, targetZ);
		if(targetID == Block.bedrock.blockID){
			return false;
		}
		int targetMeta = world.getBlockMetadata(targetX, targetY, targetZ);
		Block target;
		if (targetID > 0) {
			target = Block.blocksList[targetID];
		} else {
			return false;
		}
		
		
		if(Item.pickaxeStone.canHarvestBlock(target) || target.getBlockHardness(world,targetX,targetY,targetZ) < 1.0F){
			// mine it
			world.destroyBlock(targetX, targetY, targetZ, true);
			return true;
		} 
		return false;
		
	}

}
