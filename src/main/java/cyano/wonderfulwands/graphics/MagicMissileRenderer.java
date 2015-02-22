package cyano.wonderfulwands.graphics;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import cyano.wonderfulwands.WonderfulWands;
import cyano.wonderfulwands.projectiles.EntityMagicMissile;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class MagicMissileRenderer extends RenderArrow{

	public MagicMissileRenderer(RenderManager rm) {
		super(rm);
	}
	
	private static final ResourceLocation arrowTextures = new ResourceLocation(WonderfulWands.MODID+":textures/entity/magic_missile.png");
	/**
     * Returns the location of an entity's texture. 
     */
    protected ResourceLocation getEntityTexture(EntityMagicMissile p_110775_1_)
    {
        return arrowTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((EntityMagicMissile)p_110775_1_);
    }
}
