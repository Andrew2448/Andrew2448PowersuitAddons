package andrew.powersuits.modules;

import andrew.powersuits.common.AddonComponent;
import andrew.powersuits.common.AddonUtils;
import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.MuseCommonStrings;
import net.machinemuse.api.MuseItemUtils;
import net.machinemuse.api.electricity.ElectricItemUtils;
import net.machinemuse.api.moduletrigger.IPlayerTickModule;
import net.machinemuse.api.moduletrigger.IToggleableModule;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class MagnetModule extends PowerModuleBase implements IPlayerTickModule, IToggleableModule {
	public static final String MODULE_MAGNET = "Magnet";
	public static final String MAGNET_ENERGY_CONSUMPTION = "Magnet Energy Consumption";
	public MagnetModule(List<IModularItem> validItems) {
		super(validItems);
		addBaseProperty(MuseCommonStrings.WEIGHT, 1000);
		addInstallCost(MuseItemUtils.copyAndResize(AddonComponent.magnet, 2));
	    addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.controlCircuit, 1));
	    addBaseProperty(MAGNET_ENERGY_CONSUMPTION, 200);
	}
	
	@Override
	public String getTextureFile() {
		return "magnetmodule";
	}

	@Override
	public String getCategory() {
		return MuseCommonStrings.CATEGORY_SPECIAL;
	}

	@Override
	public String getName() {
		return MODULE_MAGNET;
	}

	@Override
	public String getDescription() {
		return "Generates a magnetic field strong enough to attract items towards the player.         WARNING:                 This module drains power continuously. Turn it off when not needed. (Keybind menu: k)";
	}

	@Override
	public void onPlayerTickActive(EntityPlayer player, ItemStack stack) {
		if (ElectricItemUtils.getPlayerEnergy(player) > 100) {
			if ((player.worldObj.getTotalWorldTime() % 20) == 0) {
				ElectricItemUtils.drainPlayerEnergy(player, ModuleManager.computeModularProperty(stack, MAGNET_ENERGY_CONSUMPTION));
			}
			World world = player.worldObj;
			float distancexz = 6;
			float distancey = 6;
			double maxspeedxz = 0.5;
			double maxspeedy = 0.5;
			double speedxz = 0.05;
			double speedy = 0.07;
			
			List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, player.boundingBox.expand(distancexz, distancey, distancexz));
			for(Iterator<EntityItem> iterator = items.iterator(); iterator.hasNext();) {
				EntityItem item = iterator.next();
				
				if(item.delayBeforeCanPickup > 0) {
					continue;
				}
				if(item.isDead && world.isRemote) {
					iterator.remove();
				}
				
				if(!AddonUtils.canItemFitInInventory(player, item.getEntityItem())) {
					continue;
				}
				
				double dx = player.posX - item.posX;
				double dy = player.posY + player.getEyeHeight() - item.posY;
				double dz = player.posZ - item.posZ;
				double absxz = Math.sqrt(dx*dx+dz*dz);
				double absy = Math.abs(dy);
				if(absxz > distancexz) {
					continue;
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
				if(rationspeedxz > 1)
				{
					vx/=rationspeedxz;
					vz/=rationspeedxz;
				}
				
				double rationspeedy = absvy / maxspeedy;
				if(rationspeedy > 1)
				{
					vy/=rationspeedy;
				}
				
				if(absvxz < 0.2 && absxz < 0.2 && world.isRemote)
				{
					item.setDead();
				}
				
				item.setVelocity(vx, vy, vz);
			}
		}
	}

	@Override
	public void onPlayerTickInactive(EntityPlayer player, ItemStack item) {}
}
