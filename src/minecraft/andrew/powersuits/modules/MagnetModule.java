package andrew.powersuits.modules;

import java.util.List;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.IPlayerTickModule;
import net.machinemuse.api.IToggleableModule;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.MuseItemUtils;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import andrew.powersuits.common.AddonComponent;

public class MagnetModule extends PowerModuleBase implements IPlayerTickModule, IToggleableModule {
	public static final String MODULE_MAGNET = "Magnet";
	public MagnetModule(List<IModularItem> validItems) {
		super(validItems);
		addBaseProperty(MuseCommonStrings.WEIGHT, 1000);
		addInstallCost(MuseItemUtils.copyAndResize(AddonComponent.magnet, 1));
	    addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
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
	public String getName() {
		return MODULE_MAGNET;
	}

	@Override
	public String getDescription() {
		return "Generates a magnetic field strong enough to attract items on the ground towards the player.";
	}

	@Override
	public void onPlayerTickActive(EntityPlayer player, ItemStack item) {
		
	}

	@Override
	public void onPlayerTickInactive(EntityPlayer player, ItemStack item) {}
}
