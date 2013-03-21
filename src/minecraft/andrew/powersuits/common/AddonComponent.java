package andrew.powersuits.common;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.machinemuse.general.gui.MuseIcon;
import net.machinemuse.powersuits.common.Config;
import net.machinemuse.powersuits.common.ModularPowersuits;
import net.machinemuse.powersuits.item.ItemComponent;
import net.minecraft.item.ItemStack;

public class AddonComponent {
	public static ItemStack magnet;
	public static ItemStack solarPanel;
	public AddonComponent() {}
	
	public void populate() {
		solarPanel = ModularPowersuits.components.addComponent("componentSolarPanel", "Solar Panel", "A light sensitive device that will generate electricity from the sun.", "solarpanel");
		magnet = ModularPowersuits.components.addComponent("componentMagnet", "Magnet", "A metallic device that generates a magnetic field which pulls items towards the player.", "magnetb");
	}
	
}
