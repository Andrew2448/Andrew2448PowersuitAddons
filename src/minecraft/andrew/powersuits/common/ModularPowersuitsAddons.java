package andrew.powersuits.common;

import andrew.powersuits.book.ItemBook;
import andrew.powersuits.network.AndrewPacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "PowersuitAddons", name = "Andrew's Modular Powersuits Addons", version = "@VERSION@", dependencies = "required-after:mmmPowersuits", acceptedMinecraftVersions = "[1.5,)")
@NetworkMod(clientSideRequired = true, serverSideRequired = false,
        clientPacketHandlerSpec = @SidedPacketHandler(channels = {"psa"}, packetHandler = AndrewPacketHandler.class),
        serverPacketHandlerSpec = @SidedPacketHandler(channels = {"psa"}, packetHandler = AndrewPacketHandler.class))
public class ModularPowersuitsAddons {

    public static GuiHandler guiHandler = new GuiHandler();

    public static ItemBook book;

    @Instance("PowersuitAddons")
    public static ModularPowersuitsAddons INSTANCE;

    @SidedProxy(clientSide = "andrew.powersuits.client.ClientProxy", serverSide = "andrew.powersuits.common.CommonProxy")
    public static CommonProxy proxy;

    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
        AddonConfig.setConfigFolderBase(event.getModConfigurationDirectory());
        AddonConfig.extractLang(AddonConfig.languages);
        AddonConfig.initItems();
        proxy.registerRenderers();
        //proxy.readManuals();
    }

    @Init
    public void load(FMLInitializationEvent event) {
        //book = new ItemBook(AddonConfig.manualID);
        AddonComponent.populate();
        AddonConfig.loadPowerModules();
        AddonConfig.loadLang();
        AddonConfig.loadOptions();
        proxy.registerHandlers();
        NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
    }

    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
        AddonRecipeManager.addRecipes();
        AddonConfig.getConfig().save();
    }
}
