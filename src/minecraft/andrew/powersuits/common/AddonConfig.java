package andrew.powersuits.common;

import andrew.powersuits.modules.*;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.machinemuse.api.IModularItem;
import net.machinemuse.powersuits.common.Config;
import net.machinemuse.powersuits.common.ModCompatability;
import net.machinemuse.powersuits.common.ModularPowersuits;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class AddonConfig extends Config {

    public static String category = "Modular Powersuits Addons";

    public static BlockTorch torch;

    public static final String LANG_PATH = "/andrew/powersuits/lang/";
    public static String[] languages = {"en_US"};
    public static File configFolder;

    public static boolean useAdvancedOreScannerMessage;
    public static boolean useOldAutoFeeder;
    public static boolean useCheatyLeatherRecipe;
    public static boolean useHUDStuff;
    public static boolean useDebugMode;
    public static boolean use24hClock;

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
        addModule(new FlintAndSteelModule(TOOLONLY));
        addModule(new ClockModule(HEADONLY));
        addModule(new CompassModule(HEADONLY));
        addModule(new LightningModule(TOOLONLY));
        addModule(new WaterTankModule(TORSOONLY));
        //addModule(new BucketModule(TOOLONLY));
        //addModule(new DimensionalRift(TORSOONLY));

        if (ModCompatability.isIndustrialCraftLoaded()) {
            addModule(new EUReaderModule(TOOLONLY));
            addModule(new TreetapModule(TOOLONLY));
        }

        if (ModCompatability.isThermalExpansionLoaded()) {
            addModule(new TEMultimeterModule(TOOLONLY));
        }

        try {
            torch = (BlockTorch) Block.blocksList[Block.torchWood.blockID];
            addModule(new TorchPlacerModule(TOOLONLY));
        } catch (Exception e) {
            AddonLogger.logError("Some mod is overriding the default torch. MPSA Torch Placer Module is being disabled.");
        }
    }

    public static void setConfigFolderBase(File folder) {
        configFolder = new File(folder.getAbsolutePath() + "/andrew/powersuits/");
    }

    public static void extractLang(String[] langauges) {
        for (String lang : langauges) {
            InputStream inputStream = ModularPowersuitsAddons.INSTANCE.getClass().getResourceAsStream(LANG_PATH + lang + ".lang");
            try {
                File file = new File(configFolder.getAbsolutePath() + "/lang/" + lang + ".lang");
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                }
                OutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int read;
                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
                inputStream.close();
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                AddonLogger.logError("Error initializing MPSA localizations!");
            }
        }
    }

    public static void loadLang() {
        File file = new File(configFolder.getAbsolutePath() + "/lang/");
        for (File langFile : file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".lang");
            }
        })) {
            try {
                Properties langPack = new Properties();
                langPack.load(new FileInputStream(langFile));
                String lang = langFile.getName().replace(".lang", "");
                LanguageRegistry.instance().addStringLocalization(langPack, lang);
            } catch (Exception e) {
                e.printStackTrace();
                AddonLogger.logError("Error reading MPSA localizations!");
            }
        }
    }

    public static void loadOptions() {
        useAdvancedOreScannerMessage = getConfig().get(category, "Use Detailed Ore Scanner Message", true).getBoolean(true);
        useOldAutoFeeder = getConfig().get(category, "Use Old Auto Feeder Method", false).getBoolean(false);
        useCheatyLeatherRecipe = getConfig().get(category, "Use Cheaty Leather Recipe (Requires Thermal Expansion)", true).getBoolean(true);
        useHUDStuff = getConfig().get(category, "Use HUD for certain modules (Auto Feeder, Torch Placer, Compass, Clock, etc.", true).getBoolean(true);
        useDebugMode = getConfig().get(category, "Use Debug mode. WARNING: WILL PROBABLY SPAM YOUR CONSOLE", false).getBoolean(false);
        use24hClock = getConfig().get(category, "Use a 24h clock instead of 12h", false).getBoolean(false);
    }

    public static String getNetworkChannelName() {
        return "psa";
    }
}
