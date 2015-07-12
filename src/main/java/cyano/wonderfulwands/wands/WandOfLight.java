package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;

public class WandOfLight extends Wand {
	public static final String itemName = "wand_light";

	public static int cooldown = 10;
	
	public static int defaultCharges = 256;
	
	static final int MAX_RANGE = 64;
	
	public WandOfLight() {
		super(defaultCharges);
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
	}


	@Override
	public int getBaseRepairCost() {
		return 1;
	}
	@Override public int getMaxItemUseDuration(ItemStack par1ItemStack){
		return 1200;
	}
	@Override public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.BOW;
    }

	@Override  public ItemStack onItemRightClick(ItemStack srcItemStack, World world, EntityPlayer playerEntity){
		 playerEntity.setItemInUse(srcItemStack, getMaxItemUseDuration(srcItemStack));
	     return srcItemStack;
	 }
	@Override public boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, BlockPos coord, EnumFacing blockFace, float par8, float par9, float par10){
		return super.onItemUse(srcItemStack, playerEntity, world, coord,blockFace, par8, par9, par10);
	}
	@Override public void onPlayerStoppedUsing (ItemStack srcItemStack, World world, EntityPlayer playerEntity, int timeRemain){
		int chargetime = this.getMaxItemUseDuration(srcItemStack) - timeRemain;
		if(chargetime < 3) return;
		if (!playerEntity.capabilities.isCreativeMode)
        {
        	if(isOutOfCharge(srcItemStack)){
        		// wand out of magic
        		playSound(noChargeAttackSound,world,playerEntity);
        	}
        }
		
		net.minecraft.util.Vec3 vector = playerEntity.getLookVec();
		net.minecraft.util.Vec3 origin = (new Vec3(playerEntity.posX, playerEntity.posY + playerEntity.getEyeHeight(), playerEntity.posZ)).add(vector);
		
		boolean success = placeMageLight(world, origin, vector, MAX_RANGE);
		
		if(success){
	        playSound("note.pling",world,playerEntity);
			if (!playerEntity.capabilities.isCreativeMode)
	        {
	        	srcItemStack.damageItem(1, playerEntity);
	        }
		}
		
	}

	private boolean placeMageLight(World w, net.minecraft.util.Vec3 start, net.minecraft.util.Vec3 velocity, int rangeLimit) {
		BlockPos block = new BlockPos(start);
		if(w.isAirBlock(block)){
			net.minecraft.util.Vec3 pos = start; 
			for(int i = 0; i < rangeLimit; i++){
				net.minecraft.util.Vec3 next = pos.add(velocity);
				BlockPos nextBlock = new BlockPos(next);
				if(w.isAirBlock(nextBlock)){
					// keep moving
					w.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.xCoord, pos.yCoord, pos.zCoord, 
							(w.rand.nextFloat() - 0.5f) * 0.2f, (w.rand.nextFloat() - 0.5f) * 0.2f, (w.rand.nextFloat() - 0.5f) * 0.2f,
							new int[0]);
					pos = next;
					block = nextBlock;
					if(pos.yCoord < 0 ){
						pos = new net.minecraft.util.Vec3(pos.xCoord, 0, pos.yCoord);
						break;
					}
					if(pos.yCoord > 255){
						pos = new net.minecraft.util.Vec3(pos.xCoord, 255, pos.yCoord);
						break;
					}
				} else {
					//place mage light
					break;
				}
			}
			if(!w.isRemote){
				w.setBlockState(block, WonderfulWands.mageLight.getDefaultState());
			}
			return true;
		}
		return false;
	}

}
