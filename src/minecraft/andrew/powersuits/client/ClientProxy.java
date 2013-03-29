package andrew.powersuits.client;

import andrew.powersuits.tick.RenderTickHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import andrew.powersuits.common.CommonProxy;

public class ClientProxy extends CommonProxy {
	
	private static RenderTickHandler renderTickHandler;
	
	@Override
	public void registerHandlers() {
		renderTickHandler = new RenderTickHandler();
		TickRegistry.registerTickHandler(renderTickHandler, Side.CLIENT);
	}
}
