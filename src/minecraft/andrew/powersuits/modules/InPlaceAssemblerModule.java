package andrew.powersuits.modules;

import andrew.powersuits.common.ModularPowersuitsAddons;
import net.machinemuse.api.IModularItem;
import net.machinemuse.api.moduletrigger.IRightClickModule;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class InPlaceAssemblerModule extends PowerModuleBase implements IRightClickModule {

    public static final String MODULE_PORTABLE_CRAFTING = "In-Place Assembler";

    public InPlaceAssemblerModule(List<IModularItem> validItems) {
        super(validItems);
        addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
        addInstallCost(new ItemStack(Block.workbench, 1));
    }

    @Override
    public String getTextureFile() {
        return "portablecrafting";
    }

    @Override
    public String getCategory() {
        return MuseCommonStrings.CATEGORY_SPECIAL;
    }

    @Override
    public String getDataName() {
        return MODULE_PORTABLE_CRAFTING;
    }

    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal("module.portableCraftingTable.name");
    }

    @Override
    public String getDescription() {
        return "A larger crafting grid, on the go.";
    }

    @Override
    public void onRightClick(EntityPlayer player, World world, ItemStack item) {
        player.openGui(ModularPowersuitsAddons.INSTANCE, 0, world, (int) player.posX, (int) player.posY, (int) player.posZ);
    }

    @Override
    public void onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {}

    @Override
    public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int par4) {}

}
