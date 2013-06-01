package andrew.powersuits.common;

import andrew.powersuits.modules.*;
import net.machinemuse.api.IModularItem;
import net.machinemuse.powersuits.common.Config;
import net.machinemuse.powersuits.common.ModCompatability;
import net.machinemuse.powersuits.common.ModularPowersuits;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;

import java.util.Arrays;
import java.util.List;

public class AddonConfig extends Config {

    public static String category = "Modular Powersuits Addons";

    public static BlockTorch torch;

    public static boolean useAdvancedOreScannerMessage;
    public static boolean useOldAutoFeeder;
    public static boolean useCheatyLeatherRecipe;
    public static boolean useHUDStuff;
    public static boolean useDebugMode;

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
        addModule(new OreScannerModule(TOOLONLY));
        addModule(new LeafBlowerModule(TOOLONLY));
        addModule(new ThermalGeneratorModule(TORSOONLY));
        addModule(new MobRepulsorModule(TORSOONLY));
        //addModule(new BucketModule(TOOLONLY));
        //addModule(new DimensionalRift(TORSOONLY));

        if (ModCompatability.isIndustrialCraftLoaded()) {
            addModule(new EUReaderModule(TOOLONLY));
            addModule(new TreetapModule(TOOLONLY));
        }

        try {
            torch = (BlockTorch) Block.blocksList[Block.torchWood.blockID];
            addModule(new TorchPlacerModule(TOOLONLY));
        } catch (Exception e) {
            AddonLogger.logError("Some mod is overriding the default torch. MPSA Torch Placer Module is being disabled.");
        }
    }

    public static void loadOptions() {
        useAdvancedOreScannerMessage = getConfig().get(category, "Use Detailed Ore Scanner Message", true).getBoolean(true);
        useOldAutoFeeder = getConfig().get(category, "Use Old Auto Feeder Method", false).getBoolean(false);
        useCheatyLeatherRecipe = getConfig().get(category, "Use Cheaty Leather Recipe", true).getBoolean(true);
        useHUDStuff = getConfig().get(category, "Use HUD for certain modules (Auto Feeder, Torch Placer, etc.", true).getBoolean(true);
        useDebugMode = getConfig().get(category, "Use Debug mode. WARNING: WILL SPAM YOUR CONSOLE", false).getBoolean(false);
    }

    public static String getNetworkChannelName() {
        return "psa";
    }
}
