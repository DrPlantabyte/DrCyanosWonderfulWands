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

public class WandOfLight extends Wand {
	public static final String itemName = "wand_light";

	public static int cooldown = 10;
	
	public static int defaultCharges = 128;
	
	public WandOfLight() {
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
		return 1;
	}
	
	@Override public boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, 
			World world, int targetX, int targetY, int targetZ, 
			int blockFace, 
			float par8, float par9, float par10){
		if (!playerEntity.capabilities.isCreativeMode)
        {
        	if(isOutOfCharge(srcItemStack)){
        		// wand out of magic
        		playSound(noChargeAttackSound,world,playerEntity);
        		return true;
        	}
        }
		boolean success = false;
		
		switch(blockFace){
		case 0:
			// bottom face
			success = placeMageLight(world, targetX,targetY-1,targetZ);
			break;
		case 1:
			// top face
			success = placeMageLight(world, targetX,targetY+1,targetZ);
			break;
		case 2:
			// north face
			success = placeMageLight(world, targetX,targetY,targetZ-1);
			break;
		case 3:
			// south face
			success = placeMageLight(world, targetX,targetY,targetZ+1);
			break;
		case 4:
			// east face
			success = placeMageLight(world, targetX-1,targetY,targetZ);
			break;
		case 5:
			// west face
			success = placeMageLight(world, targetX+1,targetY,targetZ);
			break;
		}
		
		if(success){
			if (!playerEntity.capabilities.isCreativeMode)
	        {
	        	srcItemStack.damageItem(getUseCost(), playerEntity);
	        }
		}
		return success;
		
	}

	private boolean placeMageLight(World w, int x, int y, int z) {
		if(w.isAirBlock(x, y, z)){
			w.setBlock(x, y, z, WonderfulWands.mageLight);
			return true;
		} else{ 
			return false;
		}
	}

}
