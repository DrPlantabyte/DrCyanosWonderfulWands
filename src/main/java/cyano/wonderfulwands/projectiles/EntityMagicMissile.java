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
		this.canBePickedUp = EntityArrow.PickupStatus.DISALLOWED;
		this.setDamage(7);
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
    }



	@Override
	protected ItemStack getArrowStack() {
		return null;
	}

}
