package andrew.powersuits.client;

import andrew.powersuits.common.CommonProxy;
import andrew.powersuits.tick.ClientTickHandler;
import andrew.powersuits.tick.RenderTickHandler;
import andrew.powersuits.tick.ServerTickHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {

    private static RenderTickHandler renderTickHandler;
    private static ClientTickHandler clientTickHandler;
    private static ServerTickHandler serverTickHandler;

    @Override
    public void registerHandlers() {
        renderTickHandler = new RenderTickHandler();
        TickRegistry.registerTickHandler(renderTickHandler, Side.CLIENT);

        clientTickHandler = new ClientTickHandler();
        TickRegistry.registerTickHandler(clientTickHandler, Side.CLIENT);
        ClientTickHandler.load();

        serverTickHandler = new ServerTickHandler();
        TickRegistry.registerTickHandler(serverTickHandler, Side.CLIENT);
    }
}
