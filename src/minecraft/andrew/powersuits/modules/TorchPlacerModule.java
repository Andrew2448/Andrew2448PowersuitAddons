package andrew.powersuits.modules;

import java.util.List;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.MuseItemUtils;
import net.machinemuse.api.moduletrigger.IPlayerTickModule;
import net.machinemuse.api.moduletrigger.IToggleableModule;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import andrew.powersuits.common.AddonUtils;

public class TorchPlacerModule extends PowerModuleBase implements IToggleableModule, IPlayerTickModule {
	public static final String MODULE_TORCH_PLACER = "Torch Placer";
	public static final String TORCH_ENERGY_CONSUMPTION = "Torch Fabrication Energy Consumption";
	public static final String MAX_TORCH_STORAGE = "Maximum Storage Amount";
	public TorchPlacerModule(List<IModularItem> validItems) {
		super(validItems);
		addBaseProperty(TORCH_ENERGY_CONSUMPTION, 50);
		addBaseProperty(MAX_TORCH_STORAGE, 64);
		addTradeoffProperty("Storage", MAX_TORCH_STORAGE, 192);
		addTradeoffProperty("Storage", TORCH_ENERGY_CONSUMPTION, 450);
		addInstallCost(new ItemStack(Block.torchWood, 1));
	    addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.servoMotor, 2));
	}
	
	@Override
	public String getTextureFile() {
		return "torchplacer";
	}

	@Override
	public String getCategory() {
		return MuseCommonStrings.CATEGORY_TOOL;
	}

	@Override
	public String getName() {
		return MODULE_TORCH_PLACER;
	}

	@Override
	public String getDescription() {
		return "Stored torches in an internal storage and places them in the world on use.";
	}

	@Override
	public void onPlayerTickActive(EntityPlayer player, ItemStack item) {
		IInventory inv = player.inventory;
		int torchesNeeded = (int) ModuleManager.computeModularProperty(item, MAX_TORCH_STORAGE) - AddonUtils.getTorchLevel(item);
		if (torchesNeeded > 0) {
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack stack = inv.getStackInSlot(i);
				if (stack != null && stack.itemID == Block.torchWood.blockID) {
					int loopTimes = torchesNeeded < stack.stackSize ? torchesNeeded : stack.stackSize;
					for (int i2 = 0; i2 < loopTimes; i2++) {
						AddonUtils.setTorchLevel(item, AddonUtils.getTorchLevel(item)+1);
						player.inventory.decrStackSize(i, 1);
						if (stack.stackSize == 0) {
							player.inventory.setInventorySlotContents(i, null);
						}
					}
					player.sendChatToPlayer("Torches: "+AddonUtils.getTorchLevel(item));
				}
			}
		}
	}

	@Override
	public void onPlayerTickInactive(EntityPlayer player, ItemStack item) {}
	
}
