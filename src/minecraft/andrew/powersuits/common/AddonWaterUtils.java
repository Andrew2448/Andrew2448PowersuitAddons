package andrew.powersuits.common;

import andrew.powersuits.modules.WaterTankModule;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.powersuits.item.ItemPowerArmorChestplate;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by User: Andrew2448
 * 4:52 PM 6/21/13
 */
public class AddonWaterUtils {

    public static double getPlayerWater(EntityPlayer player) {
        double water = 0;
        for (ItemStack stack : MuseItemUtils.getModularItemsInInventory(player)) {
            if (stack.getItem() instanceof ItemPowerArmorChestplate && MuseItemUtils.itemHasActiveModule(stack, WaterTankModule.MODULE_WATER_TANK)) {
                water = AddonUtils.getWaterLevel(stack);
            }
        }
        return water;
    }

    public static double getMaxWater(EntityPlayer player) {
        double water = 0;
        for (ItemStack stack : MuseItemUtils.getModularItemsInInventory(player)) {
            if (stack.getItem() instanceof ItemPowerArmorChestplate && MuseItemUtils.itemHasActiveModule(stack, WaterTankModule.MODULE_WATER_TANK)) {
                water = ModuleManager.computeModularProperty(stack, WaterTankModule.WATER_TANK_SIZE);
            }
        }
        return water;
    }
}
