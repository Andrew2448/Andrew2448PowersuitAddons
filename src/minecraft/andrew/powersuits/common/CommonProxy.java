package andrew.powersuits.common;

import andrew.powersuits.tick.ServerTickHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {

    private static ServerTickHandler serverTickHandler;

    public void registerHandlers() {
        serverTickHandler = new ServerTickHandler();
        TickRegistry.registerTickHandler(serverTickHandler, Side.SERVER);
        ServerTickHandler.load();
    }
}
