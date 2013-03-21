package andrew.powersuits.modules;

import java.util.List;

import andrew.powersuits.common.AddonComponent;
import net.machinemuse.api.IModularItem;
import net.machinemuse.api.IRightClickModule;
import net.machinemuse.api.IToggleableModule;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.MuseItemUtils;
import net.machinemuse.general.gui.MuseIcon;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TorchPlacerModule extends PowerModuleBase implements IToggleableModule, IRightClickModule {
	public static final String MODULE_TORCH_PLACER = "Torch Placer";
	public static final String TORCH_ENERGY_CONSUMPTION = "Torch Fabrication Energy Consumption";
	public TorchPlacerModule(List<IModularItem> validItems) {
		super(validItems);
		addBaseProperty(TORCH_ENERGY_CONSUMPTION, 1000);
		addInstallCost(new ItemStack(Block.torchWood, 1));
	    addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.servoMotor, 1));
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
		return "Fabricates torches from pure energy and places them in the world.";
	}

	@Override
	public void onRightClick(EntityPlayer playerClicking, World world, ItemStack item) {
		//world.setBlockAndMetadataWithNotify(playerClicking., par2, par3, par4, par5, par6);
		//playerClicking.
		//ElectricItemUtils.drainPlayerEnergy(playerClicking, 1000);
	}
	
}
