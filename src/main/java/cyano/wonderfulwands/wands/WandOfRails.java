package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WandOfRails extends Wand {
	public static final String itemName = "wand_rails";

	public static int cooldown = 10;

	public static int defaultCharges = 1024;


	public WandOfRails() {
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
	}
	
	@Override
	public int getBaseRepairCost() {
		return 2;
	}
	
	@Override public boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, BlockPos coord, EnumFacing blockFace, float par8, float par9, float par10){
		return false;
		
	}


	/**
	 * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
	 * update it's contents.
	 */
	@Override
	public void onUpdate(ItemStack item, World w, Entity e, int itemSlot, boolean isSelected) {
		if(isSelected && !w.isRemote){
			if(this.placeRail(w,e.getPosition(),item) ){
				if(e instanceof EntityPlayer && !((EntityPlayer)e).capabilities.isCreativeMode){
					item.damageItem(1,(EntityPlayer)e);
				}
				super.playSound(SoundEvents.entity_experience_orb_pickup,w,e);
			}
		}
	}

	public boolean placeRail(World w, BlockPos pos, ItemStack item){
		IBlockState current = w.getBlockState(pos);
		BlockPos basePos = pos.down();
		IBlockState base = w.getBlockState(basePos);
		if(base.isSideSolid(w, basePos,EnumFacing.UP) && current.getBlock().isReplaceable(w,pos)){
			BlockPos lastPos = getLastPosition(item);
			IBlockState rail;
			if(lastPos == null // last position not set
					|| (lastPos.getY() != pos.getY() && (lastPos.getX() == pos.getX() || lastPos.getZ() == pos.getZ())) // slope
					|| (getUnpoweredRailCount(item) > 6 && (lastPos.getX() == pos.getX() || lastPos.getZ() == pos.getZ())) // distance from last powered rail
					|| (pos.distanceSq(lastPos) > 8 && (lastPos.getX() == pos.getX() || lastPos.getZ() == pos.getZ())) // discontinuous rail
			){
				rail = WonderfulWands.feyRailPowered.getDefaultState();
				resetUnpoweredRailCount(item);
			} else {
				// non-powered rail (can make turns)
				rail = WonderfulWands.feyRail.getDefaultState();
				incrementUnpoweredRailCount(item);
			}
			w.setBlockState(pos,rail,3);
			setLastPosition(pos,item);
			return true;
		}
		return false;
	}

	private static BlockPos getLastPosition(ItemStack i){
		if(i.hasTagCompound() && i.getTagCompound().hasKey("LP")){
			int[] coord = i.getTagCompound().getIntArray("LP");
			if(coord.length > 2){
				return new BlockPos(coord[0],coord[1],coord[2]);
			}
		}
		return null;
	}

	private static void setLastPosition(BlockPos pos, ItemStack i){
		NBTTagCompound itemTag;
		if(i.hasTagCompound()){
			itemTag = i.getTagCompound();
		} else {
			itemTag = new NBTTagCompound();
		}
		int[] coord = new int[3];
		coord[0] = pos.getX();
		coord[1] = pos.getY();
		coord[2] = pos.getZ();
		itemTag.setIntArray("LP",coord);
		i.setTagCompound(itemTag);
	}


	private static int getUnpoweredRailCount(ItemStack i){
		if(i.hasTagCompound() && i.getTagCompound().hasKey("UC")){
			return i.getTagCompound().getInteger("UC");
		}
		return 0;
	}

	private static void incrementUnpoweredRailCount(ItemStack i){
		NBTTagCompound itemTag;
		if(i.hasTagCompound()){
			itemTag = i.getTagCompound();
			itemTag.setInteger("UC",itemTag.getInteger("UC")+1);
		} else {
			itemTag = new NBTTagCompound();
			itemTag.setInteger("UC",1);
		}

		i.setTagCompound(itemTag);
	}

	private static void resetUnpoweredRailCount(ItemStack i){
		NBTTagCompound itemTag;
		if(i.hasTagCompound()){
			itemTag = i.getTagCompound();
		} else {
			itemTag = new NBTTagCompound();
		}
		itemTag.setInteger("UC",0);
		i.setTagCompound(itemTag);
	}

}
