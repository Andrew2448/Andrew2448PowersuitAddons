package andrew.powersuits.tick;

import andrew.powersuits.common.AddonUtils;
import andrew.powersuits.common.AndrewPacketMagnetMode;
import andrew.powersuits.modules.MagnetModule;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.machinemuse.powersuits.item.ItemPowerArmorChestplate;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;
import java.util.List;

/**
 * Created by User: Andrew2448
 * 12:46 PM 4/27/13
 */
public class ServerTickHandler implements ITickHandler {

    private static ServerTickHandler instance;

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        if(type.contains(TickType.PLAYER)) {
            System.out.println("Server");
            EntityPlayerMP player = (EntityPlayerMP)tickData[0];
            ItemStack torso = player.getCurrentArmor(2);
            if (torso != null && torso.getItem() instanceof ItemPowerArmorChestplate) {
                if (MuseItemUtils.itemHasActiveModule(torso, MagnetModule.MODULE_MAGNET)) {
                    updateMagneticPlayer(player);
                }
            }
        }
    }

    private void updateMagneticPlayer(EntityPlayerMP player) {

        float distancexz = 16;
        float distancey = 8;
        double maxspeedxz = 0.5;
        double maxspeedy = 0.5;
        double speedxz = 0.05;
        double speedy = 0.07;

        System.out.println("Derp1");
        @SuppressWarnings("unchecked")
        List<EntityItem> items = player.worldObj.getEntitiesWithinAABB(EntityItem.class, player.boundingBox.expand(distancexz, distancey, distancexz));
        System.out.println("Derp2");
        for(EntityItem item : items) {
            if(item.delayBeforeCanPickup > 0) {
                continue;
            }

            if(!AddonUtils.canItemFitInInventory(player, item.getEntityItem())) {
                continue;
            }

            if(item.delayBeforeCanPickup == 0) {
                PacketDispatcher.sendPacketToPlayer(new AndrewPacketMagnetMode((Player)player, item.entityId).getPacket250(), (Player) player);
            }

            double dx = player.posX - item.posX;
            double dy = player.posY+player.getEyeHeight() - item.posY;
            double dz = player.posZ - item.posZ;
            double absxz = Math.sqrt(dx*dx+dz*dz);
            double absy = Math.abs(dy);

            if(absxz > distancexz) {
                continue;
            }
            if(absxz < 1) {
                item.onCollideWithPlayer(player);
            }

            if(absxz > 1) {
                dx /= absxz;
                dz /= absxz;
            }

            if(absy > 1) {
                dy /= absy;
            }

            double vx = item.motionX + speedxz*dx;
            double vy = item.motionY + speedy*dy;
            double vz = item.motionZ + speedxz*dz;

            double absvxz = Math.sqrt(vx*vx+vz*vz);
            double absvy = Math.abs(vy);

            double rationspeedxz = absvxz / maxspeedxz;
            if(rationspeedxz > 1) {
                vx/=rationspeedxz;
                vz/=rationspeedxz;
            }

            double rationspeedy = absvy / maxspeedy;
            if(rationspeedy > 1) {
                vy/=rationspeedy;
            }

            item.motionX = vx;
            item.motionY = vy;
            item.motionZ = vz;
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {

    }

    public static void load() {
        instance = new ServerTickHandler();
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.PLAYER);
    }

    @Override
    public String getLabel() {
        return "MPSA: Server Tick";
    }

    public static ServerTickHandler instance() {
        return instance;
    }
}
