package cyano.wonderfulwands.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Fireball extends EntityLargeFireball{


	    public Fireball(World par1World, EntityLivingBase par2EntityLivingBase, double posX, double posY, double posZ, double vX, double vY, double vZ)
	    {
	        super(par1World, par2EntityLivingBase, vX, vY, vZ);
	        this.setPosition(posX, posY, posZ);
	        Double d3 = (double)MathHelper.sqrt_double(vX * vX + vY * vY + vZ * vZ);
	        this.accelerationX = vX / d3 * 0.1D;
	        this.accelerationY = vY / d3 * 0.1D;
	        this.accelerationZ = vZ / d3 * 0.1D;
	    }

}
