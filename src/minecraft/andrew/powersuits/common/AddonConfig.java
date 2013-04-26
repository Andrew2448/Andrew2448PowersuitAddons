package andrew.powersuits.common;

import andrew.powersuits.modules.*;
import net.machinemuse.api.IModularItem;
import net.machinemuse.powersuits.common.Config;
import net.machinemuse.powersuits.common.ModCompatability;
import net.machinemuse.powersuits.common.ModularPowersuits;

import java.util.Arrays;
import java.util.List;

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
        addModule(new TorchPlacerModule(TOOLONLY));
        //addModule(new OreScannerModule(TOOLONLY));
        addModule(new LeafBlowerModule(TOOLONLY));
        addModule(new ThermalGeneratorModule(TORSOONLY));
        addModule(new MobRepulsorModule(TORSOONLY));

        if (ModCompatability.isIndustrialCraftLoaded()) {
            addModule(new EUReaderModule(TOOLONLY));
            addModule(new TreetapModule(TOOLONLY));
        }
    }
}
