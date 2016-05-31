package cyano.wonderfulwands.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class Fireball extends EntityLargeFireball{


	    public Fireball(World par1World, EntityLivingBase par2EntityLivingBase, double posX, double posY, double posZ, double vX, double vY, double vZ)
	    {
	        super(par1World, par2EntityLivingBase, vX, vY, vZ);
	        this.setPosition(posX, posY, posZ);
	        Double d3 = (double) MathHelper.sqrt_double(vX * vX + vY * vY + vZ * vZ);
	        this.accelerationX = vX / d3 * 0.1D;
	        this.accelerationY = vY / d3 * 0.1D;
	        this.accelerationZ = vZ / d3 * 0.1D;
	    }

	@Override
	protected void onImpact(RayTraceResult impact)
	{
		if (!this.worldObj.isRemote)
		{
			double radius = 2.0;
			if(impact.hitVec != null){
				AxisAlignedBB aoe = new AxisAlignedBB(impact.hitVec.xCoord-radius,impact.hitVec.yCoord-radius,impact.hitVec.zCoord-radius,
						impact.hitVec.xCoord+radius,impact.hitVec.yCoord+radius,impact.hitVec.zCoord+radius);
				List<EntityLivingBase> collateralDamage = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, aoe);
				for(EntityLivingBase victim : collateralDamage){
					victim.attackEntityFrom(DamageSource.magic, 5);
					victim.setFire(5);
				}
			}
		}
		super.onImpact(impact);
	}
}
