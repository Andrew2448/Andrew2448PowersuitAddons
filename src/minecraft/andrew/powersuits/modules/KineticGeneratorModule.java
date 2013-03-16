package andrew.powersuits.modules;

import java.util.List;

import net.machinemuse.api.ElectricItemUtils;
import net.machinemuse.api.IModularItem;
import net.machinemuse.api.IPlayerTickModule;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.MuseItemUtils;
import net.machinemuse.general.gui.MuseIcon;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.modules.PowerModuleBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class KineticGeneratorModule extends PowerModuleBase implements IPlayerTickModule {
	public static final String MODULE_KINETIC_GENERATOR = "Kinetic Generator";
	public static final String KINETIC_ENERGY_GENERATION = "Energy Generation Per 5 Blocks";
	public KineticGeneratorModule(List<IModularItem> validItems) {
		super(validItems);
		addBaseProperty(MuseCommonStrings.WEIGHT, 1000);
		addBaseProperty(KINETIC_ENERGY_GENERATION, 250);
		addTradeoffProperty("Energy Generated", KINETIC_ENERGY_GENERATION, 750, " Joules");
		addTradeoffProperty("Energy Generated", MuseCommonStrings.WEIGHT, 3000, "g");
		addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.servoMotor, 2));
	    addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
	}
	
	@Override
	public MuseIcon getIcon(ItemStack item) {
		return MuseIcon.NEXUS_1_RED;
	}

	@Override
	public String getCategory() {
		return MuseCommonStrings.CATEGORY_ENERGY;
	}

	@Override
	public String getName() {
		return MODULE_KINETIC_GENERATOR;
	}

	@Override
	public String getDescription() {
		return "Generate power with your movement.";
	}

	@Override
	public void onPlayerTickActive(EntityPlayer player, ItemStack item) {
		if (!player.isAirBorne) {
			NBTTagCompound tag = MuseItemUtils.getMuseItemTag(item);
			boolean isNotWalking = (player.ridingEntity != null) || (player.isInWater());
			if ((!tag.hasKey("x")) || (isNotWalking))
				tag.setInteger("x", (int) player.posX);
			if ((!tag.hasKey("z")) || (isNotWalking))
				tag.setInteger("z", (int) player.posZ);
			double distance = Math.sqrt((tag.getInteger("x") - (int) player.posX) * (tag.getInteger("x") - (int) player.posX) + (tag.getInteger("z") - (int) player.posZ) * (tag.getInteger("z") - (int) player.posZ));
			if (distance >= 5.0D) {
				tag.setInteger("x", (int) player.posX);
				tag.setInteger("z", (int) player.posZ);
				ElectricItemUtils.givePlayerEnergy(player, ModuleManager.computeModularProperty(item, KINETIC_ENERGY_GENERATION));
			}
		}
	}

	@Override
	public void onPlayerTickInactive(EntityPlayer player, ItemStack item) {}
}
