package andrew.powersuits.modules;

import java.util.List;

import net.machinemuse.api.ElectricItemUtils;
import net.machinemuse.api.IModularItem;
import net.machinemuse.api.IPlayerTickModule;
import net.machinemuse.api.IToggleableModule;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.MuseItemUtils;
import net.machinemuse.general.gui.MuseIcon;
import net.machinemuse.powersuits.powermodule.modules.PowerModuleBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MagnetModule extends PowerModuleBase implements IPlayerTickModule, IToggleableModule {
	public static final String MODULE_MAGNET = "Magnet";
	//public static final String KINETIC_ENERGY_GENERATION = "Energy Generation Per 5 Blocks";
	public MagnetModule(List<IModularItem> validItems) {
		super(validItems);
		//addBaseProperty(MuseCommonStrings.WEIGHT, 1000);
		//addBaseProperty(KINETIC_ENERGY_GENERATION, 250);
		//addTradeoffProperty("Energy Generated", KINETIC_ENERGY_GENERATION, 750, " Joules");
		//addTradeoffProperty("Energy Generated", MuseCommonStrings.WEIGHT, 3000, "g");
		//addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.servoMotor, 2));
	    //addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
	}
	
	@Override
	public MuseIcon getIcon(ItemStack item) {
		return MuseIcon.NEXUS_1_RED;
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
		return "Generate power with your movement.";
	}

	@Override
	public void onPlayerTickActive(EntityPlayer player, ItemStack item) {
		
	}

	@Override
	public void onPlayerTickInactive(EntityPlayer player, ItemStack item) {}
}
