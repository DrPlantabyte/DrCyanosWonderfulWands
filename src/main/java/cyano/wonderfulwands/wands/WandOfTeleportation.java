package cyano.wonderfulwands.wands;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cyano.wonderfulwands.WonderfulWands;

public class WandOfTeleportation extends Wand {
	public static final String itemName = "wand_teleportation";

	
	public static int defaultCharges = 64;
	
	public WandOfTeleportation() {
		super();
		this.setUnlocalizedName(WonderfulWands.MODID +"_"+ itemName);
		this.setCreativeTab(CreativeTabs.tabTools);
        this.setMaxDamage(defaultCharges + 1);
	}

	@Override
	public int getUseCost() {
		return 1;
	}

	@Override
	public int getBaseRepairCost() {
		return 4;
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
        		return;
        	}
        	srcItemStack.damageItem(getUseCost(), playerEntity);
        }

	        playSound("mob.endermen.portal",world,playerEntity);
			
			final int maxRange = 160;
			Vec3 origin = (new Vec3(playerEntity.posX, playerEntity.posY + playerEntity.getEyeHeight(), playerEntity.posZ));
			Vec3 vector = playerEntity.getLookVec();
			Vec3 pos = origin;
			Vec3 next = pos;
			BlockPos coord = new BlockPos(pos);
			
			for(int i = 0; i < maxRange; i++){
				next = pos.add(vector);
				if(next.yCoord < 0 || next.yCoord > 255) break;
				BlockPos nextBlock = new BlockPos(next);
				if(world.isAreaLoaded(coord, 1, true) ){
					if(world.isAirBlock(nextBlock)){
						world.spawnParticle(EnumParticleTypes.PORTAL, 
								pos.xCoord + (world.rand.nextDouble() - 0.5), pos.yCoord + (world.rand.nextDouble() - 0.5), pos.zCoord + (world.rand.nextDouble() - 0.5), 
								(world.rand.nextFloat() - 0.5f) * 0.2f, (world.rand.nextFloat() - 0.5f) * 0.2f, (world.rand.nextFloat() - 0.5f) * 0.2f, 
								new int[0]);
						pos = next;
						coord = nextBlock;
						continue;
					} else {
						if(world.isAirBlock(nextBlock.up())){
							coord = nextBlock.up();
						}
						break;
					}
				} else{
					break;
				}
			}

			playerEntity.setLocationAndAngles(coord.getX()+0.5,coord.getY()+0.25,coord.getZ()+0.5,playerEntity.rotationYaw, playerEntity.rotationPitch);
			if(world.isRemote)playerEntity.setVelocity(0, 0, 0);
			playerEntity.fallDistance = 0;

	        playSound("mob.endermen.portal",world,playerEntity);
	}

}
