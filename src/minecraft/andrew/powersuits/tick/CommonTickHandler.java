package andrew.powersuits.tick;

import andrew.powersuits.common.AddonUtils;
import andrew.powersuits.network.AndrewPacketMagnetMode;
import andrew.powersuits.modules.MagnetModule;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.machinemuse.general.MuseLogger;
import net.machinemuse.powersuits.item.ItemPowerArmorChestplate;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;
import java.util.List;

/**
 * Created by User: Andrew2448
 * 12:46 PM 4/27/13
 */
public class CommonTickHandler implements ITickHandler {

    private static CommonTickHandler instance;

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        if(type.contains(TickType.PLAYER)) {
            EntityPlayer player;
            player = (EntityPlayer)tickData[0];
            ItemStack torso = player.getCurrentArmor(2);
            if (torso != null && torso.getItem() instanceof ItemPowerArmorChestplate) {
                if (MuseItemUtils.itemHasActiveModule(torso, MagnetModule.MODULE_MAGNET)) {
                    MuseLogger.logDebug("Module Installed, server.");
                    updateMagneticPlayer(player);
                }
            }
        }
    }

    private void updateMagneticPlayer(EntityPlayer player) {
        MuseLogger.logDebug("Magnet method entered, server.");
        float distancexz = 16;
        float distancey = 8;

        @SuppressWarnings("unchecked")
        List<EntityItem> items = player.worldObj.getEntitiesWithinAABB(EntityItem.class, player.boundingBox.expand(distancexz, distancey, distancexz));
        for(EntityItem item : items) {
            if(item.delayBeforeCanPickup > 0) {
                continue;
            }

            if(!AddonUtils.canItemFitInInventory(player, item.getEntityItem())) {
                continue;
            }

            if(item.delayBeforeCanPickup == 0 && AddonUtils.isServerSide()) {
                AndrewPacketMagnetMode packet = new AndrewPacketMagnetMode((Player)player, item.entityId);
                PacketDispatcher.sendPacketToPlayer(packet.getPacket250(), (Player) player);
                MuseLogger.logDebug("Packet Sent.");
            }

            double dx = player.posX - item.posX;
            double dz = player.posZ - item.posZ;
            double absxz = Math.sqrt(dx * dx + dz * dz);

            if(absxz > distancexz) {
                continue;
            }
            if(absxz < 1) {
                item.onCollideWithPlayer(player);
            }
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {

    }

    public static void load() {
        instance = new CommonTickHandler();
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.PLAYER);
    }

    @Override
    public String getLabel() {
        return "MPSA: Common Tick";
    }

    public static CommonTickHandler instance() {
        return instance;
    }
}
