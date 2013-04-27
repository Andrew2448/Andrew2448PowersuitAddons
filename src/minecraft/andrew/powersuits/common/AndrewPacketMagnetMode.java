package andrew.powersuits.common;

import andrew.powersuits.tick.ClientTickHandler;
import cpw.mods.fml.common.network.Player;
import net.machinemuse.powersuits.network.MusePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import java.io.DataInputStream;

/**
 * Created by User: Andrew2448
 * 1:30 PM 4/27/13
 */
public class AndrewPacketMagnetMode extends MusePacket {

    protected int entityID;

    public AndrewPacketMagnetMode(Player player, int id) {
        super(player);
        writeInt(id);
    }

    public AndrewPacketMagnetMode(DataInputStream data, Player player) {
        super(data, player);
        entityID = readInt();
    }

    public void handleClient(EntityClientPlayerMP player) {
        Minecraft mc = Minecraft.getMinecraft();
        ClientTickHandler.instance().addSMPMagneticItem(entityID, (World)mc.theWorld);
    }

    public void handleServer(EntityPlayerMP player) {

    }

}
