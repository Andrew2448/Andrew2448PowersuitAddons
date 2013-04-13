package andrew.powersuits.modules;

import java.util.List;

import forestry.api.arboriculture.IToolGrafter;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.MuseItemUtils;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GrafterModule extends PowerModuleBase implements IToolGrafter {
	
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
	public float getSaplingModifier(ItemStack stack, World world, int x, int y, int z) {
		return 100;
	}
}
