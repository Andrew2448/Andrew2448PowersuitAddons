package andrew.powersuits.modules;

import andrew.powersuits.common.AddonComponent;
import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.moduletrigger.IPlayerTickModule;
import net.machinemuse.api.moduletrigger.IToggleableModule;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.machinemuse.utils.ElectricItemUtils;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class MagnetModule extends PowerModuleBase implements IPlayerTickModule, IToggleableModule {
    public static final String MODULE_MAGNET = "Magnet";
    public static final String MAGNET_ENERGY_CONSUMPTION = "Energy Consumption";

    public MagnetModule(List<IModularItem> validItems) {
        super(validItems);
        addBaseProperty(MuseCommonStrings.WEIGHT, 1000);
        addInstallCost(MuseItemUtils.copyAndResize(AddonComponent.magnet, 2));
        addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
        addBaseProperty(MAGNET_ENERGY_CONSUMPTION, 200);
    }

    @Override
    public String getTextureFile() {
        return "magnetmodule";
    }

    @Override
    public String getCategory() {
        return MuseCommonStrings.CATEGORY_SPECIAL;
    }

    @Override
    public String getDataName() {
        return MODULE_MAGNET;
    }

    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal("module.magnet.name");
    }

    @Override
    public String getDescription() {
        return "Generates a magnetic field strong enough to attract items towards the player.         WARNING:                   This module drains power continuously. Turn it off when not needed. (Keybind menu: k)";
    }

    @Override
    public void onPlayerTickActive(EntityPlayer player, ItemStack stack) {
        if (ElectricItemUtils.getPlayerEnergy(player) > ModuleManager.computeModularProperty(stack, MAGNET_ENERGY_CONSUMPTION)) {
            if ((player.worldObj.getTotalWorldTime() % 20) == 0) {
                ElectricItemUtils.drainPlayerEnergy(player, ModuleManager.computeModularProperty(stack, MAGNET_ENERGY_CONSUMPTION));
            }
        }
    }

    @Override
    public void onPlayerTickInactive(EntityPlayer player, ItemStack item) {
    }
}
