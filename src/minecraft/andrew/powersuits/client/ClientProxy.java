package andrew.powersuits.client;

import andrew.powersuits.common.AddonConfig;
import andrew.powersuits.common.CommonProxy;
import andrew.powersuits.tick.ClientTickHandler;
import andrew.powersuits.tick.RenderTickHandler;
import andrew.powersuits.tick.CommonTickHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {

    private static RenderTickHandler renderTickHandler;
    private static ClientTickHandler clientTickHandler;
    private static CommonTickHandler commonTickHandler;

    @Override
    public void registerHandlers() {
        super.registerHandlers();

        if (AddonConfig.useHUDStuff) {
            renderTickHandler = new RenderTickHandler();
            TickRegistry.registerTickHandler(renderTickHandler, Side.CLIENT);
        }

        clientTickHandler = new ClientTickHandler();
        TickRegistry.registerTickHandler(clientTickHandler, Side.CLIENT);
        ClientTickHandler.load();

        commonTickHandler = new CommonTickHandler();
        TickRegistry.registerTickHandler(commonTickHandler, Side.CLIENT);
    }
}
