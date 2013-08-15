package andrew.powersuits.common;

import andrew.powersuits.book.ItemBook;
import andrew.powersuits.network.AndrewPacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
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

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        AddonConfig.setConfigFolderBase(event.getModConfigurationDirectory());
        AddonConfig.initItems();
        proxy.registerRenderers();
        //proxy.readManuals();
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        //book = new ItemBook(AddonConfig.manualID);
        AddonComponent.populate();
        AddonConfig.loadPowerModules();
        Localization.loadCurrentLanguage();
        AddonConfig.loadOptions();
        proxy.registerHandlers();
        NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        AddonRecipeManager.addRecipes();
        AddonConfig.getConfig().save();
    }
}
