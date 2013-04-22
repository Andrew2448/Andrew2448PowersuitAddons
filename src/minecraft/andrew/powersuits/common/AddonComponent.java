package andrew.powersuits.common;

import net.machinemuse.powersuits.common.ModularPowersuits;
import net.minecraft.item.ItemStack;

public class AddonComponent {
	public static ItemStack magnet;
	public static ItemStack solarPanel;
    public static ItemStack computerChip;
	
	public static void populate() {
		solarPanel = ModularPowersuits.components.addComponent("componentSolarPanel", "Solar Panel", "A light sensitive device that will generate electricity from the sun.", "solarpanel");
		magnet = ModularPowersuits.components.addComponent("componentMagnet", "Magnet", "A metallic device that generates a magnetic field which pulls items towards the player.", "magnetb");
        computerChip = ModularPowersuits.components.addComponent("componentComputerChip", "Computer Chip", "An upgraded control circuit that contains a CPU which is capable of more advanced calculations.", "computerchip");
	}
	
}
