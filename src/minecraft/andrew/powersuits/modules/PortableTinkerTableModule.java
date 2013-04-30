package andrew.powersuits.modules;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.moduletrigger.IRightClickModule;
import net.machinemuse.powersuits.common.Config;
import net.machinemuse.powersuits.common.ModularPowersuits;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by User: Andrew2448
 * 12:15 AM 4/30/13
 */
public class PortableTinkerTableModule extends PowerModuleBase implements IRightClickModule {

    public static final String MODULE_TINKER_TABLE = "In-Place Tinker Table";

    public PortableTinkerTableModule(List<IModularItem> validItems) {
        super(validItems);
        addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
        addInstallCost(new ItemStack(ModularPowersuits.tinkerTable, 1));
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
    public String getName() {
        return MODULE_TINKER_TABLE;
    }

    @Override
    public String getDescription() {
        return "A way to tweak your modules while on the move.";
    }

    @Override
    public void onRightClick(EntityPlayer player, World world, ItemStack item) {
        player.openGui(ModularPowersuits.instance, Config.Guis.GuiTinkerTable.ordinal(), world, (int)player.posX, (int)player.posY, (int)player.posZ);
    }

    @Override
    public void onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

    }

    @Override
    public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int par4) {

    }
}
