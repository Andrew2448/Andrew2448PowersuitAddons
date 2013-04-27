package andrew.powersuits.common;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.machinemuse.powersuits.network.MusePacketHandler;

/**
 * Created by User: Andrew2448
 * 1:29 PM 4/27/13
 */
public class AndrewPacketHandler extends MusePacketHandler {

    @Override
    public MusePacketHandler register() {
        addPacketType(1, AndrewPacketMagnetMode.class);

        NetworkRegistry.instance().registerChannel(this, "psa");
        return this;
    }
}
