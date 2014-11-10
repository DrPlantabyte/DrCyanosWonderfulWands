package cyano.wonderfulwands.wizardrobes;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class WizardHatModel extends ModelBase {
	final ModelRenderer hat;

	
	
	public WizardHatModel()
    {
		int h = 2;
		float offset = 5f;
		hat = new ModelRenderer(this, 0, 0);
		hat.textureWidth = 16;
		hat.textureHeight = 16;
		float layerHeight = 0.1f;
		for(int i = 0; i < 5; i++){
			if(i == 0){
				hat.addBox(-1.5f*(5-i),h*i+offset,-1.5f*(5-i),3*(5-i),2,3*(5-i),0.0f);
			}else {
				hat.addBox(-1f*(5-i),h*i+offset,-1f*(5-i),2*(5-i),2,2*(5-i),0.0f);
			}
		}
    }
	
	public void render(float rotationYangle, float rotationXangle){
		hat.rotateAngleY = rotationYangle;
		hat.rotateAngleX = rotationXangle+(float)Math.PI;
		
		
		hat.render(1f/15f);
	}
}
