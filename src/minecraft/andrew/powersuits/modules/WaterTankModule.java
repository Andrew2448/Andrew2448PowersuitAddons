package andrew.powersuits.modules;

import andrew.powersuits.common.AddonUtils;
import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.moduletrigger.IPlayerTickModule;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseHeatUtils;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

import java.util.List;

/**
 * Created by User: Andrew2448
 * 4:35 PM 6/21/13
 */
public class WaterTankModule extends PowerModuleBase implements IPlayerTickModule {
    public static String MODULE_WATER_TANK;
    public static final String WATER_TANK_SIZE = "Tank Size";
    ItemStack bucketWater = new ItemStack(Item.bucketWater);

    public WaterTankModule(List<IModularItem> validItems) {
        super(validItems);
        addBaseProperty(WATER_TANK_SIZE, 200);
        addBaseProperty(MuseCommonStrings.WEIGHT, 1000);
        addTradeoffProperty("Tank Size", WATER_TANK_SIZE, 800, " buckets");
        addTradeoffProperty("Tank Size", MuseCommonStrings.WEIGHT, 4000, "g");
        addInstallCost(new ItemStack(Item.bucketWater));
        addInstallCost(new ItemStack(Block.glass, 8));
        addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 2));
    }

    @Override
    public String getTextureFile() {
        return null;
    }

    @Override
    public Icon getIcon(ItemStack item) {
        return bucketWater.getIconIndex();
    }

    @Override
    public String getCategory() {
        return MuseCommonStrings.CATEGORY_ENVIRONMENTAL;
    }

    @Override
    public String getName() {
        MODULE_WATER_TANK = StatCollector.translateToLocal("module.waterTank.name");
        return MODULE_WATER_TANK;
    }

    @Override
    public String getDescription() {
        return "Store water which can later be used to cool yourself in emergency situations.";
    }

    @Override
    public void onPlayerTickActive(EntityPlayer player, ItemStack item) {
        if (player.isInWater() && AddonUtils.getWaterLevel(item) < ModuleManager.computeModularProperty(item, WATER_TANK_SIZE)) {
            AddonUtils.setWaterLevel(item, AddonUtils.getWaterLevel(item) + 1);
        }
        if (player.worldObj.isRaining() && (player.worldObj.getTotalWorldTime() % 5) == 0 && AddonUtils.getWaterLevel(item) < ModuleManager.computeModularProperty(item, WATER_TANK_SIZE)) {
            AddonUtils.setWaterLevel(item, AddonUtils.getWaterLevel(item) + 1);
        }
        if (MuseHeatUtils.getPlayerHeat(player) >= (MuseHeatUtils.getMaxHeat(player)-1) && AddonUtils.getWaterLevel(item) > 0) {
            MuseHeatUtils.coolPlayer(player, 1);
            AddonUtils.setWaterLevel(item, AddonUtils.getWaterLevel(item) - 1);
        }
    }

    @Override
    public void onPlayerTickInactive(EntityPlayer player, ItemStack item) {
    }
}
