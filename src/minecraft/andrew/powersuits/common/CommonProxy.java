package andrew.powersuits.common;

import andrew.powersuits.tick.CommonTickHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {

    private static CommonTickHandler serverTickHandler;
    public static AndrewPacketHandler packetHandler;


    public void registerHandlers() {
        serverTickHandler = new CommonTickHandler();
        TickRegistry.registerTickHandler(serverTickHandler, Side.SERVER);
        CommonTickHandler.load();

        packetHandler = new AndrewPacketHandler();
        packetHandler.register();
    }
}
