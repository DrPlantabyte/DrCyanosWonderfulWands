package cyano.wonderfulwands.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
/** a non-pickupable arrow */
public class EntityMagicMissile extends net.minecraft.entity.projectile.EntityArrow{

	int life = 0;
	final int lifeSpan = 100;
	
	public EntityMagicMissile(World par1World)
    {
        super(par1World);
		init();
    }

	private void init() {
		this.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
		this.setDamage(2.5);
	}

	public EntityMagicMissile(World par1World, double par2, double par4, double par6)
    {
        super(par1World,par2,par4,par6);
		init();
    }

    public EntityMagicMissile(World w, EntityLivingBase shooter)
    {
        super(w, shooter);
		init();
		this.setAim(shooter, shooter.rotationPitch, shooter.rotationYaw, 0.0F, 3.0F, 1.0F); // set velocity from shooter
    }



	@Override
	protected ItemStack getArrowStack() {
		return null;
	}

}
