package andrew.addons.powersuits.common;

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

@Mod(modid = "powersuitsAddons", name = "Andrew's Modular Powersuits Addons", version = "@MOD_VERSION@", dependencies = "required-after:mmmPowersuits", acceptedMinecraftVersions = "[1.4.7,)")
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
clientPacketHandlerSpec = @SidedPacketHandler(channels = { "powersuitsAddons" }, packetHandler = MusePacketHandler.class),
serverPacketHandlerSpec = @SidedPacketHandler(channels = { "powersuitsAddons" }, packetHandler = MusePacketHandler.class))
public class ModularPowersuitsAddons {
	
	@Instance("ModularPowersuits")
	public static ModularPowersuitsAddons instance;

	/**
	 * Tells Forge what classes to load for the client and server proxies. These
	 * execute side-specific code like registering renderers (for the client) or
	 * different tick handlers (for the server).
	 */
	@SidedProxy(clientSide = "andrew.addons.powersuits.client.ClientProxy", serverSide = "andrew.addons.powersuits.common.CommonProxy")
	public static CommonProxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		instance = this;
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
