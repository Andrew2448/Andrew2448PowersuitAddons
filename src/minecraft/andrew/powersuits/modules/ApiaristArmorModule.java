package andrew.powersuits.modules;

import java.util.List;

import forestry.api.apiculture.IArmorApiarist;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ApiaristArmorModule extends PowerModuleBase implements IArmorApiarist {
	
	public static final String MODULE_APIARIST_ARMOR = "Grafter";
	public static final String APIARIST_ARMOR_ENERGY_CONSUMPTION = "Apiarist Armor Energy Consumption";
	
	public ApiaristArmorModule(List<IModularItem> validItems) {
		super(validItems);
		addBaseProperty(APIARIST_ARMOR_ENERGY_CONSUMPTION, 100);
		//addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
	    //addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.servoMotor, 2));
	}
	
	@Override
	public String getTextureFile() {
		return "torchplacer";
	}

	@Override
	public String getCategory() {
		return MuseCommonStrings.CATEGORY_SPECIAL;
	}

	@Override
	public String getName() {
		return MODULE_APIARIST_ARMOR;
	}

	@Override
	public String getDescription() {
		return "A set of Forestry apiarist armor integrated with your modular armor.";
	}

	@Override
	public boolean protectPlayer(EntityPlayer player, ItemStack armor, String cause, boolean doProtect) {
		return true;
	}
}
