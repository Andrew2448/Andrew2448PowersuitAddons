package andrew.powersuits.modules;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.moduletrigger.IRightClickModule;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.machinemuse.utils.ElectricItemUtils;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by User: Andrew2448
 * 10:48 PM 6/11/13
 */
public class FlintAndSteelModule extends PowerModuleBase implements IRightClickModule {

    public static String MODULE_FLINT_AND_STEEL;
    public static final String IGNITION_ENERGY_CONSUMPTION = "Ignition Energy Consumption";
    public ItemStack fas = new ItemStack(Item.flintAndSteel);
    Random ran = new Random();

    public FlintAndSteelModule(List<IModularItem> validItems) {
        super(validItems);
        addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.servoMotor, 1));
        addInstallCost(new ItemStack(Item.flintAndSteel, 1));
        addBaseProperty(IGNITION_ENERGY_CONSUMPTION, 1000, "J");
    }

    @Override
    public String getTextureFile() {
        return null;
    }

    @Override
    public Icon getIcon(ItemStack item) {
        return fas.getIconIndex();
    }

    @Override
    public String getCategory() {
        return MuseCommonStrings.CATEGORY_TOOL;
    }

    @Override
    public String getName() {
        MODULE_FLINT_AND_STEEL = StatCollector.translateToLocal("module.flintAndSteel.name");
        return MODULE_FLINT_AND_STEEL;
    }

    @Override
    public String getDescription() {
        return "A portable igniter that creates fire through the power of energy.";
    }

    @Override
    public void onRightClick(EntityPlayer player, World world, ItemStack item) {}

    @Override
    public void onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        double energyConsumption = ModuleManager.computeModularProperty(itemStack, IGNITION_ENERGY_CONSUMPTION);
        if (energyConsumption < ElectricItemUtils.getPlayerEnergy(player)) {
            x += (side == 5 ? 1 : side == 4 ? -1 : 0);
            y += (side == 1 ? 1 : side == 0 ? -1 : 0);
            z += (side == 3 ? 1 : side == 2 ? -1 : 0);

            if (player.canPlayerEdit(x, y, z, side, itemStack)) {
                int id = world.getBlockId(x, y, z);

                if (id == 0) {
                    ElectricItemUtils.drainPlayerEnergy(player, energyConsumption);
                    world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "fire.ignite", 1.0F, ran.nextFloat() * 0.4F + 0.8F);
                    world.setBlock(x, y, z, Block.fire.blockID);
                }
            }
        }
    }

    @Override
    public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int par4) {}
}
