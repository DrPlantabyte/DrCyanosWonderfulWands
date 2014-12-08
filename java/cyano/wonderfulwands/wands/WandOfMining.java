package cyano.wonderfulwands.wands;

import java.util.List;

import cyano.wonderfulwands.WonderfulWands;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WandOfMining extends Wand {
	public static final String itemName = "wand_mining";

	public static int cooldown = 10;
	
	public static int defaultCharges = 256;
	
	public WandOfMining() {
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
	protected boolean mineBlock(EntityPlayer playerEntity, World world, int targetX, int targetY, int targetZ){
		Block targetBlock = world.getBlock(targetX, targetY, targetZ);
		if(targetBlock == Blocks.bedrock){
			return false;
		}
		int targetMeta = world.getBlockMetadata(targetX, targetY, targetZ);
		
		if(Items.stone_pickaxe.canHarvestBlock(targetBlock,fauxPick) || targetBlock.getBlockHardness(world,targetX,targetY,targetZ) < 1.0F){
			// mine it
			List<ItemStack> drop = targetBlock.getDrops(world, targetX, targetY, targetZ, targetMeta, 0);
			if(!world.isRemote){
			for(int i = 0; i < drop.size(); i++){
				world.spawnEntityInWorld(new net.minecraft.entity.item.EntityItem(world, targetX, targetY, targetZ, drop.get(i)));
			}}
			world.setBlockToAir(targetX, targetY, targetZ);
			return true;
		} 
		return false;
		
	}

}
