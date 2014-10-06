package cyano.wonderfulwands.wands;

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

public class WandOfGrowth extends Wand {
	public static final String itemName = "wand_growth";

	public static int cooldown = 10;
	
	public static int defaultCharges = 128;
	
	public WandOfGrowth() {
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
		return 3;
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
	 * Acts like bonemeal, but also grows reeds and cactus and turns cobblestone/stone brick into their mossy equivalents
	 * @param playerEntity
	 * @param world
	 * @param targetX
	 * @param targetY
	 * @param targetZ
	 * @return True if anything happened, false otherwise (invalid target)
	 */
	protected boolean growBlock(EntityPlayer playerEntity, World world, int targetX, int targetY, int targetZ){

		Block targetBlock = world.getBlock(targetX, targetY, targetZ);
		ItemStack fauxItemStack = new ItemStack(Items.dye,1,15);
		
		if(targetBlock == Blocks.cactus){
			// grow cactus
			int y = targetY+1;
			while(world.getBlock(targetX, y, targetZ) == Blocks.cactus){
				y++;
			}
			if(world.isAirBlock(targetX, y, targetZ)){
				// place cactus block
				world.setBlock(targetX, y, targetZ, Blocks.cactus);
			}
			return true;
		} else if(targetBlock == Blocks.reeds){
			// grow reeds
			int y = targetY+1;
			while(world.getBlock(targetX, y, targetZ) == Blocks.reeds){
				y++;
			}
			if(world.isAirBlock(targetX, y, targetZ)){
				// place reed block
				world.setBlock(targetX, y, targetZ, Blocks.reeds);
			}
			return true;
		} else if(targetBlock == Blocks.cobblestone){
			// mossify cobblestone
			world.setBlock(targetX, targetY, targetZ, Blocks.mossy_cobblestone);
			return true;
		} else if(targetBlock == Blocks.stonebrick){
			if( world.getBlockMetadata(targetX, targetY, targetZ) == 0){
				// mossify stonebrick
				world.setBlockMetadataWithNotify(targetX, targetY, targetZ, 1, 3);
				return true;
			}
		}
		return ItemDye.applyBonemeal(fauxItemStack, world, targetX, targetY, targetZ, playerEntity);
		
	}

}
