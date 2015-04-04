package cyano.wonderfulwands.entities;

import cyano.wonderfulwands.WonderfulWands;
import cyano.wonderfulwands.blocks.MageLight;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class LightWisp extends net.minecraft.entity.Entity{

	long offset = 0;
	int preference = 0;
	final short LIFESPAN = 30*20;
	short lifetime = LIFESPAN;
	
	public LightWisp(World w) {
		super(w);
		offset = w.rand.nextInt(8);
		preference = w.rand.nextInt(4);
	}

	@Override
	protected void entityInit() {
		// do nothing
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound root) {
		this.posX = root.getDouble("x");
		this.posY = root.getDouble("y");
		this.posZ = root.getDouble("z");
		this.lifetime = root.getShort("t");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound root) {
		root.setDouble("x", this.posX);
		root.setDouble("y", this.posY);
		root.setDouble("z", this.posZ);
		root.setShort("t", this.lifetime);
	}
	
	@Override
	public void onEntityUpdate(){
		super.onEntityUpdate();
		--lifetime;
		if(worldObj.isRemote)return;
		if(this.worldObj.getTotalWorldTime() % 8 == 0){
			BlockPos blockCoord = this.getPosition();
			int light = getLight(blockCoord);
			if(canPlace(blockCoord) && light < 8){
				turnIntoMageLight();
			} else {
				wanderFrom(blockCoord, light);
			}
		}
	}
	

	private int getLight(BlockPos coord){
		IBlockState bs = worldObj.getBlockState(coord);
		if(bs.getBlock().isSolidFullCube()) return 16;
		return worldObj.getLight(coord);
	}
	
	private boolean canPlace(BlockPos coord){
		return worldObj.isAirBlock(coord);
	}
	
	
	public void turnIntoMageLight(){
		worldObj.setBlockState(getPosition(), WonderfulWands.mageLight.getDefaultState());
		worldObj.removeEntity(this);
	}

	
	public void wanderFrom(BlockPos blockCoord, int light) {
		BlockPos target = blockCoord;
		BlockPos[] coords = new BlockPos[4];
		coords[0] = blockCoord.north();
		coords[1] = blockCoord.east();
		coords[2] = blockCoord.south();
		coords[3] = blockCoord.west();
		for(int i = 0; i < 4; i++){
			if(worldObj.isAirBlock(coords[i])){
				BlockPos down = coords[i].down();
				while(worldObj.isAirBlock(down)){
					coords[i] = down;
					down = coords[i].down();
				}
			}else{
				while(!worldObj.isAirBlock(coords[i])){
					coords[i] = coords[i].up();
				}
			}
			int l = getLight(coords[i]);
			if(l < light){
				light = l;
				target = coords[i];
			}
		}
		this.setPosition(target.getX()+0.5, target.getY(), target.getZ()+0.5);
	}
}
