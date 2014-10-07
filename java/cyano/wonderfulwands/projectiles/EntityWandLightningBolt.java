package cyano.wonderfulwands.projectiles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class EntityWandLightningBolt  extends Entity{

	int life = 0;
	final int lifeSpan = 13;
	private EntityLivingBase owner;
	
	int tileX = 0, tileY = 0, tileZ = 0;
	
	public double length = 1;
	
	public EntityWandLightningBolt(World w) {
		super(w);
		this.posX = 0;
		this.posY = 0;
		this.posZ = 0;
		this.rotationPitch = 0;
		this.rotationYaw = 0;
	}
	
	public EntityWandLightningBolt(World w, EntityLivingBase owner, double x, double y, double z, float yaw, float pitch, double length) {
		super(w);
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		tileX = (int)x;
		tileY = (int)y;
		tileZ = (int)z;
		this.rotationPitch = pitch;
		this.rotationYaw = yaw;
		this.owner = owner;
		this.length = length;
	}
	
	@Override public void onUpdate(){
		super.onUpdate();
		if(++life > lifeSpan){
			this.setDead();
		}
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {

        this.tileX = tag.getShort("xTile");
        this.tileY = tag.getShort("yTile");
        this.tileZ = tag.getShort("zTile");
        this.length = tag.getByte("len") * 0.01d;

        if (tag.hasKey("direction", 9))
        {
            NBTTagList nbttaglist = tag.getTagList("direction", 6);
            this.motionX = nbttaglist.func_150309_d(0);
            this.motionY = nbttaglist.func_150309_d(1);
            this.motionZ = nbttaglist.func_150309_d(2);
        }
        else
        {
            this.setDead();
        }
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		//net.minecraft.entity.projectile.EntityFireball
		tag.setShort("xTile", (short)this.tileX);
		tag.setShort("yTile", (short)this.tileY);
		tag.setShort("zTile", (short)this.tileZ);
		tag.setByte("len", (byte)(this.length * 100f));
		tag.setTag("direction", this.newDoubleNBTList(new double[] {this.motionX, this.motionY, this.motionZ}));
		
	}
	
	 /**
     * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
     * posY, posZ, yaw, pitch
     */
    @SideOnly(Side.CLIENT)
    @Override public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_)
    {
        this.setPosition(p_70056_1_, p_70056_3_, p_70056_5_);
        this.setRotation(p_70056_7_, p_70056_8_);
    }

}
