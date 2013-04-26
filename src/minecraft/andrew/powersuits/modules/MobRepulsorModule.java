package andrew.powersuits.modules;

import andrew.powersuits.common.AddonComponent;
import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.moduletrigger.IPlayerTickModule;
import net.machinemuse.api.moduletrigger.IToggleableModule;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.machinemuse.utils.ElectricItemUtils;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

/**
 * Created by User: Andrew2448
 * 8:26 PM 4/25/13
 */
public class MobRepulsorModule extends PowerModuleBase implements IPlayerTickModule, IToggleableModule {
    public static final String MODULE_MOB_REPULSOR = "Mob Repulsor";
    public static final String MOB_REPULSOR_ENERGY_CONSUMPTION = "Repulsor Energy Consumption";

    public MobRepulsorModule(List<IModularItem> validItems) {
        super(validItems);
        addBaseProperty(MOB_REPULSOR_ENERGY_CONSUMPTION, 250);
        addBaseProperty(MuseCommonStrings.WEIGHT, 2000);
        addInstallCost(MuseItemUtils.copyAndResize(AddonComponent.magnet, 1));
        addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
    }

    @Override
    public String getTextureFile() {
        return "magneta";
    }

    @Override
    public String getCategory() {
        return MuseCommonStrings.CATEGORY_ENVIRONMENTAL;
    }

    @Override
    public String getName() {
        return MODULE_MOB_REPULSOR;
    }

    @Override
    public String getDescription() {
        return "Pushes mobs away from you when activated, but constantly drains power. It is highly recommended that you set this module to a keybind because of the high energy draw.";
    }

    @Override
    public void onPlayerTickActive(EntityPlayer player, ItemStack item) {
        if (ElectricItemUtils.getPlayerEnergy(player) > ModuleManager.computeModularProperty(item, MOB_REPULSOR_ENERGY_CONSUMPTION)) {
            if (player.worldObj.getTotalWorldTime() % 20 == 0) {
                ElectricItemUtils.drainPlayerEnergy(player, ModuleManager.computeModularProperty(item, MOB_REPULSOR_ENERGY_CONSUMPTION));
            }
            repulse(player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
        }
    }

    @Override
    public void onPlayerTickInactive(EntityPlayer player, ItemStack item) {
    }

    public void repulse(World world, int i, int j, int k) {
        float distance = 5.0F;
        List list = world.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox(i - distance, j - distance, k - distance, i + distance, j + distance, k + distance));
        Entity eentity;
        for (Iterator iterator1 = list.iterator(); iterator1.hasNext(); push(eentity, i, j, k)) {
            eentity = (Entity)iterator1.next();
        }
        List lista = world.getEntitiesWithinAABB(EntityArrow.class, AxisAlignedBB.getBoundingBox(i - distance, j - distance, k - distance, i + distance, j + distance, k + distance));
        Entity aentity;
        for (Iterator iterator2 = lista.iterator(); iterator2.hasNext(); push(aentity, i, j, k)) {
            aentity = (Entity)iterator2.next();
        }
        List listb = world.getEntitiesWithinAABB(EntityFireball.class, AxisAlignedBB.getBoundingBox(i - distance, j - distance, k - distance, i + distance, j + distance, k + distance));
        Entity bentity;
        for (Iterator iterator3 = listb.iterator(); iterator3.hasNext(); push(bentity, i, j, k))
            bentity = (Entity)iterator3.next();
    }

    private void push(Entity entity, int i, int j, int k) {
        if (!(entity instanceof EntityPlayer)) {
            Entity entity2 = entity;
            double d = i - entity2.posX;
            double d1 = j - entity2.posY;
            double d2 = k - entity2.posZ;
            double d4 = d * d + d1 * d1 + d2 * d2;
            d4 *= d4;
            if (d4 <= Math.pow(6.0D, 4.0D)) {
                double d5 = -(d * 0.01999999955296516D / d4) * Math.pow(6.0D, 3.0D);
                double d6 = -(d1 * 0.01999999955296516D / d4) * Math.pow(6.0D, 3.0D);
                double d7 = -(d2 * 0.01999999955296516D / d4) * Math.pow(6.0D, 3.0D);
                if (d5 > 0.0D) {
                    d5 = 0.22D;
                }
                else if (d5 < 0.0D) {
                    d5 = -0.22D;
                }
                if (d6 > 0.2D) {
                    d6 = 0.12D;
                }
                else if (d6 < -0.1D) {
                    d6 = 0.12D;
                }
                if (d7 > 0.0D) {
                    d7 = 0.22D;
                }
                else if (d7 < 0.0D) {
                    d7 = -0.22D;
                }
                entity2.motionX += d5;
                entity2.motionY += d6;
                entity2.motionZ += d7;
            }
        }
    }
}
