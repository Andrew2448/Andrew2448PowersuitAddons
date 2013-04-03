package andrew.powersuits.common;

import andrew.powersuits.common.CommonProxy;
import net.machinemuse.powersuits.network.MusePacketHandler;
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
clientPacketHandlerSpec = @SidedPacketHandler(channels = { "psa" }, packetHandler = MusePacketHandler.class),
serverPacketHandlerSpec = @SidedPacketHandler(channels = { "psa" }, packetHandler = MusePacketHandler.class))
public class ModularPowersuitsAddons {
	
	public static GuiHandler guiHandler = new GuiHandler();
	
	@Instance("PowersuitAddons")
	public static ModularPowersuitsAddons instance;
	
	@SidedProxy(clientSide = "andrew.powersuits.client.ClientProxy", serverSide = "andrew.powersuits.common.CommonProxy")
	public static CommonProxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {}
	
	@Init
	public void load(FMLInitializationEvent event) {
		AddonComponent.populate();
		AddonConfig.loadPowerModules();
		proxy.registerHandlers();
		NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		AddonRecipeManager.addRecipes();
		AddonConfig.getConfig().save();
	}
}
