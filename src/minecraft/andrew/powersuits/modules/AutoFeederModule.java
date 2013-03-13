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
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.ToggleablePowerModule;
import net.machinemuse.powersuits.powermodule.modules.PowerModuleBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.FoodStats;

public class AutoFeederModule extends PowerModuleBase implements IToggleableModule, IPlayerTickModule {
	public AutoFeederModule(List<IModularItem> validItems) {
		super(validItems);
		addBaseProperty(MuseCommonStrings.EATING_ENERGY_CONSUMPTION, 100);
		addBaseProperty(MuseCommonStrings.EATING_EFFICIENCY, 50);
		addTradeoffProperty("Efficiency", MuseCommonStrings.EATING_ENERGY_CONSUMPTION, 100);
		addTradeoffProperty("Efficiency", MuseCommonStrings.EATING_EFFICIENCY, 50);
		addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.servoMotor, 2));
		addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
	}
	
	@Override
	public MuseIcon getIcon(ItemStack item) {
		return MuseIcon.NEXUS_1_BLUE;
	}

	@Override
	public String getCategory() {
		return MuseCommonStrings.CATEGORY_ENVIRONMENTAL;
	}

	@Override
	public String getName() {
		return MuseCommonStrings.MODULE_AUTO_FEEDER;
	}

	@Override
	public String getDescription() {
		return "Whenever you're hungry, this module will grab the bottom-left-most food item from your inventory and feed it to you, storing the rest for later.";
	}

	@Override
	public void onPlayerTickActive(EntityPlayer player, ItemStack item) {
		ItemStack helmet = player.getCurrentArmor(3);
		double totalEnergy = ElectricItemUtils.getPlayerEnergy(player);
		IInventory inv = player.inventory;
		double foodLevel = (double) MuseItemUtils.getFoodLevel(helmet);
		double saturationLevel = MuseItemUtils.getSaturationLevel(helmet);
		double efficiency = ModuleManager.computeModularProperty(helmet, MuseCommonStrings.EATING_EFFICIENCY);
		FoodStats foodStats = player.getFoodStats();
		int foodNeeded = 20 - foodStats.getFoodLevel();
		for (int i = 0; i < inv.getSizeInventory() && foodNeeded > foodLevel; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null && stack.getItem() instanceof ItemFood) {
				ItemFood food = (ItemFood) stack.getItem();
				for (; stack.stackSize > 0 && foodNeeded > foodLevel; stack.stackSize--) {
					foodLevel += food.getHealAmount() * efficiency / 100.0;
					saturationLevel += food.getSaturationModifier() * efficiency / 100.0;
				}
				if (stack.stackSize == 0) {
					player.inventory.setInventorySlotContents(i, null);
				}
			}
		}
		double eatingEnergyConsumption = foodNeeded * ModuleManager.computeModularProperty(helmet, MuseCommonStrings.EATING_ENERGY_CONSUMPTION);
		int foodConsumed = (int) Math.min(foodNeeded, Math.min(foodLevel, eatingEnergyConsumption * totalEnergy));
		if (foodConsumed > 0) {
			if (saturationLevel >= 1.0F) {
				foodStats.addStats(foodConsumed, 1.0F);
				saturationLevel -= 1.0F;
			}
			else {
				foodStats.addStats(foodConsumed, 0.0F);
			}
			foodLevel -= foodConsumed;
			ElectricItemUtils.drainPlayerEnergy(player, eatingEnergyConsumption * foodConsumed);
		}
		MuseItemUtils.setFoodLevel(helmet, foodLevel);
		MuseItemUtils.setSaturationLevel(helmet, saturationLevel);
	}

	@Override
	public void onPlayerTickInactive(EntityPlayer player, ItemStack item) {}
}
