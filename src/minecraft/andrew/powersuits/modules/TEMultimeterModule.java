package andrew.powersuits.modules;

import andrew.powersuits.common.AddonUtils;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import cofh.api.tileentity.ITileInfo;
import net.machinemuse.api.IModularItem;
import net.machinemuse.api.moduletrigger.IRightClickModule;
import net.machinemuse.powersuits.common.ModCompatability;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.List;

/**
 * Created by User: Andrew2448
 * 8:42 PM 6/17/13
 */
public class TEMultimeterModule extends PowerModuleBase implements IRightClickModule {
    public static final String MODULE_TE_MULTIMETER = "TE Multimeter";

    public TEMultimeterModule(List<IModularItem> validItems) {
        super(validItems);
        addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
        addInstallCost(ModCompatability.getThermexItem("multimeter", 1));
    }

    @Override
    public String getTextureFile() {
        return "multimeter";
    }

    @Override
    public String getCategory() {
        return MuseCommonStrings.CATEGORY_TOOL;
    }

    @Override
    public String getDataName() {
        return MODULE_TE_MULTIMETER;
    }

    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal("module.teMultimeter.name");
    }

    @Override
    public String getDescription() {
        return "A Thermal Expansion multimeter integrated in your power tool.";
    }

    @Override
    public void onRightClick(EntityPlayer player, World world, ItemStack item) {}

    @Override
    public void onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {}

    @Override
    public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (AddonUtils.isClientWorld(world)) {
            return false;
        }
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof ITileInfo) {
            ((ITileInfo)tileEntity).sendTileInfoToPlayer(player);
            return true;
        }
        if (tileEntity instanceof IPowerReceptor && ((IPowerReceptor)tileEntity).getPowerProvider() != null) {
            IPowerReceptor tilePowered = (IPowerReceptor)tileEntity;
            IPowerProvider tilePP = tilePowered.getPowerProvider();
            player.sendChatToPlayer("Energy Requested: " + minF(tilePowered.powerRequest(ForgeDirection.UNKNOWN), tilePP.getMaxEnergyStored() - tilePP.getEnergyStored()));
            return true;
        }
        return false;
    }

    public float minF(float a, float b) {
        return a < b ? a : b;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int par4) {}
}
