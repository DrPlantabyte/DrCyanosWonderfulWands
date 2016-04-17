package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import cyano.wonderfulwands.blocks.IllusoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WandOfIllusions extends Wand {
	public static final String itemName = "wand_illusion";

	public static int cooldown = 10;
	
	public static int defaultCharges = 64;
	
	public WandOfIllusions() {
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
        this.setMaxDamage(defaultCharges + 1);
	}


	@Override
	public int getBaseRepairCost() {
		return 2;
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
		boolean success = convertBlock(playerEntity,world,coord);
		if(success){
			playSound(SoundEvents.entity_experience_orb_pickup,world,playerEntity);
			if (!playerEntity.capabilities.isCreativeMode)
	        {
	        	srcItemStack.damageItem(1, playerEntity);
	        }
		}
		return success;
		
	}
	
	private final ItemStack fauxPick = new ItemStack(Items.stone_pickaxe);
	/**
	 * turns block into illusion
	 * @param playerEntity
	 * @param world
	 * @return True if anything happened, false otherwise (invalid target)
	 */
	protected boolean convertBlock(EntityPlayer playerEntity, World world, BlockPos coord){
		IBlockState targetBlock = world.getBlockState(coord);
		Block illusoryBlock = IllusoryBlock.getIllusionForBlock(targetBlock.getBlock());
		if(illusoryBlock == null) return false;
		
		IBlockState newBlock = illusoryBlock.getDefaultState();
		world.setBlockState(coord, newBlock);
		return true;
		
	}

}
