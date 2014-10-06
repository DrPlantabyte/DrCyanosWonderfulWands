package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WandOfIce extends Wand {
	public static final String itemName = "wand_ice";

	public static int cooldown = 10;
	
	public static int defaultCharges = 128;
	
	public WandOfIce() {
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
		if (!playerEntity.capabilities.isCreativeMode)
        {
        	if(isOutOfCharge(srcItemStack)){
        		// wand out of magic
        		playSound(noChargeAttackSound,world,playerEntity);
        		return true;
        	}
        }
		int blocksChanged = 0;
		for(int y = targetY+1; y >= targetY-1; y--){
			if(y < 0) continue;
			for(int x = targetX-1; x <= targetX+1; x++){
				for(int z = targetZ-1; z <= targetZ+1; z++){
					blocksChanged += freezeBlock(world,x,y,z);
				}
			}
		}
		if(blocksChanged > 0){
			srcItemStack.damageItem(getUseCost(), playerEntity);
	        playSound("random.orb",world,playerEntity);
			return true;
		}else {
			return false;
		}
	}
	
	protected int freezeBlock(World w, int x, int y, int z){
		Block target = w.getBlock(x, y, z);
		if(target == Blocks.water || target == Blocks.flowing_water){
			w.setBlock(x, y, z, Blocks.ice);
			return 1;
		} else if(target == Blocks.lava || target == Blocks.flowing_lava){
			w.setBlock(x, y, z, Blocks.cobblestone);
			return 1;
		}
		return 0;
	}

}
