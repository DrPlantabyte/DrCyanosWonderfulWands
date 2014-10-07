package cyano.wonderfulwands.projectiles;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
/** a non-pickupable arrow */
public class EntityMagicMissile extends net.minecraft.entity.projectile.EntityArrow{

	int life = 0;
	final int lifeSpan = 100;
	
	public EntityMagicMissile(World par1World)
    {
        super(par1World);
        this.canBePickedUp = 0;
    }

    public EntityMagicMissile(World par1World, double par2, double par4, double par6)
    {
        super(par1World,par2,par4,par6);
        this.canBePickedUp = 0;
    }

    public EntityMagicMissile(World par1World, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase, float par4, float par5)
    {
        super(par1World,par2EntityLivingBase,par3EntityLivingBase,par4,par5);
        this.canBePickedUp = 0;
    }

    public EntityMagicMissile(World par1World, EntityLivingBase par2EntityLivingBase, float par3)
    {
        super(par1World,par2EntityLivingBase,par3);
        this.canBePickedUp = 0;
    }
    
    @Override public void onUpdate(){
		super.onUpdate();
		if(++life > lifeSpan){
			this.setDead();
		}
	}

}
