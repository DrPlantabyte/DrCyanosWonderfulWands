package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;

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
			double x = playerEntity.getPositionVector().xCoord;
			double y = playerEntity.getPositionVector().yCoord + 1.4;
			double z = playerEntity.getPositionVector().zCoord;
			double dx = (double)(-MathHelper.sin(playerEntity.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(playerEntity.rotationPitch / 180.0F * (float)Math.PI));
        	double dy = (double)(-MathHelper.sin(playerEntity.rotationPitch / 180.0F * (float)Math.PI));
        	double dz = (double)( MathHelper.cos(playerEntity.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(playerEntity.rotationPitch / 180.0F * (float)Math.PI));

        	
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

	        playSound("mob.endermen.portal",world,playerEntity);
	}

}
