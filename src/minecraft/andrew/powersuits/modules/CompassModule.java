package andrew.powersuits.modules;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.moduletrigger.IToggleableModule;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import java.util.List;

/**
 * Created by User: Andrew2448
 * 11:12 PM 6/11/13
 */
public class CompassModule extends PowerModuleBase implements IToggleableModule {

    public static final String MODULE_COMPASS = "Compass";
    public static ItemStack compass = new ItemStack(Item.compass);

    public CompassModule(List<IModularItem> validItems) {
        super(validItems);
        addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
        addInstallCost(new ItemStack(Item.compass, 1));
    }

    @Override
    public String getTextureFile() {
        return null;
    }

    @Override
    public Icon getIcon(ItemStack item) {
        return compass.getIconIndex();
    }

    @Override
    public String getCategory() {
        return MuseCommonStrings.CATEGORY_SPECIAL;
    }

    @Override
    public String getName() {
        return MODULE_COMPASS;
    }

    @Override
    public String getDescription() {
        return "A compass on the run that shows you your direction, no matter where you are. Toggleable by keybinds.";
    }
}
