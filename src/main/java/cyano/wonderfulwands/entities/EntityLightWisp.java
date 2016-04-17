package cyano.wonderfulwands.entities;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.Random;

public class EntityLightWisp extends net.minecraft.entity.EntityLivingBase{

	static final short LIFESPAN = 30*20;
	short lifeCounter = LIFESPAN;
	static final long UPDATE_INTERVAL = 8;
	private final double dt = 4.0 / UPDATE_INTERVAL;
	private BlockPos lastPos = new BlockPos(0,1,0);

	private long nextUpdateTime = 0;
	public EntityLightWisp(World w) {
		super(w);
		this.lifeCounter = LIFESPAN;
		this.isImmuneToFire = true;
	}

	public EntityLightWisp(World w, BlockPos startingPosition) {
		this(w);
		this.posX = startingPosition.getX() + 0.5;
		this.posY = startingPosition.getY();
		this.posZ = startingPosition.getZ() + 0.5;
	}

	@Override
	protected void damageEntity(DamageSource src, float amount){
		// immune to wall suffocation
		if(DamageSource.inWall.equals(src.damageType)) return;
		super.damageEntity(src,amount);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound root) {
		super.readFromNBT(root);
		if(root.hasKey("t")) {
			this.lifeCounter = root.getShort("t");
		} else {
			this.lifeCounter = LIFESPAN;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound root) {
		super.writeToNBT(root);
		root.setShort("t", this.lifeCounter);
	}
	
	@Override
	public void onEntityUpdate(){
		super.onEntityUpdate();
		if(worldObj.isRemote)return;
		--lifeCounter;
		long time = this.worldObj.getTotalWorldTime();
		if(time > nextUpdateTime){
			nextUpdateTime = time + UPDATE_INTERVAL;
			BlockPos blockCoord = this.getPosition();
			int light = getLight(blockCoord);
			if(canPlace(blockCoord) && light < 8){
				turnIntoMageLight();
			} else {
				wanderFrom(blockCoord, light);
			}
			lastPos = blockCoord;
		}
		if(lifeCounter <= 0){
			worldObj.removeEntity(this);
		}
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public ItemStack getItemStackFromSlot(EntityEquipmentSlot s) {
		return null;
	}

	@Override
	public void setItemStackToSlot(EntityEquipmentSlot s, ItemStack i) {
		// do nothing
	}

	@Override
	public EnumHandSide getPrimaryHand() {
		return EnumHandSide.LEFT;
	}


	private int getLight(BlockPos coord){
		if(coord.getY() < 0 || coord.getY() > 255) return 16;
		IBlockState bs = worldObj.getBlockState(coord);
		if(bs.isFullBlock()) return 16;
		if(!(worldObj.getChunkFromBlockCoords(coord).isLoaded())) return 16;
		return worldObj.getChunkFromBlockCoords(coord).getLightFor(EnumSkyBlock.BLOCK, coord);
	}
	
	private boolean canPlace(BlockPos coord){
		return worldObj.isAirBlock(coord);
	}
	
	
	public void turnIntoMageLight(){
		worldObj.setBlockState(getPosition(), WonderfulWands.mageLight.getDefaultState());
		this.isDead = true;
		worldObj.removeEntity(this);
	}

	
	public void wanderFrom(BlockPos blockCoord, int light) {
		// first, check if neighbor is darker than here
		BlockPos target = null;
		BlockPos[] coords = new BlockPos[4];
		coords[0] = blockCoord.north();
		coords[1] = blockCoord.east();
		coords[2] = blockCoord.south();
		coords[3] = blockCoord.west();
		for(int i = 0; i < coords.length; i++){
			coords[i] = moveToGroundLevel(coords[i]);
			int l = getLight(coords[i]);
			if(l < light){
				light = l;
				target = coords[i];
			}
		}
		// if target is still null, then we are stuck in a local minimum, look further afield
		if(target == null){
			coords = new BlockPos[8];
			coords[0] = blockCoord.add(-12, 0, 0);
			coords[1] = blockCoord.add(-8, 0, -8);
			coords[2] = blockCoord.add(0, 0, -12);
			coords[3] = blockCoord.add(8, 0, -8);
			coords[4] = blockCoord.add(12, 0, 0);
			coords[5] = blockCoord.add(8, 0, 8);
			coords[6] = blockCoord.add(0, 0, 12);
			coords[7] = blockCoord.add(-8, 0, 8);
			for(int i = 0; i < coords.length; i++){
				coords[i] = moveToGroundLevel(coords[i]);
				int l = getLight(coords[i]);
				if(l < light){
					light = l;
					target = coords[i];
				}
			}
		}

		// if target is still null, then we are stuck in a brightly lit area, make long jump in random direction
		if(target == null){
			final float r = 24;
			float theta = this.getEntityWorld().rand.nextFloat() * 6.2832f;
			float dx = r * MathHelper.sin(theta);
			float dz = r * MathHelper.cos(theta);
			target = moveToGroundLevel(blockCoord.add(dx,0,dz));
		}

		this.setPosition(target.getX()+0.5, target.getY(), target.getZ()+0.5);
	}

	private BlockPos moveToGroundLevel(BlockPos coord){
		if(worldObj.isAirBlock(coord)){
			BlockPos down = coord.down();
			while(worldObj.isAirBlock(down) && down.getY() > 0){
				coord = down;
				down = coord.down();
			}
		}else{
			while(!worldObj.isAirBlock(coord) && coord.getY() < 255){
				coord = coord.up();
			}
		}
		return coord;
	}

	@Override public boolean canBreatheUnderwater() {
		return true;
	}


	private static void shuffle(Object[] in, Random prng){
		for(int i = in.length - 1; i > 1; i--){
			Object o = in[i];
			int r = prng.nextInt(i);
			in[i] = in[r];
			in[r] = o;
		}
	}

}
