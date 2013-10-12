package cyano.wizardrobes;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class TopHatBlackModel extends ModelBase  {
	final ModelRenderer hat;
	
	public TopHatBlackModel(){
		float offset = 5f;
		int brimHeight = 1;
		hat = new ModelRenderer(this, 0, 0);
		hat.textureWidth = 16;
		hat.textureHeight = 16;
		hat.addBox(-4f, offset+brimHeight, -4f, 8, 9, 8, 0.0f);
		hat.addBox(-6.5f, offset, -6.5f, 13, brimHeight, 13, 0.0f);
	}
	
	public void render(float rotationYangle, float rotationXangle){
		hat.rotateAngleY = rotationYangle;
		hat.rotateAngleX = rotationXangle+(float)Math.PI;
		
		
		hat.render(1f/15f);
	}
}
