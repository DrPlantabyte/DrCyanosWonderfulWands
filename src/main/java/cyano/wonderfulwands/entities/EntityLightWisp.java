package cyano.wonderfulwands.entities;

import cyano.wonderfulwands.WonderfulWands;
import cyano.wonderfulwands.blocks.MageLight;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;

public class EntityLightWisp extends net.minecraft.entity.EntityLivingBase{

	static final short LIFESPAN = 30*20;
	short lifeCounter = LIFESPAN;
	static final long UPDATE_INTERVAL = 8;
	private final double dt = 4.0 / UPDATE_INTERVAL;
	
	private long nextUpdateTime = 0;
	public EntityLightWisp(World w) {
		super(w);
		this.lifeCounter = LIFESPAN;
	}
	
	public EntityLightWisp(World w, BlockPos startingPosition) {
		this(w);
		this.posX = startingPosition.getX() + 0.5;
		this.posY = startingPosition.getY();
		this.posZ = startingPosition.getZ() + 0.5;
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
		}
		if(lifeCounter <= 0){
			worldObj.removeEntity(this);
		}
	}
	

	private int getLight(BlockPos coord){
		IBlockState bs = worldObj.getBlockState(coord);
		if(bs.getBlock().isSolidFullCube()) return 16;
		return worldObj.getChunkFromBlockCoords(coord).getLightFor(EnumSkyBlock.BLOCK, coord);
	}
	
	private boolean canPlace(BlockPos coord){
		return worldObj.isAirBlock(coord);
	}
	
	
	public void turnIntoMageLight(){
		worldObj.setBlockState(getPosition(), WonderfulWands.mageLight.getDefaultState());
		worldObj.removeEntity(this);
	}

	
	public void wanderFrom(BlockPos blockCoord, int light) {
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
		if(target == null || worldObj.rand.nextInt(16) == 0){
			final float r = 12;
			float theta = worldObj.rand.nextFloat() * 6.2832f;
			float dx = r*net.minecraft.util.MathHelper.sin(theta);
			float dz = r*net.minecraft.util.MathHelper.cos(theta);
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
	
	@Override
	public ItemStack getHeldItem() {
		return inv[0];
	}

	@Override
	public ItemStack getEquipmentInSlot(int p0) {
		return inv[0];
	}

	@Override
	public ItemStack getCurrentArmor(int p0) {
		return inv[0];
	}

	@Override
	public void setCurrentItemOrArmor(int p0, ItemStack p1) {
		// do nothing
	}

	private final ItemStack[] inv = new ItemStack[1];
	@Override
	public ItemStack[] getInventory() {
		return inv;
	}
	
}
