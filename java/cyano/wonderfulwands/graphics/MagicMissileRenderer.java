package cyano.wonderfulwands.graphics;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cyano.wonderfulwands.WonderfulWands;
import cyano.wonderfulwands.projectiles.EntityMagicMissile;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class MagicMissileRenderer extends RenderArrow{

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
