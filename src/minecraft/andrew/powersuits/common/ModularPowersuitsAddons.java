package andrew.powersuits.common;

import net.machinemuse.powersuits.common.Config;
import net.machinemuse.powersuits.common.ModCompatability;
import net.machinemuse.powersuits.common.RecipeManager;
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

@Mod(modid = "powersuitsAddons", name = "Andrew's Modular Powersuits Addons", version = "0.1.1", dependencies = "required-after:mmmPowersuits", acceptedMinecraftVersions = "[1.4.7,)")
@NetworkMod(clientSideRequired = true, serverSideRequired = false,
clientPacketHandlerSpec = @SidedPacketHandler(channels = { "psa" }, packetHandler = MusePacketHandler.class),
serverPacketHandlerSpec = @SidedPacketHandler(channels = { "psa" }, packetHandler = MusePacketHandler.class))
public class ModularPowersuitsAddons {
	
	@Instance("powersuitsAddons")
	public static ModularPowersuitsAddons instance;
	
	@SidedProxy(clientSide = "andrew.powersuits.client.ClientProxy", serverSide = "andrew.powersuits.common.CommonProxy")
	public static CommonProxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		//Derp
	}
	
	@Init
	public void load(FMLInitializationEvent event) {
		//proxy.registerHandlers();
		//proxy.registerRenderers();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		//proxy.postInit();
	}
}
