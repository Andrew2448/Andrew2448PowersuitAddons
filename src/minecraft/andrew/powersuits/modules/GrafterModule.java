package andrew.powersuits.modules;

import java.util.List;

import forestry.api.arboriculture.IToolGrafter;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.MuseItemUtils;
import net.machinemuse.api.electricity.ElectricItemUtils;
import net.machinemuse.api.moduletrigger.IBlockBreakingModule;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;

public class GrafterModule extends PowerModuleBase implements IBlockBreakingModule {
	
	public static final String MODULE_GRAFTER = "Grafter";
	public static final String GRAFTER_ENERGY_CONSUMPTION = "Grafter Energy Consumption";
	public static int USES = 5;
	
	public GrafterModule(List<IModularItem> validItems) {
		super(validItems);
		addBaseProperty(GRAFTER_ENERGY_CONSUMPTION, 100);
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
		return MODULE_GRAFTER;
	}

	@Override
	public String getDescription() {
		return "A Forestry grafter integrated with your power tool.";
	}

	@Override
	public boolean canHarvestBlock(ItemStack stack, Block block, int meta, EntityPlayer player) {
		if (stack.canHarvestBlock(block) || ForgeHooks.canToolHarvestBlock(block, meta, stack) || stack.getStrVsBlock(block) > 1) {
				return true;
		}
		return false;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, int blockID, int x, int y, int z, EntityPlayer player) {
		if (world.getBlockMaterial(x, y, z) == Material.leaves && ElectricItemUtils.getPlayerEnergy(player) > ModuleManager.computeModularProperty(stack, GRAFTER_ENERGY_CONSUMPTION)) {
			ElectricItemUtils.drainPlayerEnergy(player, ModuleManager.computeModularProperty(stack, GRAFTER_ENERGY_CONSUMPTION));
			return true;
		}
		return false;
	}

	@Override
	public void handleBreakSpeed(BreakSpeed event) {}
}
