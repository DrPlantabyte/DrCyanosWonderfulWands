package cyano.wonderfulwands.wands;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public abstract class Wand extends Item {
	/** vanilla minecraft sound to play when you try to use a wand that has no charge left */
    public static SoundEvent noChargeAttackSound = SoundEvents.ENTITY_ITEM_PICKUP;

	public Wand(int numCharges) {
		super();
        this.maxStackSize = 1;
		this.setCreativeTab(WonderfulWands.wandsTab);
        this.setMaxDamage(numCharges + 1);
	}
	
	/**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.BLOCK;
    }
    
    private List<ItemStack> allowedItems = null;
    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override public boolean getIsRepairable(ItemStack srcItemStack, ItemStack rawMaterial)
    {
    	// repair with gold ingots
    	if(allowedItems == null)allowedItems = OreDictionary.getOres("ingotGold"); 
    	for(int i = 0; i < allowedItems.size(); i++){
    		if(allowedItems.get(i).getUnlocalizedName().equals(rawMaterial.getUnlocalizedName())) return true;
    	}
    	return false;
    }
    /** returns true if the wand is on its last damage point */
    public boolean isOutOfCharge(ItemStack srcItemStack){
    	return srcItemStack.getItemDamage() >= (srcItemStack.getMaxDamage() - 1);
    }
    /** plays a sound at the player location */
    protected void playSound(SoundEvent sound, World world, Entity playerEntity){
		playSound(world,playerEntity.getPositionVector().addVector(0,1,0),12, sound);
    }
    
    public abstract int getBaseRepairCost();

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(onItemUse(stack,player,w,pos,facing,hitX,hitY,hitZ)){
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	protected abstract boolean onItemUse(ItemStack srcItemStack, EntityPlayer playerEntity, World world, BlockPos coord, EnumFacing blockFace, float partialX, float partialY, float partialZ);

    
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b){
		super.addInformation(stack,player,list,b);
		StringBuilder sb = new StringBuilder();
		int max = stack.getMaxDamage() - 1;
		sb.append(max - stack.getItemDamage()).append('/').append(max);
		list.add(sb.toString());
	}

	protected void playSound(World w, Vec3d position, double range, SoundEvent sound){
		playSound(w,position,range,sound,1.0F,1.0F);
	}
	protected void playSound(World w, Vec3d position, double range, SoundEvent sound, float volume, float pitch){
		if(w.isRemote)return;
		AxisAlignedBB area = new AxisAlignedBB(
				position.xCoord - range,position.yCoord - range,position.zCoord - range,
				position.xCoord + range,position.yCoord + range,position.zCoord + range
				);

		List<EntityPlayerMP> players = w.getEntitiesWithinAABB(EntityPlayerMP.class, area);
		SPacketCustomSound soundPacket = new SPacketCustomSound(sound.getRegistryName().toString(), SoundCategory.PLAYERS,
				position.xCoord, position.yCoord, position.zCoord, volume, pitch);
		for(EntityPlayerMP player : players){
			player.playerNetServerHandler.sendPacket(soundPacket);
		}
	}
	protected void playFadedSound(World w, Vec3d position, double range, SoundEvent sound, float volume, float pitch){
		if(w.isRemote)return;
		AxisAlignedBB area = new AxisAlignedBB(
				position.xCoord - range,position.yCoord - range,position.zCoord - range,
				position.xCoord + range,position.yCoord + range,position.zCoord + range
		);
		List<EntityPlayerMP> players = w.getEntitiesWithinAABB(EntityPlayerMP.class, area);
		for(EntityPlayerMP player : players){
			float distSqr = (float)player.getPositionVector().squareDistanceTo(position);
			float localVolume = Math.min(volume,volume/distSqr);
			SPacketCustomSound soundPacket = new SPacketCustomSound(sound.getRegistryName().toString(), SoundCategory.PLAYERS,
					position.xCoord, position.yCoord, position.zCoord, localVolume, pitch);
			player.playerNetServerHandler.sendPacket(soundPacket);
		}
	}
}
