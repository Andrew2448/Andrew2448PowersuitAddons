package andrew.powersuits.modules;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.moduletrigger.IRightClickModule;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.machinemuse.utils.MuseCommonStrings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by User: Andrew2448
 * 1:42 AM 6/22/13
 */
public class AppEngWirelessModule extends PowerModuleBase implements IRightClickModule {
    public static final String MODULE_APPENG_WIRELESS = "AppEng Wireless Terminal";

    public AppEngWirelessModule(List<IModularItem> validItems) {
        super(validItems);
    }

    @Override
    public String getTextureFile() {
        return "redsquare";
    }

    @Override
    public String getCategory() {
        return MuseCommonStrings.CATEGORY_TOOL;
    }

    @Override
    public String getDataName() {
        return MODULE_APPENG_WIRELESS;
    }

    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal("module.appengWireless.name");
    }

    @Override
    public String getDescription() {
        return "An Applied Energistics wireless terminal integrated into your power tool.";
    }

    @Override
    public void onRightClick(EntityPlayer player, World world, ItemStack item) {

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
