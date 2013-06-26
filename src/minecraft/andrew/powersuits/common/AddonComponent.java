package andrew.powersuits.common;

import net.machinemuse.powersuits.common.ModularPowersuits;
import net.minecraft.item.ItemStack;

public class AddonComponent {
    public static ItemStack magnet;
    public static ItemStack solarPanel;
    public static ItemStack computerChip;

    public static void populate() {
        if (ModularPowersuits.components != null) {
            solarPanel = ModularPowersuits.components.addComponent("componentSolarPanel", "A light sensitive device that will generate electricity from the sun.", "solarpanel");
            magnet = ModularPowersuits.components.addComponent("componentMagnet", "A metallic device that generates a magnetic field which pulls items towards the player.", "magnetb");
            computerChip = ModularPowersuits.components.addComponent("componentComputerChip", "An upgraded control circuit that contains a CPU which is capable of more advanced calculations.", "computerchip");
        }
        else {
            AddonLogger.logError("MPS components were not initialized, MPSA componenets will not be activated.");
        }
    }

}
