package andrew.powersuits.common;

import java.util.ArrayList;

import net.machinemuse.general.gui.MuseIcon;
import net.machinemuse.powersuits.common.Config;
import net.machinemuse.powersuits.item.ItemComponent;
import net.minecraft.item.ItemStack;

public class AddonComponent extends ItemComponent {
	public static ItemStack magnet;
	public static ItemStack solarPanel;
	public AddonComponent() {
		super();
	}
	
	@Override
	public void populate() {
		solarPanel = addComponent("componentSolarPanel", "Solar Panel", "A light sensitive device that will generate electricity from the sun.", new MuseIcon("blueplate"));
		magnet = addComponent("componentMagnet", "Magnet", "A metallic device that generates a magnetic field which pulls items towards the player.", new MuseIcon("magnet"));
	}
}
