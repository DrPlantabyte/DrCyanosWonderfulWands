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
			Vec3 origin = playerEntity.getPositionEyes(1f);
			double x = origin.xCoord;
			double y = origin.yCoord;
			double z = origin.zCoord;
			Vec3 vector = playerEntity.getLookVec();
			double dx = vector.xCoord;
        	double dy = vector.yCoord;
        	double dz = vector.zCoord;

        	
			for(int i = 0; i < maxRange; i++){
				double nx = x + dx;
				double ny = y + dy;
				double nz = z + dz;
				if(ny < 0 || ny > 255) break;
				BlockPos coord = new BlockPos(nx,ny,nz);
				if(world.isAreaLoaded(coord, 1, true) && world.isAirBlock(coord)){
					world.spawnParticle(EnumParticleTypes.PORTAL, 
							x + (world.rand.nextDouble() - 0.5), y + (world.rand.nextDouble() - 0.5), z + (world.rand.nextDouble() - 0.5), 
							(world.rand.nextFloat() - 0.5f) * 0.2f, (world.rand.nextFloat() - 0.5f) * 0.2f, (world.rand.nextFloat() - 0.5f) * 0.2f, 
							new int[0]);
					x = nx;
					y = ny;
					z = nz;
					continue;
				} else{
					x = coord.getX() + 0.5;
					y = coord.getY() + 1.0625;
					z = coord.getZ() + 0.5;
					break;
				}
			}

			playerEntity.setLocationAndAngles(x, y, z,playerEntity.rotationYaw, playerEntity.rotationPitch);
			playerEntity.setVelocity(0, 0, 0);
			playerEntity.fallDistance = 0;

	        playSound("mob.endermen.portal",world,playerEntity);
	}

}
