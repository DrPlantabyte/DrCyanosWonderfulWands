package cyano.wonderfulwands;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MyCreativeTab extends CreativeTabs {
	Item icon;
	/**
	 * Constructor
	 * @param unlocalizedName Unlocalized name for the tab
	 */
	public MyCreativeTab( String unlocalizedName) {
		super(unlocalizedName);
		this.icon = net.minecraft.init.Items.BED;
	}
	
	public void setIcon(Item i){
		this.icon = i;
	}

	/**
	 * Gets the item used in the tab icon
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public Item getTabIconItem() {
		return icon;
	}
}
