package andrew.powersuits.modules;

import java.util.List;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.IRightClickModule;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.MuseItemUtils;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import andrew.powersuits.common.ModularPowersuitsAddons;

public class InPlaceAssemblerModule extends PowerModuleBase implements IRightClickModule {
	public static final String MODULE_PORTABLE_CRAFTING = "In-Place Assembler";
	public InPlaceAssemblerModule(List<IModularItem> validItems) {
		super(validItems);
		addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
		addInstallCost(new ItemStack(Block.workbench, 1));
	}

	@Override
	public String getTextureFile() {
		return Block.workbench.func_94327_t_();
	}

	@Override
	public String getCategory() {
		return MuseCommonStrings.CATEGORY_SPECIAL;
	}

	@Override
	public String getName() {
		return MODULE_PORTABLE_CRAFTING;
	}

	@Override
	public String getDescription() {
		return "A larger crafting grid, on the go.";
	}

	@Override
	public void onRightClick(EntityPlayer player, World world, ItemStack item) {
		player.openGui(ModularPowersuitsAddons.instance, 0, world, (int)player.posX, (int)player.posY, (int)player.posZ);
	}

}
