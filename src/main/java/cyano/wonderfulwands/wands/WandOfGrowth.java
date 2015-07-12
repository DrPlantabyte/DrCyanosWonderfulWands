package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
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

public class WandOfGrowth extends Wand {
	public static final String itemName = "wand_growth";

	public static int cooldown = 10;
	
	public static int defaultCharges = 128;
	
	static final PropertyEnum stoneblockVariantKey = PropertyEnum.create("variant", BlockStoneBrick.EnumType.class);
	
	public WandOfGrowth() {
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
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
		boolean success = growBlock(playerEntity,world,coord);
		if(success){
	        playSound("random.orb",world,playerEntity);
			if (!playerEntity.capabilities.isCreativeMode)
	        {
	        	srcItemStack.damageItem(1, playerEntity);
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
	protected boolean growBlock(EntityPlayer playerEntity, World world, BlockPos coord){
		IBlockState targetBS = world.getBlockState(coord); 
		Block targetBlock = targetBS.getBlock();
		ItemStack fauxItemStack = new ItemStack(Items.dye,1,15);

		int targetX = coord.getX();
		int targetY = coord.getY();
		int targetZ = coord.getZ();
		
		if(targetBlock == Blocks.cactus){
			// grow cactus
			int y = targetY+1;
			while(world.getBlockState(new BlockPos(targetX, y, targetZ)).getBlock() == Blocks.cactus && y < 255){
				y++;
			}
			if(world.isAirBlock(new BlockPos(targetX, y, targetZ))){
				// place cactus block
				world.setBlockState(new BlockPos(targetX, y, targetZ), Blocks.cactus.getDefaultState());
			}
			return true;
		} else if(targetBlock == Blocks.reeds){
			// grow reeds
			int y = targetY+1;
			while(world.getBlockState(new BlockPos(targetX, y, targetZ)).getBlock() == Blocks.reeds && y < 255){
				y++;
			}
			if(world.isAirBlock(new BlockPos(targetX, y, targetZ))){
				// place cactus block
				world.setBlockState(new BlockPos(targetX, y, targetZ), Blocks.reeds.getDefaultState());
			}
			return true;
		} else if(targetBlock == Blocks.cobblestone){
			// mossify cobblestone
			world.setBlockState(coord, Blocks.mossy_cobblestone.getDefaultState());
			return true;
		} else if(targetBlock == Blocks.stonebrick){
			if( targetBS.getProperties().get(stoneblockVariantKey) == BlockStoneBrick.EnumType.DEFAULT){
				// mossify stonebrick
				world.setBlockState(coord, Blocks.stonebrick.getStateFromMeta(1)); 
				return true;
			}
		}
		return ItemDye.applyBonemeal(fauxItemStack, world, coord, playerEntity); 
	}

}
