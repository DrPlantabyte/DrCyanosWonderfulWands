package cyano.wonderfulwands.wands;

import java.util.List;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WandOfMining extends Wand {
	public static final String itemName = "wand_mining";

	public static int cooldown = 10;
	
	public static int defaultCharges = 256;
	
	public WandOfMining() {
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
        this.setMaxDamage(defaultCharges + 1);
	}


	@Override
	public int getBaseRepairCost() {
		return 1;
	}
	
	@Override public boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, BlockPos coord, EnumFacing blockFace, float par8, float par9, float par10){
		if (!playerEntity.capabilities.isCreativeMode)
        {
        	if(isOutOfCharge(srcItemStack)){
        		// wand out of magic
        		playSound(noChargeAttackSound,world,playerEntity);
        		return true;
        	}
        }
		boolean success = mineBlock(playerEntity,world,coord);
		if(success){
			if (!playerEntity.capabilities.isCreativeMode)
	        {
	        	srcItemStack.damageItem(1, playerEntity);
	        }
		}
		return success;
		
	}
	
	private final ItemStack fauxPick = new ItemStack(Items.stone_pickaxe);
	/**
	 * Acts like iron pickaxe
	 * @param playerEntity
	 * @param world
	 * @param targetX
	 * @param targetY
	 * @param targetZ
	 * @return True if anything happened, false otherwise (invalid target)
	 */
	protected boolean mineBlock(EntityPlayer playerEntity, World world, BlockPos coord){
		IBlockState targetBlock = world.getBlockState(coord);
		if(targetBlock.getBlock() == Blocks.bedrock){
			return false;
		}
		
		
		if(fauxPick.canHarvestBlock(targetBlock.getBlock()) || targetBlock.getBlock().getBlockHardness(world,coord) < 1.0F){
			// mine it
			int fortuneLevel = 0;
		//	if(!world.isRemote){ }
			world.setBlockToAir(coord);
			targetBlock.getBlock().dropBlockAsItemWithChance(world, coord, targetBlock, 1F, fortuneLevel);
			return true;
		} 
		return false;
		
	}

}
