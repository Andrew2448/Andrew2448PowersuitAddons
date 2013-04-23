package andrew.powersuits.modules;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.moduletrigger.IPlayerTickModule;
import net.machinemuse.powersuits.common.ModCompatability;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.machinemuse.utils.ElectricItemUtils;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseHeatUtils;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by User: Andrew2448
 * 6:43 PM 4/23/13
 */
public class ThermalGeneratorModule extends PowerModuleBase implements IPlayerTickModule {
    public static final String MODULE_THERMAL_GENERATOR = "Thermal Generator";
    public static final String THERMAL_ENERGY_GENERATION = "Thermal Energy Generation";

    public ThermalGeneratorModule(List<IModularItem> validItems) {
        super(validItems);
        addBaseProperty(THERMAL_ENERGY_GENERATION, 25);
        addBaseProperty(MuseCommonStrings.WEIGHT, 1000);
        addTradeoffProperty("Energy Generated", THERMAL_ENERGY_GENERATION, 25, " Joules");
        addTradeoffProperty("Energy Generated", MuseCommonStrings.WEIGHT, 1000, "g");
        if (ModCompatability.isIndustrialCraftLoaded()) {
            addInstallCost(ModCompatability.getIC2Item("geothermalGenerator"));
            addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
        }
        else {
            addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 2));
            addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.basicPlating, 1));
        }
    }

    @Override
    public String getTextureFile() {
        //return "solarhelmet";
        return "redstar";
    }

    @Override
    public String getCategory() {
        return MuseCommonStrings.CATEGORY_ENERGY;
    }

    @Override
    public String getName() {
        return MODULE_THERMAL_GENERATOR;
    }

    @Override
    public String getDescription() {
        return "Generate power from extreme amounts of heat.";
    }

    @Override
    public void onPlayerTickActive(EntityPlayer player, ItemStack item) {
        double currentHeat = MuseHeatUtils.getPlayerHeat(player);
        double maxHeat = MuseHeatUtils.getMaxHeat(player);
        if (player.worldObj.getTotalWorldTime() % 20 == 0) {
            System.out.println("Current heat: "+currentHeat+" | Max heat: "+maxHeat);
            System.out.println("Percentile: "+(currentHeat/maxHeat)*100+"%");
            if (player.isBurning()) {
                ElectricItemUtils.givePlayerEnergy(player, 4*ModuleManager.computeModularProperty(item, THERMAL_ENERGY_GENERATION));
                System.out.println("Is Burning");
            }
            else if (currentHeat >= 400) {
                ElectricItemUtils.givePlayerEnergy(player, 2*ModuleManager.computeModularProperty(item, THERMAL_ENERGY_GENERATION));
                System.out.println("Is Above 400");
            }
            else if ((currentHeat/maxHeat) >= 0.9) {
                ElectricItemUtils.givePlayerEnergy(player, ModuleManager.computeModularProperty(item, THERMAL_ENERGY_GENERATION));
                System.out.println("Is Above 90%");
            }
        }
    }

    @Override
    public void onPlayerTickInactive(EntityPlayer player, ItemStack item) {}
}
