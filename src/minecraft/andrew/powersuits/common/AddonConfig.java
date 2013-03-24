package andrew.powersuits.common;

import java.util.Arrays;
import java.util.List;

import net.machinemuse.api.IModularItem;
import net.machinemuse.powersuits.common.Config;
import net.machinemuse.powersuits.common.ModularPowersuits;
import andrew.powersuits.modules.AutoFeederModule;
import andrew.powersuits.modules.InPlaceAssemblerModule;
import andrew.powersuits.modules.KineticGeneratorModule;
import andrew.powersuits.modules.MagnetModule;
import andrew.powersuits.modules.SolarGeneratorModule;

public class AddonConfig extends Config {
	
	public static void loadPowerModules() {
		List<IModularItem> ARMORONLY = Arrays.asList((IModularItem) ModularPowersuits.powerArmorHead, ModularPowersuits.powerArmorTorso, ModularPowersuits.powerArmorLegs, ModularPowersuits.powerArmorFeet);
		List<IModularItem> ALLITEMS = Arrays.asList((IModularItem) ModularPowersuits.powerArmorHead, ModularPowersuits.powerArmorTorso, ModularPowersuits.powerArmorLegs, ModularPowersuits.powerArmorFeet, ModularPowersuits.powerTool);
		List<IModularItem> HEADONLY = Arrays.asList((IModularItem) ModularPowersuits.powerArmorHead);
		List<IModularItem> TORSOONLY = Arrays.asList((IModularItem) ModularPowersuits.powerArmorTorso);
		List<IModularItem> LEGSONLY = Arrays.asList((IModularItem) ModularPowersuits.powerArmorLegs);
		List<IModularItem> FEETONLY = Arrays.asList((IModularItem) ModularPowersuits.powerArmorFeet);
		List<IModularItem> TOOLONLY = Arrays.asList((IModularItem) ModularPowersuits.powerTool);
		
		addModule(new InPlaceAssemblerModule(TOOLONLY));
		addModule(new KineticGeneratorModule(LEGSONLY));
		addModule(new SolarGeneratorModule(HEADONLY));
		addModule(new AutoFeederModule(HEADONLY));
		addModule(new MagnetModule(TORSOONLY));
		//addModule(new TorchPlacerModule(TOOLONLY));
	}
}
