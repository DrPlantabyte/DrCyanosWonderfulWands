package cyano.wonderfulwands.graphics;

import cyano.wonderfulwands.WonderfulWands;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MagicMissileRenderer extends RenderArrow{

	public MagicMissileRenderer(RenderManager rm) {
		super(rm);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 *
	 * @param entity
	 */
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return arrowTextures;
	}

	private static final ResourceLocation arrowTextures = new ResourceLocation(WonderfulWands.MODID+":textures/entity/magic_missile.png");
}
